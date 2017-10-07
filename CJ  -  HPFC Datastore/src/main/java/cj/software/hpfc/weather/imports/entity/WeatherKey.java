package cj.software.hpfc.weather.imports.entity;

import java.time.Instant;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WeatherKey
{
	private String lokationBezeichnung;

	private Instant prognoseZeitpunkt;

	private Instant zeitpunkt;

	private WeatherKey()
	{
	}

	public WeatherKey(String pLokationBezeichnung, Instant pPrognoseZeitpunkt, Instant pZeitpunkt)
	{
		this();
		this.setLokationBezeichnung(pLokationBezeichnung);
		this.setPrognoseZeitpunkt(pPrognoseZeitpunkt);
		this.setZeitpunkt(pZeitpunkt);
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

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof WeatherKey)
		{
			WeatherKey lOther = (WeatherKey) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder.append(this.lokationBezeichnung, lOther.lokationBezeichnung)
					.append(this.prognoseZeitpunkt, lOther.prognoseZeitpunkt).append(this.zeitpunkt, lOther.zeitpunkt);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.lokationBezeichnung).append(this.prognoseZeitpunkt).append(this.zeitpunkt);
		int lResult = lBuilder.build();
		return lResult;
	}

	@Override
	public String toString()
	{
		ToStringBuilder lBuilder = new ToStringBuilder(this);
		lBuilder.append("lokationBezeichnung", this.lokationBezeichnung)
				.append("prognoseZeitpunkt", this.prognoseZeitpunkt).append("zeitpunkt", this.zeitpunkt);
		return lBuilder.build();
	}

}
