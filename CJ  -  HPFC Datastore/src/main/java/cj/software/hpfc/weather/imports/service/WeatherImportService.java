package cj.software.hpfc.weather.imports.service;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cj.software.hpfc.weather.imports.dao.WeatherImportDAO;
import cj.software.hpfc.weather.imports.entity.FilesFinished;
import cj.software.hpfc.weather.imports.entity.ImportDirectory;
import cj.software.hpfc.weather.imports.entity.WeatherValues;
import cj.software.hpfc.weather.imports.schema.DirectoriesListGetOut;
import cj.software.hpfc.weather.imports.schema.OperationImportWetterdatenIn;
import cj.software.hpfc.weather.imports.schema.OperationImportWetterdatenOut;
import cj.software.hpfc.weather.imports.schema.OperationMarkDirectoryFinishedIn;
import cj.software.hpfc.weather.imports.schema.OperationMarkDirectoryFinishedOut;

@Path("/weather/imports")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@Dependent
public class WeatherImportService
{
	@Inject
	private WeatherImportDAO weatherImportDAO;

	@Inject
	private WeatherImportEntityToSchema entityToSchema;

	@Inject
	private WeatherImportSchemaToEntity schemaToEntity;

	private Logger logger = LogManager.getFormatterLogger();

	@GET
	public DirectoriesListGetOut readIfDirectoriesFinished(
			@QueryParam("dirname") String... pDirectoryNames)
	{
		List<ImportDirectory> lRead = this.weatherImportDAO.listDirectories(pDirectoryNames);
		DirectoriesListGetOut lResult = this.entityToSchema.toDirectoriesListGetOut(lRead);
		return lResult;
	}

	@POST
	@Path("operations/mark-directory-finished")
	public OperationMarkDirectoryFinishedOut markFinished(
			OperationMarkDirectoryFinishedIn pOperation)
	{
		ImportDirectory lImportDirectory = this.schemaToEntity.toImportDirectory(pOperation);
		this.weatherImportDAO.save(lImportDirectory);
		OperationMarkDirectoryFinishedOut lResult = this.entityToSchema
				.toOperationMarkDirectoryFinishedOut(lImportDirectory);
		this.logger.info("Directory marked as finished: %s", lImportDirectory);
		return lResult;
	}

	@POST
	@Path("operations/import-wetterdaten")
	public OperationImportWetterdatenOut importWetterdaten(OperationImportWetterdatenIn pOperation)
	{
		List<WeatherValues> lValuesList = this.schemaToEntity.toValuesList(pOperation);
		FilesFinished lFilesFinished = this.schemaToEntity.toFilesFinished(pOperation);
		this.weatherImportDAO.save(lFilesFinished, lValuesList);
		OperationImportWetterdatenOut lResult = this.entityToSchema.toOperationImportWetterdatenOut(
				lFilesFinished);
		return lResult;
	}
}
