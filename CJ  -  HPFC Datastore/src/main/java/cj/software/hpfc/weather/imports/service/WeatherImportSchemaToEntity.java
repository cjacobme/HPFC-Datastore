package cj.software.hpfc.weather.imports.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.lokation.schema.LokationDetail;
import cj.software.hpfc.lokation.service.LokationSchemaToEntity;
import cj.software.hpfc.weather.imports.entity.FilesFinished;
import cj.software.hpfc.weather.imports.entity.ImportDirectory;
import cj.software.hpfc.weather.imports.entity.MeteoMeasure;
import cj.software.hpfc.weather.imports.entity.WeatherValues;
import cj.software.hpfc.weather.imports.schema.Directory;
import cj.software.hpfc.weather.imports.schema.File;
import cj.software.hpfc.weather.imports.schema.Messgroesse;
import cj.software.hpfc.weather.imports.schema.OperationImportWetterdatenIn;
import cj.software.hpfc.weather.imports.schema.OperationMarkDirectoryFinishedIn;
import cj.software.hpfc.weather.imports.schema.WetterdatenFuerLokation;
import cj.software.hpfc.weather.imports.schema.WetterdatenFuerMessgroesse;
import cj.software.hpfc.weather.imports.schema.Zeitreihenwert;

@Dependent
public class WeatherImportSchemaToEntity
{
	@Inject
	private LokationSchemaToEntity lokationSchemaToEntity;

	public ImportDirectory toImportDirectory(OperationMarkDirectoryFinishedIn pIn)
	{
		ImportDirectory lResult = new ImportDirectory(pIn.getDirectory(), true);
		return lResult;
	}

	public List<WeatherValues> toValuesList(OperationImportWetterdatenIn pOperation)
	{
		List<WeatherValues> lResult = new ArrayList<>();
		OffsetDateTime lPrognoseZeitpunkt = pOperation.getPrognosezeitpunkt();
		Set<WetterdatenFuerLokation> lDatenFuerLokation = pOperation.getWetterdatenFuerLokation();
		for (WetterdatenFuerLokation bDatenFuerLokation : lDatenFuerLokation)
		{
			LokationDetail lSchemaLokation = bDatenFuerLokation.getLokation();
			Lokation lEntityLokation = this.lokationSchemaToEntity.toLokation(lSchemaLokation);
			Set<WetterdatenFuerMessgroesse> lWetterdatenFuerMessgroessen = bDatenFuerLokation
					.getWetterdatenFuerMessgroessen();
			for (WetterdatenFuerMessgroesse bDatenFuerMessgroesse : lWetterdatenFuerMessgroessen)
			{
				Messgroesse lMessgroesse = bDatenFuerMessgroesse.getMessgroesse();
				MeteoMeasure lMeteoMeasure = this.toMeteoMeasure(lMessgroesse);
				SortedSet<Zeitreihenwert> lZeitreihenwerte = bDatenFuerMessgroesse.getZeitreihenwerte();
				for (Zeitreihenwert bZeitreihenwert : lZeitreihenwerte)
				{
					OffsetDateTime lZeitpunkt = bZeitreihenwert.getZeitpunkt();
					double lValue = bZeitreihenwert.getWert();
					WeatherValues lEntry = new WeatherValues(lEntityLokation, lMeteoMeasure, lPrognoseZeitpunkt,
							lZeitpunkt, lValue);
					lResult.add(lEntry);
				}
			}
		}
		return lResult;
	}

	public MeteoMeasure toMeteoMeasure(Messgroesse pMessgroesse)
	{
		MeteoMeasure lResult;
		switch (pMessgroesse)
		{
		case Flux:
			lResult = MeteoMeasure.Flux;
			break;
		case Speed_Total:
			lResult = MeteoMeasure.Speed_Total;
			break;
		case Speed_U:
			lResult = MeteoMeasure.Speed_U;
			break;
		case Speed_V:
			lResult = MeteoMeasure.Speed_V;
			break;
		case Temperature:
			lResult = MeteoMeasure.Temperature;
			break;
		default:
			throw new IllegalArgumentException("unknown:" + pMessgroesse);
		}
		return lResult;
	}

	public FilesFinished toFilesFinished(OperationImportWetterdatenIn pOperation)
	{
		File lFile = pOperation.getImportFile();
		Directory lDirectory = lFile.getDirectory();
		FilesFinished lResult = new FilesFinished(lDirectory.getName(), lFile.getFileName());
		return lResult;
	}
}
