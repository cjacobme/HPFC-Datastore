package cj.software.hpfc.weather.imports.service;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.enterprise.context.Dependent;

import cj.software.hpfc.weather.imports.entity.FilesFinished;
import cj.software.hpfc.weather.imports.entity.ImportDirectory;
import cj.software.hpfc.weather.imports.schema.DirectoriesListGetOut;
import cj.software.hpfc.weather.imports.schema.Directory;
import cj.software.hpfc.weather.imports.schema.File;
import cj.software.hpfc.weather.imports.schema.OperationMarkDirectoryFinishedOut;
import cj.software.hpfc.weather.imports.schema.OperationMarkFileFinishedOut;

@Dependent
public class WeatherImportEntityToSchema
{
	public DirectoriesListGetOut toDirectoriesListGetOut(List<ImportDirectory> pDirectories)
	{
		SortedSet<Directory> lSchemaDirs = this.toListOfImportDirectories(pDirectories);
		DirectoriesListGetOut lResult = new DirectoriesListGetOut(lSchemaDirs);
		return lResult;
	}

	public SortedSet<Directory> toListOfImportDirectories(List<ImportDirectory> pDirectories)
	{
		SortedSet<Directory> lResult = new TreeSet<>();
		for (ImportDirectory bImportDirectory : pDirectories)
		{
			Directory lDirectory = this.toDirectory(bImportDirectory);
			lResult.add(lDirectory);
		}
		return lResult;
	}

	public Directory toDirectory(ImportDirectory pDirectory)
	{
		Directory lResult = new Directory(pDirectory.getDirectoryName(), pDirectory.isFinished());
		return lResult;
	}

	public OperationMarkDirectoryFinishedOut toOperationMarkDirectoryFinishedOut(ImportDirectory pImportDirectory)
	{
		Directory lDirectory = this.toDirectory(pImportDirectory);
		OperationMarkDirectoryFinishedOut lResult = new OperationMarkDirectoryFinishedOut(lDirectory);
		return lResult;
	}

	public OperationMarkFileFinishedOut toOperationMarkFileFinishedOut(FilesFinished pFilesFinished)
	{
		String lDirectoryName = pFilesFinished.getDirectoryName();
		ImportDirectory lImportDirectory = new ImportDirectory(lDirectoryName, false);
		Directory lDirectory = this.toDirectory(lImportDirectory);
		String lFileName = pFilesFinished.getFileName();
		boolean lFinished = pFilesFinished.isFileFinished();
		File lFile = new File(lDirectory, lFileName, lFinished);
		OperationMarkFileFinishedOut lResult = new OperationMarkFileFinishedOut(lFile);
		return lResult;
	}
}
