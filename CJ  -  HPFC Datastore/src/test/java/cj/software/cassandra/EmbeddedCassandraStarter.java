package cj.software.cassandra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.rules.ExternalResource;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.TableMetadata;

/**
 * eine JUnit-Regel, die einen Embedded Cassandra Server startet. Dabei werden
 * eine Reihe von Dateinamen übergeben, deren Inhalt sukzessive gelesen und im
 * Cassandra ausgeführt wird. So kann man eine Datei angeben, die das
 * Tabellenschema anlegt, und eine weitere, die in einer zu testenden Tabelle
 * Datensätze anlegt.
 */
public class EmbeddedCassandraStarter
		extends ExternalResource
{
	private Logger logger = LogManager.getFormatterLogger();

	private Class<?> clazz;

	private Optional<Consumer<Session>> sessionConsumer;

	private String keyspaceName;

	private String schemaFilename;

	private String[] additionalFilenames;

	private Session session;

	public EmbeddedCassandraStarter(
			Class<?> pClazz,
			Optional<Consumer<Session>> pSessionConsumer,
			String pKeyspaceName,
			String pSchemaFilename,
			String... pAdditionalFilenames)
	{
		this.clazz = Objects.requireNonNull(pClazz);
		this.sessionConsumer = Objects.requireNonNull(pSessionConsumer);
		this.keyspaceName = Objects.requireNonNull(pKeyspaceName);
		this.schemaFilename = Objects.requireNonNull(pSchemaFilename);
		this.additionalFilenames = pAdditionalFilenames;
	}

	@Override
	protected void before() throws Throwable
	{
		EmbeddedCassandraServerHelper.startEmbeddedCassandra(
				EmbeddedCassandraServerHelper.CASSANDRA_RNDPORT_YML_FILE);
		this.logger.info("Embedded Server is started");
		this.session = this.createAndConnectToKeyspace(EmbeddedCassandraServerHelper.getSession());

		this.applyFile(this.schemaFilename);
		if (this.additionalFilenames != null)
		{
			for (String bAdditionalFilename : this.additionalFilenames)
			{
				this.applyFile(bAdditionalFilename);
			}
		}
		if (this.sessionConsumer.isPresent())
		{
			Consumer<Session> lConsumer = this.sessionConsumer.get();
			lConsumer.accept(this.session);
		}
	}

	private Session createAndConnectToKeyspace(Session pSession)
	{
		//@formatter:off
		String lQL = String.format(
				"CREATE KEYSPACE IF NOT EXISTS %s "
				+ "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}"
				+ "AND durable_writes = true",
				this.keyspaceName);
		//@formatter:on
		pSession.execute(lQL);

		Session lResult = pSession.getCluster().connect(this.keyspaceName);
		this.logger.info("Connected to keyspace \"%s\"", this.keyspaceName);
		return lResult;
	}

	private void applyFile(String pFilename) throws IOException, URISyntaxException
	{
		this.logger.info("Applying file \"%s\"...", pFilename);
		Parser lParser = new Parser();
		List<String> lStatements = lParser.extractStatements(pFilename);
		for (String bStatement : lStatements)
		{
			this.logger.debug(bStatement);
			this.session.execute(bStatement);
		}
		this.logger.debug("%s done", pFilename);
	}

	public Session getSession()
	{
		return this.session;
	}

	private class Parser
	{
		private List<String> extractStatements(String pFilename) throws IOException,
				URISyntaxException
		{
			try (InputStream lIS = EmbeddedCassandraStarter.this.clazz.getResourceAsStream(
					pFilename))
			{
				try (InputStreamReader lISR = new InputStreamReader(lIS))
				{
					try (BufferedReader lBR = new BufferedReader(lISR))
					{
						List<String> lResult = this.extractStatements(lBR);
						return lResult;
					}
				}
			}
		}

		private List<String> extractStatements(BufferedReader pReader) throws IOException
		{
			List<String> lResult = new ArrayList<>();
			StringBuilder lBisher = new StringBuilder();
			String lLine = pReader.readLine();
			while (lLine != null)
			{
				if (!lLine.isEmpty())
				{
					String lCommentCleaned = this.cleanComment(lLine);
					if (!lCommentCleaned.isEmpty())
					{
						lBisher.append(lCommentCleaned);
						if (lCommentCleaned.endsWith(";"))
						{
							lResult.add(lBisher.toString());
							lBisher.delete(0, lBisher.length());
						}
					}
				}
				lLine = pReader.readLine();
			}
			if (lBisher.length() > 0)
			{
				lResult.add(lBisher.toString());
			}
			return lResult;
		}

		private String cleanComment(String pSource)
		{
			int lIndex = pSource.indexOf("//");
			String lResult;
			if (lIndex >= 0)
			{
				lResult = pSource.substring(0, lIndex).trim();
			}
			else
			{
				lResult = pSource.trim();
			}
			return lResult;
		}
	}

	@Override
	protected void after()
	{
		this.truncateKeyspaceTables();
	}

	private void truncateKeyspaceTables()
	{
		Session lSession = this.getSession();
		Cluster lCluster = lSession.getCluster();
		final KeyspaceMetadata lKeyspaceMetadata = lCluster.getMetadata().getKeyspace(
				this.keyspaceName);
		final Collection<TableMetadata> lTables = lKeyspaceMetadata.getTables();
		for (TableMetadata bTable : lTables)
		{
			final String lTableName = bTable.getName();
			String lStmt = String.format("truncate table %s.%s", this.keyspaceName, lTableName);
			this.logger.debug(lStmt);
			this.session.execute(lStmt);
		}
		this.logger.info("Keyspace \"%s\" cleaned", this.keyspaceName);
	}

}
