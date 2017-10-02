package cj.software.hpfc.weather.imports.service;

import javax.enterprise.context.Dependent;

import cj.software.hpfc.weather.imports.entity.ImportDirectory;
import cj.software.hpfc.weather.imports.schema.OperationMarkDirectoryFinishedIn;

@Dependent
public class WeatherImportSchemaToEntity
{
	public ImportDirectory toImportDirectory(OperationMarkDirectoryFinishedIn pIn)
	{
		ImportDirectory lResult = new ImportDirectory(pIn.getDirectory(), true);
		return lResult;
	}
}
