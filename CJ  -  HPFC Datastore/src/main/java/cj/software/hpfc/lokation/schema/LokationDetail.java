package cj.software.hpfc.lokation.schema;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
public class LokationDetail
		implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String bezeichnung;

	@XmlElement(name = "geogr_breite")
	private float geogrBreite;

	@XmlElement(name = "geogr_laenge")
	private float geogrLaenge;

	private LokationDetail()
	{
	}

	public LokationDetail(String pBezeichnung, float pGeogrBreite, float pGeogrLaenge)
	{
		this();
		this.setBezeichnung(pBezeichnung);
		this.setGeogrBreite(pGeogrBreite);
		this.setGeogrLaenge(pGeogrLaenge);
	}

	public String getBezeichnung()
	{
		return this.bezeichnung;
	}

	private void setBezeichnung(String pBezeichnung)
	{
		this.bezeichnung = pBezeichnung;
	}

	public float getGeogrBreite()
	{
		return this.geogrBreite;
	}

	private void setGeogrBreite(float pGeogrBreite)
	{
		this.geogrBreite = pGeogrBreite;
	}

	public float getGeogrLaenge()
	{
		return this.geogrLaenge;
	}

	private void setGeogrLaenge(float pGeogrLaenge)
	{
		this.geogrLaenge = pGeogrLaenge;
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.bezeichnung);
		int lResult = lBuilder.build();
		return lResult;
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof LokationDetail)
		{
			LokationDetail lOther = (LokationDetail) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder.append(this.bezeichnung, lOther.bezeichnung);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}

	@Override
	public String toString()
	{
		ToStringBuilder lLBuilder = new ToStringBuilder(this);
		lLBuilder
				.append("bezeichnung", this.bezeichnung)
				.append("geogrBreite", this.geogrBreite)
				.append("geogrLaenge", this.geogrLaenge);
		return lLBuilder.build();
	}

}
