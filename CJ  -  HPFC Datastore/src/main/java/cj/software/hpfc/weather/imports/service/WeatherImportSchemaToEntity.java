package cj.software.hpfc.weather.imports.service;

import javax.enterprise.context.Dependent;

import cj.software.hpfc.weather.imports.entity.FilesFinished;
import cj.software.hpfc.weather.imports.entity.ImportDirectory;
import cj.software.hpfc.weather.imports.schema.OperationMarkDirectoryFinishedIn;
import cj.software.hpfc.weather.imports.schema.OperationMarkFileFinishedIn;

@Dependent
public class WeatherImportSchemaToEntity
{
	public ImportDirectory toImportDirectory(OperationMarkDirectoryFinishedIn pIn)
	{
		ImportDirectory lResult = new ImportDirectory(pIn.getDirectory(), true);
		return lResult;
	}

	public FilesFinished toFilesFinished(OperationMarkFileFinishedIn pOperation)
	{
		String lDirName = pOperation.getDirName();
		String lFileName = pOperation.getFileName();
		FilesFinished lResult = new FilesFinished(lDirName, lFileName);
		return lResult;
	}
}
