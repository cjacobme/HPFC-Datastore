package cj.software.hpfc.lokation.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import cj.software.hpfc.lokation.entity.Lokation;

@Dependent
public class LokationenDAO
		implements
		Serializable
{
	private static final long serialVersionUID = 1L;

	private static final int BUCKET_NO = 1;

	private Logger logger = LogManager.getFormatterLogger();

	@Inject
	Session session;

	public LokationenDAO()
	{
	}

	public List<String> readLokationBezeichnungen()
	{
		List<String> lResult = this.readLokationBezeichnungen(this.session);
		return lResult;

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
		Lokation lResult = this.readLokationDetails(this.session, pBezeichnung);
		return lResult;
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
		String lResult = this.addLokation(this.session, pLokation);
		return lResult;
	}

	private String addLokation(Session pSession, Lokation pLokation)
	{
		MappingManager lMappingManager = new MappingManager(pSession);
		Mapper<Lokation> lMapper = lMappingManager.mapper(Lokation.class);
		lMapper.save(pLokation);
		String lResult = pLokation.getBezeichnung();
		this.logger.info("added Lokation \"%s\"", lResult);
		return lResult;
	}
}
