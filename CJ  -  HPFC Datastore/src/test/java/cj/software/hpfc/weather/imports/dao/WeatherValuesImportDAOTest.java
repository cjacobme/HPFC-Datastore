package cj.software.hpfc.weather.imports.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import cj.software.cassandra.EmbeddedCassandraStarter;
import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.weather.imports.entity.MeteoMeasure;
import cj.software.hpfc.weather.imports.entity.WeatherValues;

public class WeatherValuesImportDAOTest
{
	private static WeatherValuesImportDAO weatherValuesImportDAO = new WeatherValuesImportDAO();

	private Logger logger = LogManager.getFormatterLogger();

	@Rule
	public EmbeddedCassandraStarter embeddedCassandraStarter = new EmbeddedCassandraStarter(
			WeatherValuesImportDAOTest.class,
			Optional.of(this::setSession),
			"hpfcds_test",
			"/Statements.cql");

	private Session session;

	void setSession(Session pSession)
	{
		this.session = pSession;
		weatherValuesImportDAO.setSession(this.session);
	}

	@Test
	public void insertSome()
	{
		int lCount = 100;
		List<WeatherValues> lToBeSaved = new ArrayList<>(lCount);
		Lokation lLokation = new Lokation("Essen", 51.26f, 23.45f);
		OffsetDateTime lPrognose = OffsetDateTime.of(2018, 2, 9, 20, 21, 0, 0, ZoneOffset.UTC);
		OffsetDateTime lZeitpunkt = OffsetDateTime.of(2018, 2, 11, 1, 0, 0, 0, ZoneOffset.UTC);
		for (int bI = 0; bI < lCount; bI++)
		{
			WeatherValues lEntry = new WeatherValues(
					lLokation,
					MeteoMeasure.Temperature,
					lPrognose,
					lZeitpunkt,
					3.1 + bI);
			lToBeSaved.add(lEntry);
			lZeitpunkt = lZeitpunkt.plusMinutes(15);
		}

		List<ResultSetFuture> lFutures = weatherValuesImportDAO.saveSome(lToBeSaved);
		for (ResultSetFuture bFuture : lFutures)
		{
			bFuture.getUninterruptibly();
		}

		ResultSet lRsCount = this.session.execute("SELECT count(*) FROM weather_values");
		List<Row> lAll = lRsCount.all();
		Row lRow = lAll.get(0);
		long lActCount = lRow.getLong(0);
		assertThat(lActCount).as("number in DB").isEqualTo(lCount);
	}

	@Test
	public void insertMany100() throws InterruptedException
	{
		this.insertMany(100);
	}

	private void insertMany(int pMaxPending) throws InterruptedException
	{
		OffsetDateTime lPrognose = OffsetDateTime.of(2018, 2, 9, 20, 21, 0, 0, ZoneOffset.UTC);
		OffsetDateTime lZeitpunkt = OffsetDateTime.of(2018, 2, 11, 1, 0, 0, 0, ZoneOffset.UTC);
		int lNumBlocks = 100;
		Lokation lLokation = new Lokation("Essen", 51.26f, 23.45f);
		int lNumPendings = 0;
		List<ResultSetFuture> lFutures = new ArrayList<>();
		for (int bBlock = 0; bBlock < lNumBlocks; bBlock++)
		{
			int lNumValues = 1000;
			List<WeatherValues> lToBeSaved = new ArrayList<>(lNumValues);
			for (int bValue = 0; bValue < lNumValues; bValue++)
			{
				double lValue = bBlock + (double) bValue / 1000;
				WeatherValues lEntry = new WeatherValues(
						lLokation,
						MeteoMeasure.Temperature,
						lPrognose,
						lZeitpunkt,
						lValue);
				lToBeSaved.add(lEntry);
				lZeitpunkt = lZeitpunkt.plusMinutes(15);
			}
			BulkyOperationResult lOperationsResult;
			lOperationsResult = weatherValuesImportDAO.saveMany(pMaxPending, lToBeSaved);
			lFutures.addAll(lOperationsResult.getFutures());
			while (lOperationsResult.isRejectedTooManyPending())
			{
				lNumPendings++;
				this.logger.info("Pending #%d in Block %d", lNumPendings, bBlock);
				Thread.sleep(2000);
				lOperationsResult = weatherValuesImportDAO.saveMany(pMaxPending, lToBeSaved);
				lFutures.addAll(lOperationsResult.getFutures());
			}
		}
		this.logger.info("total pendings = %d", lNumPendings);
		try
		{
			assertThat(lNumPendings).as("number of pendings").isGreaterThan(0);
			this.logger.info("finish %d futures", lFutures.size());
			for (ResultSetFuture bFuture : lFutures)
			{
				bFuture.getUninterruptibly();
			}
			ResultSet lRsCount = this.session.execute(
					"SELECT count(*) FROM weather_values "
							+ "WHERE lokation_bezeichnung = 'Essen' "
							+ "AND meteo_measure = 'Temperature' "
							+ "AND prognosezeitpunkt = '2018-02-09T20:21:00+0000'");
			this.logger.info("validate number of entries");
			List<Row> lAll = lRsCount.all();
			Row lRow = lAll.get(0);
			long lActCount = lRow.getLong(0);
			assertThat(lActCount).as("number in DB").isEqualTo(100000);
		}
		finally
		{
			Thread.sleep(5000);
		}
	}
}
