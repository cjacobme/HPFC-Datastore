package cj.software.hpfc.weather.imports.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.Session;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.weather.imports.entity.FilesFinished;
import cj.software.hpfc.weather.imports.entity.ImportDirectory;
import cj.software.hpfc.weather.imports.entity.MeteoMeasure;
import cj.software.hpfc.weather.imports.entity.TmpWindU;
import cj.software.hpfc.weather.imports.entity.TmpWindV;
import cj.software.hpfc.weather.imports.entity.WeatherKey;
import cj.software.hpfc.weather.imports.entity.WeatherValues;

@Dependent
public class WeatherImportDAO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Logger logger = LogManager.getFormatterLogger();

	@Inject
	private MappingManager mappingManager;

	public WeatherImportDAO()
	{
		CodecRegistry.DEFAULT_INSTANCE.register(new EnumNameCodec<MeteoMeasure>(MeteoMeasure.class));
	}

	public List<ImportDirectory> listDirectories(String... pSearched)
	{
		WeatherImportAccessor lAccessor = this.mappingManager.createAccessor(WeatherImportAccessor.class);
		List<String> lAsList = this.toStringList(pSearched);
		Result<ImportDirectory> lRead = lAccessor.listDirectories(lAsList);
		List<ImportDirectory> lResult = lRead.all();
		return lResult;
	}

	private List<String> toStringList(String... pItems)
	{
		List<String> lResult = new ArrayList<>(pItems.length);
		lResult = Arrays.asList(pItems);
		return lResult;
	}

	public void save(ImportDirectory pImportDirectory)
	{
		Mapper<ImportDirectory> lMapper = this.mappingManager.mapper(ImportDirectory.class);
		lMapper.save(pImportDirectory);
	}

	public void save(FilesFinished pFilesFinished, List<WeatherValues> pValuesList)
	{
		ImportDirectory lImportDirectory = new ImportDirectory(pFilesFinished.getDirectoryName(), false);

		Mapper<FilesFinished> lMapperFiles = this.mappingManager.mapper(FilesFinished.class);
		Mapper<ImportDirectory> lMapperDirs = this.mappingManager.mapper(ImportDirectory.class);
		Mapper<WeatherValues> lMapperWeatherValues = this.mappingManager.mapper(WeatherValues.class);
		Mapper<Lokation> lMapperLokation = this.mappingManager.mapper(Lokation.class);
		Mapper<TmpWindU> lMapperWindU = this.mappingManager.mapper(TmpWindU.class);
		Mapper<TmpWindV> lMapperWindV = this.mappingManager.mapper(TmpWindV.class);

		BatchStatement lBatchStatement = new BatchStatement();
		lBatchStatement.add(lMapperFiles.saveQuery(pFilesFinished));
		lBatchStatement.add(lMapperDirs.saveQuery(lImportDirectory));

		Lokation lLastLokation = null;

		for (WeatherValues bWeatherValues : pValuesList)
		{
			Lokation lCurrentLokation = new Lokation(bWeatherValues.getLokationBezeichnung(),
					bWeatherValues.getGeogrBreite(), bWeatherValues.getGeogrLaenge());
			if (!lCurrentLokation.equals(lLastLokation))
			{
				lBatchStatement.add(lMapperLokation.saveQuery(lCurrentLokation));
				lLastLokation = lCurrentLokation;
			}
			lBatchStatement.add(lMapperWeatherValues.saveQuery(bWeatherValues));
			MeteoMeasure lMeteoMeasure = bWeatherValues.getMeteoMeasure();
			switch (lMeteoMeasure)
			{
			case Flux:
				break;
			case Speed_Total:
				break;
			case Speed_U:
				TmpWindU lTmpWindU = this.toTmpWindU(bWeatherValues);
				lBatchStatement.add(lMapperWindU.saveQuery(lTmpWindU));
				break;
			case Speed_V:
				TmpWindV lTmpWindV = this.toTmpWindV(bWeatherValues);
				lBatchStatement.add(lMapperWindV.saveQuery(lTmpWindV));
				break;
			case Temperature:
				break;
			default:
				throw new IllegalArgumentException("Unknown: " + lMeteoMeasure);
			}
		}

		Session lSession = this.mappingManager.getSession();
		lSession.execute(lBatchStatement);
		this.logger.info("imported %s", pFilesFinished);
	}

	private TmpWindU toTmpWindU(WeatherValues pWeatherValues)
	{
		TmpWindU lResult = new TmpWindU(pWeatherValues.getLokationBezeichnung(), pWeatherValues.getPrognoseZeitpunkt(),
				pWeatherValues.getZeitpunkt(), pWeatherValues.getGeogrBreite(), pWeatherValues.getGeogrLaenge(),
				pWeatherValues.getWert());
		return lResult;
	}

	private TmpWindV toTmpWindV(WeatherValues pWeatherValues)
	{
		TmpWindV lResult = new TmpWindV(pWeatherValues.getLokationBezeichnung(), pWeatherValues.getPrognoseZeitpunkt(),
				pWeatherValues.getZeitpunkt(), pWeatherValues.getGeogrBreite(), pWeatherValues.getGeogrLaenge(),
				pWeatherValues.getWert());
		return lResult;
	}

	public Map<WeatherKey, TmpWindV> getTmpWindV()
	{
		TmpWindVAccessor lAccessor = this.mappingManager.createAccessor(TmpWindVAccessor.class);
		Result<TmpWindV> lRead = lAccessor.listTmpWindV(3);
		Map<WeatherKey, TmpWindV> lResult = new HashMap<>();
		for (TmpWindV bTmpWindV : lRead)
		{
			WeatherKey lKey = new WeatherKey(bTmpWindV.getLokationBezeichnung(), bTmpWindV.getPrognoseZeitpunkt(),
					bTmpWindV.getZeitpunkt());
			lResult.put(lKey, bTmpWindV);
		}
		return lResult;
	}

	public Map<WeatherKey, TmpWindU> getTmpWindU()
	{
		TmpWindUAccessor lAccessor = this.mappingManager.createAccessor(TmpWindUAccessor.class);
		Result<TmpWindU> lRead = lAccessor.listTmpWindU(3);
		Map<WeatherKey, TmpWindU> lResult = new HashMap<>();
		for (TmpWindU bTmpWindU : lRead)
		{
			WeatherKey lKey = new WeatherKey(bTmpWindU.getLokationBezeichnung(), bTmpWindU.getPrognoseZeitpunkt(),
					bTmpWindU.getZeitpunkt());
			lResult.put(lKey, bTmpWindU);
		}
		return lResult;
	}

	public void saveForTotalWindSpeed(WeatherValues pValuesTotalWindSpeed, TmpWindU pTmpWindU, TmpWindV pTmpWindV)
	{
		Mapper<WeatherValues> lValuesMapper = this.mappingManager.mapper(WeatherValues.class);
		Mapper<TmpWindU> lWindUMapper = this.mappingManager.mapper(TmpWindU.class);
		Mapper<TmpWindV> lWindVMapper = this.mappingManager.mapper(TmpWindV.class);
		BatchStatement lBatchStatement = new BatchStatement();
		lBatchStatement.add(lValuesMapper.saveQuery(pValuesTotalWindSpeed));
		lBatchStatement.add(lWindUMapper.deleteQuery(pTmpWindU));
		lBatchStatement.add(lWindVMapper.deleteQuery(pTmpWindV));
		Session lSession = this.mappingManager.getSession();
		lSession.execute(lBatchStatement);
		this.logger.info("imported %s and deleted %s and %s", pValuesTotalWindSpeed, pTmpWindU, pTmpWindV);
	}
}
