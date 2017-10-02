package cj.software.hpfc.weather.imports.entity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;

import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import cj.software.hpfc.lokation.entity.Lokation;

@Table(name = "weather_values")
public class WeatherValues
{
	@PartitionKey(0)
	@Column(name = "lokation_bezeichnung")
	private String lokationBezeichnung;

	@PartitionKey(1)
	@Column(name = "meteo_measure")
	private MeteoMeasure meteoMeasure;

	@PartitionKey(2)
	@Column(name = "prognosezeitpunkt", codec = InstantCodec.class)
	private Instant prognoseZeitpunkt;

	@ClusteringColumn
	@Column(name = "zeitpunkt", codec = InstantCodec.class)
	private Instant zeitpunkt;

	@Column(name = "geogr_breite")
	private float geogrBreite;

	@Column(name = "geogr_laenge")
	private float geogrLaenge;

	@Column(name = "wert")
	private double wert;

	private WeatherValues()
	{
	}

	public WeatherValues(Lokation pLokation, MeteoMeasure pMeteoMeasure, OffsetDateTime pPrognoseZeitpunkt,
			OffsetDateTime pZeitpunkt, double pWert)
	{
		this();
		this.setLokationBezeichnung(pLokation.getBezeichnung());
		this.setMeteoMeasure(pMeteoMeasure);
		this.setPrognoseZeitpunkt(pPrognoseZeitpunkt.toInstant());
		this.setZeitpunkt(pZeitpunkt.toInstant());
		this.setGeogrBreite(pLokation.getGeogrBreite());
		this.setGeogrLaenge(pLokation.getGeogrLaenge());
		this.setWert(pWert);
	}

	public String getLokationBezeichnung()
	{
		return this.lokationBezeichnung;
	}

	public MeteoMeasure getMeteoMeasure()
	{
		return this.meteoMeasure;
	}

	public Instant getPrognoseZeitpunkt()
	{
		return this.prognoseZeitpunkt;
	}

	public Instant getZeitpunkt()
	{
		return this.zeitpunkt;
	}

	public float getGeogrBreite()
	{
		return this.geogrBreite;
	}

	public float getGeogrLaenge()
	{
		return this.geogrLaenge;
	}

	public double getWert()
	{
		return this.wert;
	}

	private void setLokationBezeichnung(String pLokationBezeichnung)
	{
		this.lokationBezeichnung = Objects.requireNonNull(pLokationBezeichnung);
	}

	private void setMeteoMeasure(MeteoMeasure pMeteoMeasure)
	{
		this.meteoMeasure = Objects.requireNonNull(pMeteoMeasure);
	}

	private void setPrognoseZeitpunkt(Instant pPrognoseZeitpunkt)
	{
		this.prognoseZeitpunkt = Objects.requireNonNull(pPrognoseZeitpunkt);
	}

	private void setZeitpunkt(Instant pZeitpunkt)
	{
		this.zeitpunkt = Objects.requireNonNull(pZeitpunkt);
	}

	private void setGeogrBreite(float pGeogrBreite)
	{
		this.geogrBreite = pGeogrBreite;
	}

	private void setGeogrLaenge(float pGeogrLaenge)
	{
		this.geogrLaenge = pGeogrLaenge;
	}

	private void setWert(double pWert)
	{
		this.wert = pWert;
	}

}
