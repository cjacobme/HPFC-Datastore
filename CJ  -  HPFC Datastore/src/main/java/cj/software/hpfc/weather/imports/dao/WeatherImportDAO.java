package cj.software.hpfc.weather.imports.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

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
import cj.software.hpfc.weather.imports.entity.WeatherValues;

@Dependent
public class WeatherImportDAO implements Serializable
{
	private static final long serialVersionUID = 1L;

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
		}

		Session lSession = this.mappingManager.getSession();
		lSession.execute(lBatchStatement);
	}
}
