package cj.software.hpfc.weather.imports.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import cj.software.hpfc.weather.imports.entity.MeteoMeasure;
import cj.software.hpfc.weather.imports.entity.WeatherValues;

public class WeatherValuesImportDAO
{

	@Inject
	private MappingManager mappingManager;

	@Inject
	private Session session;

	private Logger logger = LogManager.getFormatterLogger();

	public WeatherValuesImportDAO()
	{
		CodecRegistry.DEFAULT_INSTANCE.register(
				new EnumNameCodec<MeteoMeasure>(MeteoMeasure.class));
	}

	void setSession(Session pSession)
	{
		this.session = pSession;
		this.mappingManager = new MappingManager(this.session);
	}

	public List<ResultSetFuture> saveSome(List<WeatherValues> pEntries)
	{
		List<ResultSetFuture> lResult = new ArrayList<>(pEntries.size());
		Mapper<WeatherValues> lMapper = this.mappingManager.mapper(WeatherValues.class);
		for (WeatherValues bEntry : pEntries)
		{
			lResult.add(this.session.executeAsync(lMapper.saveQuery(bEntry)));
		}
		this.logger.debug("saved %d entries async", lResult.size());
		return lResult;
	}

	public BulkyOperationResult saveMany(int pMaxPending, List<WeatherValues> pEntries)
	{
		int lNumPending = this.getPending();
		BulkyOperationResult.Builder lBuilder = BulkyOperationResult.builder().withInFlights(
				lNumPending);

		if (lNumPending > pMaxPending)
		{
			this.logger.warn("too many pending: %d > %d", lNumPending, pMaxPending);
			lBuilder.rejected();
		}
		else
		{
			List<ResultSetFuture> lFutures = this.saveSome(pEntries);
			lBuilder.addResultSetFutures(lFutures);
			this.logger.info("saved #entries=%d, #pending=%d", lFutures.size(), lNumPending);
		}
		BulkyOperationResult lResult = lBuilder.build();
		return lResult;
	}

	/*
	 * funzt erst mal nur bei 1 Host
	 */
	private int getPending()
	{
		Session.State lSessionState = this.session.getState();
		Set<Host> lAllHosts = this.session.getCluster().getMetadata().getAllHosts();

		Host lHost = lAllHosts.iterator().next();
		int lNumInFlights = lSessionState.getInFlightQueries(lHost);
		return lNumInFlights;
	}
}
