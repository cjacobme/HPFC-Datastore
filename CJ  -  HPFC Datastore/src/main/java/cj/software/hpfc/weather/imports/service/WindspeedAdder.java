package cj.software.hpfc.weather.imports.service;

import java.time.ZoneOffset;
import java.util.Map;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.weather.imports.dao.WeatherImportDAO;
import cj.software.hpfc.weather.imports.entity.MeteoMeasure;
import cj.software.hpfc.weather.imports.entity.TmpWindU;
import cj.software.hpfc.weather.imports.entity.TmpWindV;
import cj.software.hpfc.weather.imports.entity.WeatherKey;
import cj.software.hpfc.weather.imports.entity.WeatherValues;

@Singleton
public class WindspeedAdder
{
	private Logger logger = LogManager.getFormatterLogger();

	@Inject
	private WeatherImportDAO weatherImportDAO;

	@Schedule(second = "10", minute = "*/1", hour = "*", persistent = false)
	public void calcTotalSpeeds()
	{
		Map<WeatherKey, TmpWindU> lTmpWindUs = this.weatherImportDAO.getTmpWindU();
		Map<WeatherKey, TmpWindV> lTmpWindVs = this.weatherImportDAO.getTmpWindV();
		for (WeatherKey bWeatherKey : lTmpWindUs.keySet())
		{
			if (lTmpWindVs.containsKey(bWeatherKey))
			{
				TmpWindU lTmpWindU = lTmpWindUs.get(bWeatherKey);
				TmpWindV lTmpWindV = lTmpWindVs.get(bWeatherKey);
				WeatherValues lTotalWind = this.calcTotalWind(lTmpWindU, lTmpWindV);
				this.weatherImportDAO.saveForTotalWindSpeed(lTotalWind, lTmpWindU, lTmpWindV);
				this.logger.info("%s done", bWeatherKey);
			}
			else
			{
				this.logger.warn("%s only in U-Direction", bWeatherKey);
			}
		}
	}

	private WeatherValues calcTotalWind(TmpWindU pTmpWindU, TmpWindV pTmpWindV)
	{
		double lWertU = pTmpWindU.getWert();
		double lWertV = pTmpWindV.getWert();
		double lWertTotal = Math.sqrt(lWertU * lWertU + lWertV * lWertV);
		Lokation lLokation = new Lokation(pTmpWindU.getLokationBezeichnung(), pTmpWindU.getGeogrBreite(),
				pTmpWindU.getGeogrLaenge());
		WeatherValues lResult = new WeatherValues(lLokation, MeteoMeasure.Speed_Total,
				pTmpWindU.getPrognoseZeitpunkt().atOffset(ZoneOffset.UTC),
				pTmpWindU.getZeitpunkt().atOffset(ZoneOffset.UTC), lWertTotal);
		return lResult;
	}
}
