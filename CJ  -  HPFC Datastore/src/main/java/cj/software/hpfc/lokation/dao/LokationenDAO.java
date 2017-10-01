package cj.software.hpfc.lokation.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import cj.software.hpfc.lokation.entity.Lokation;

public class LokationenDAO
{
	private Logger logger = LogManager.getFormatterLogger();

	private static final int BUCKET_NO = 1;

	public List<String> readLokationBezeichnungen()
	{
		String lHostname = System.getProperty("host");
		if (lHostname == null)
		{
			throw new IllegalStateException("System Property \"host\" not set");
		}
		String lKeyspaceName = System.getProperty("keyspace");
		if (lKeyspaceName == null)
		{
			throw new IllegalStateException("System Property \"keyspace\" not set");
		}
		try (Cluster lCluster = Cluster.builder().addContactPoint(lHostname).build())
		{
			this.logger.info("connected to %s", lHostname);
			try (Session lSession = lCluster.connect(lKeyspaceName))
			{
				this.logger.info("opened session on %s", lKeyspaceName);
				List<String> lResult = this.readLokationBezeichnungen(lSession);
				return lResult;
			}
		}
	}

	private List<String> readLokationBezeichnungen(Session pSession)
	{
		String lQL = "SELECT bezeichnung FROM lokation WHERE bucket = ?";
		PreparedStatement lStmt = pSession.prepare(lQL);
		BoundStatement lBoundStmt = lStmt.bind(BUCKET_NO);
		ResultSet lRS = pSession.execute(lBoundStmt);
		List<String> lResult = new ArrayList<>();
		for (Row bRow : lRS.all())
		{
			String lBezeichnung = bRow.getString("bezeichnung");
			lResult.add(lBezeichnung);
		}
		return lResult;
	}

	public Lokation readLokationDetails(String pBezeichnung)
	{
		String lHostname = System.getProperty("host");

		if (lHostname == null)
		{
			throw new IllegalStateException("System Property \"host\" not set");
		}
		String lKeyspaceName = System.getProperty("keyspace");
		if (lKeyspaceName == null)
		{
			throw new IllegalStateException("System Property \"keyspace\" not set");
		}
		try (Cluster lCluster = Cluster.builder().addContactPoint(lHostname).build())
		{
			this.logger.info("connected to %s", lHostname);
			try (Session lSession = lCluster.connect(lKeyspaceName))
			{
				this.logger.info("opened session on %s", lKeyspaceName);
				Lokation lResult = this.readLokationDetails(lSession, pBezeichnung);
				return lResult;
			}
		}
	}

	private Lokation readLokationDetails(Session pSession, String pBezeichnung)
	{
		MappingManager lMappingManager = new MappingManager(pSession);
		LokationAccessor lAccessor = lMappingManager.createAccessor(LokationAccessor.class);
		Lokation lResult = lAccessor.readLokationDetails(pBezeichnung);
		return lResult;
	}

	public String addLokation(Lokation pLokation)
	{
		String lHostname = System.getProperty("host");
		if (lHostname == null)
		{
			throw new IllegalStateException("System Property \"host\" not set");
		}
		String lKeyspaceName = System.getProperty("keyspace");
		if (lKeyspaceName == null)
		{
			throw new IllegalStateException("System Property \"keyspace\" not set");
		}
		try (Cluster lCluster = Cluster.builder().addContactPoint(lHostname).build())
		{
			this.logger.info("connected to %s", lHostname);
			try (Session lSession = lCluster.connect(lKeyspaceName))
			{
				this.logger.info("opened session on %s", lKeyspaceName);
				String lResult = this.addLokation(lSession, pLokation);
				return lResult;
			}
		}
	}

	private String addLokation(Session pSession, Lokation pLokation)
	{
		MappingManager lMappingManager = new MappingManager(pSession);
		Mapper<Lokation> lMapper = lMappingManager.mapper(Lokation.class);
		lMapper.save(pLokation);
		return pLokation.getBezeichnung();

	}
}
