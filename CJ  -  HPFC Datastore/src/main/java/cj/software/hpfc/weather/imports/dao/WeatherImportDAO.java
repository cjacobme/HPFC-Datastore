package cj.software.hpfc.weather.imports.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import cj.software.hpfc.weather.imports.entity.FilesFinished;
import cj.software.hpfc.weather.imports.entity.ImportDirectory;

@Dependent
public class WeatherImportDAO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private MappingManager mappingManager;

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

	public void save(FilesFinished pFilesFinished)
	{
		ImportDirectory lImportDirectory = new ImportDirectory(pFilesFinished.getDirectoryName(), false);

		Mapper<FilesFinished> lMapperFiles = this.mappingManager.mapper(FilesFinished.class);
		Mapper<ImportDirectory> lMapperDirs = this.mappingManager.mapper(ImportDirectory.class);

		BatchStatement lBatchStatement = new BatchStatement();
		lBatchStatement.add(lMapperFiles.saveQuery(pFilesFinished));
		lBatchStatement.add(lMapperDirs.saveQuery(lImportDirectory));
		Session lSession = this.mappingManager.getSession();
		lSession.execute(lBatchStatement);
	}
}
