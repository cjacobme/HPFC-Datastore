package cj.software.hpfc.weather.imports.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

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
}
