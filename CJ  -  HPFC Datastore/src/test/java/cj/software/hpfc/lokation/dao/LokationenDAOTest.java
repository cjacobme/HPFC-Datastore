package cj.software.hpfc.lokation.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.Session;

import cj.software.hpfc.lokation.entity.Lokation;

public class LokationenDAOTest
{
	private static Logger logger = LogManager.getFormatterLogger();

	private static LokationenDAO lokationenDAO;

	@BeforeClass
	public static void startEmbeddedCassandra() throws ConfigurationException,
			TTransportException,
			IOException,
			InterruptedException
	{

		EmbeddedCassandraServerHelper.startEmbeddedCassandra(
				EmbeddedCassandraServerHelper.CASSANDRA_RNDPORT_YML_FILE);
		logger.info("Embedded Server is started");

		lokationenDAO = new LokationenDAO();
		Session lSession = EmbeddedCassandraServerHelper.getSession();
		lSession = createKeyspace(lSession);
		createLokationTable(lSession);
		lokationenDAO.session = lSession;
	}

	private static Session createKeyspace(Session pSession)
	{
		String lKeyspaceName = "hpfcds_test";
		String lQL = "CREATE KEYSPACE IF NOT EXISTS "
				+ lKeyspaceName
				+ " WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  "
				+ "AND durable_writes = true;";
		pSession.execute(lQL);

		Session lResult = pSession.getCluster().connect(lKeyspaceName);
		logger.info("connected to keyspace \"%s\"", lKeyspaceName);
		return lResult;
	}

	private static void createLokationTable(Session pSession)
	{
		String lQL = "CREATE TABLE lokation ("
				+ "bucket INT, bezeichnung TEXT, geogr_breite FLOAT, geogr_laenge FLOAT, "
				+ "PRIMARY KEY((bucket), bezeichnung));";
		pSession.execute(lQL);
	}

	@Before
	public void insertLocations()
	{
		this.insertLocations("DE", 51.27f, 8.27f);
		this.insertLocations("AT", 39.23f, 14.41f);
	}

	private void insertLocations(String pBezeichnung, float pGeogrBreite, float pGeogrLaenge)
	{
		Session lSession = lokationenDAO.session;
		String lInsertQL = String.format(
				Locale.US,
				"INSERT INTO lokation(bucket, bezeichnung, geogr_breite, geogr_laenge) "
						+ "VALUES(1, '%s', %6.2f, %6.2f);",
				pBezeichnung,
				pGeogrBreite,
				pGeogrLaenge);
		lSession.execute(lInsertQL);
	}

	@Test
	public void listDE()
	{
		Lokation lLokation = lokationenDAO.readLokationDetails("DE");
		assertThat(lLokation).as("Gelesene Lokation").isNotNull();
		String lLokationBezeichnung = lLokation.getBezeichnung();
		assertThat(lLokationBezeichnung).as("Gelesene Lokationsbezeichnung").isEqualTo("DE");
		float lGeogrBreite = lLokation.getGeogrBreite();
		assertThat(lGeogrBreite).as("Gelesene geogr. Breite").isCloseTo(51.27f, within(0.001f));
	}

	@Test
	public void listNonExisting()
	{
		Lokation lLokation = lokationenDAO.readLokationDetails("GippetNicht");
		assertThat(lLokation).as("Gelesene Lokation").isNull();
	}

	@Test
	public void listLokationsBezeichnungen()
	{
		List<String> lBezeichnungen = lokationenDAO.readLokationBezeichnungen();
		assertThat(lBezeichnungen).as("Gelesene Lokationsbezeichnungen").containsExactlyInAnyOrder(
				"DE",
				"AT");
	}
}
