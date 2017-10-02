package cj.software.hpfc.weather.imports.service;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cj.software.hpfc.weather.imports.dao.WeatherImportDAO;
import cj.software.hpfc.weather.imports.entity.ImportDirectory;
import cj.software.hpfc.weather.imports.schema.DirectoriesListGetOut;

@Path("/weather/imports")
@Produces(
{ MediaType.APPLICATION_XML })
@Dependent
public class WeatherImportService
{
	@Inject
	private WeatherImportDAO weatherImportDAO;

	@Inject
	private WeatherImportEntityToSchema entityToSchema;

	@GET
	public DirectoriesListGetOut readIfDirectoriesFinished(@QueryParam("dirname") String... pDirectoryNames)
	{
		List<ImportDirectory> lRead = this.weatherImportDAO.listDirectories(pDirectoryNames);
		DirectoriesListGetOut lResult = this.entityToSchema.toDirectoriesListGetOut(lRead);
		return lResult;
	}
}
