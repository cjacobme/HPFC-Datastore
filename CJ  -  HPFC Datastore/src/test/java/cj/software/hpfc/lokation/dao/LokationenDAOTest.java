package cj.software.hpfc.lokation.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.util.List;
import java.util.Optional;

import org.junit.ClassRule;
import org.junit.Test;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import cj.software.cassandra.EmbeddedCassandraStarter;
import cj.software.hpfc.lokation.entity.Lokation;

public class LokationenDAOTest
{

	private static LokationenDAO lokationenDAO = new LokationenDAO();

	@ClassRule
	public static EmbeddedCassandraStarter embeddedCassandraStarter = new EmbeddedCassandraStarter(
			LokationenDAOTest.class,
			Optional.of(lokationenDAO::setSession),
			"hpfcds_test",
			"/Statements.cql",
			"InsertDefaultLokationen.cql");

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
	public void listAT()
	{
		Lokation lLokation = lokationenDAO.readLokationDetails("AT");
		assertThat(lLokation).as("Gelesene Lokation").isNotNull();
		String lLokationBezeichnung = lLokation.getBezeichnung();
		assertThat(lLokationBezeichnung).as("Gelesene Lokationsbezeichnung").isEqualTo("AT");
		float lGeogrBreite = lLokation.getGeogrBreite();
		assertThat(lGeogrBreite).as("Gelesene geogr. Breite").isCloseTo(39.23f, within(0.001f));
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

	@Test
	public void addLokation()
	{
		Lokation lToBeAdded = new Lokation("GR", 23.45f, 32.1f);
		LokationenDAOTest.lokationenDAO.addLokation(lToBeAdded);

		Session lSession = embeddedCassandraStarter.getSession();
		String lQuery = "SELECT COUNT(*) FROM Lokation WHERE bucket = 1";
		ResultSet lResultSet = lSession.execute(lQuery);
		Row lRow = lResultSet.one();
		long lCount = lRow.getLong(0);
		assertThat(lCount).as("Number of locations after insert").isEqualTo(3);
	}
}
