package cj.software.hpfc.weather.imports.entity;

import java.time.Instant;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "tmp_wind_v")
public class TmpWindV
{
	@PartitionKey(0)
	@Column(name = "lokation_bezeichnung")
	private String lokationBezeichnung;

	@PartitionKey(1)
	@Column(name = "prognosezeitpunkt", codec = InstantCodec.class)
	private Instant prognoseZeitpunkt;

	@PartitionKey(2)
	@Column(name = "zeitpunkt", codec = InstantCodec.class)
	private Instant zeitpunkt;

	@Column(name = "geogr_breite")
	private float geogrBreite;

	@Column(name = "geogr_laenge")
	private float geogrLaenge;

	@Column(name = "wert")
	private double wert;

	private TmpWindV()
	{
	}

	public TmpWindV(
			String pLokationBezeichnung,
			Instant pPrognoseZeitpunkt,
			Instant pZeitpunkt,
			float pGeogrBreite,
			float pGeogrLaenge,
			double pWert)
	{
		this();
		this.setLokationBezeichnung(pLokationBezeichnung);
		this.setPrognoseZeitpunkt(pPrognoseZeitpunkt);
		this.setZeitpunkt(pZeitpunkt);
		this.setGeogrBreite(pGeogrBreite);
		this.setGeogrLaenge(pGeogrLaenge);
		this.setWert(pWert);
	}

	public String getLokationBezeichnung()
	{
		return this.lokationBezeichnung;
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

	private void setPrognoseZeitpunkt(Instant pPrognoseZeitpunkt)
	{
		this.prognoseZeitpunkt = Objects.requireNonNull(pPrognoseZeitpunkt);
	}

	private void setZeitpunkt(Instant pZeitpunkt)
	{
		this.zeitpunkt = Objects.requireNonNull(pZeitpunkt);
	}

	private void setWert(double pWert)
	{
		this.wert = pWert;
	}

	private void setGeogrBreite(float pGeogrBreite)
	{
		this.geogrBreite = Objects.requireNonNull(pGeogrBreite);
	}

	private void setGeogrLaenge(float pGeogrLaenge)
	{
		this.geogrLaenge = Objects.requireNonNull(pGeogrLaenge);
	}

	@Override
	public String toString()
	{
		ToStringBuilder lBuilder = new ToStringBuilder(this);
		lBuilder
				.append("lokationBezeichnung", this.lokationBezeichnung)
				.append("prognoseZeitpunkt", this.prognoseZeitpunkt)
				.append("zeitpunkt", this.zeitpunkt)
				.append("wert", this.wert);
		return lBuilder.build();
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.lokationBezeichnung).append(this.prognoseZeitpunkt).append(
				this.zeitpunkt);
		int lResult = lBuilder.build();
		return lResult;
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof TmpWindV)
		{
			TmpWindV lOther = (TmpWindV) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder
					.append(this.lokationBezeichnung, lOther.lokationBezeichnung)
					.append(this.prognoseZeitpunkt, lOther.prognoseZeitpunkt)
					.append(this.zeitpunkt, lOther.zeitpunkt);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}
}
