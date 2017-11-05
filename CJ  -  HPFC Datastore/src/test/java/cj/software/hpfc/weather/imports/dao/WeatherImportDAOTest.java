package cj.software.hpfc.weather.imports.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.ClassRule;
import org.junit.Test;

import cj.software.cassandra.EmbeddedCassandraStarter;
import cj.software.hpfc.weather.imports.entity.ImportDirectory;

public class WeatherImportDAOTest
{
	private static WeatherImportDAO weatherImportDAO = new WeatherImportDAO();

	@ClassRule
	public static EmbeddedCassandraStarter embeddedCassandraStarter = new EmbeddedCassandraStarter(
			WeatherImportDAOTest.class,
			Optional.of(weatherImportDAO::setSession),
			"hpfcds_test",
			"/Statements.cql",
			"InsertDiretoriesFinished.cql");

	@Test
	public void listAllDirectories()
	{
		List<ImportDirectory> lFoundDirectories = weatherImportDAO.listDirectories();
		assertThat(lFoundDirectories).isNotNull();
		assertThat(lFoundDirectories)
				.as("Entries in List of found Directories")
				.containsExactlyInAnyOrder(
						new ImportDirectory("2017-10-01T06:00Z Wind-U", false),
						new ImportDirectory("2017-10-01T09:00Z Wind-U", false),
						new ImportDirectory("2017-10-01T12:00Z Wind-U", false),
						new ImportDirectory("2017-10-01T06:00Z Flux", true),
						new ImportDirectory("2017-10-01T09:00Z Flux", true),
						new ImportDirectory("2017-10-01T12:00Z Flux", false));
	}

	@Test
	public void listSomeDirectories()
	{
		List<ImportDirectory> lFoundDirectories = weatherImportDAO.listDirectories(
				"2017-10-01T06:00Z Wind-U",
				"2017-10-01T09:00Z Flux");
		assertThat(lFoundDirectories).isNotNull();
		assertThat(lFoundDirectories)
				.as("Entries in List of found Directories")
				.containsExactlyInAnyOrder(
						new ImportDirectory("2017-10-01T06:00Z Wind-U", false),
						new ImportDirectory("2017-10-01T09:00Z Flux", true));
	}
}
