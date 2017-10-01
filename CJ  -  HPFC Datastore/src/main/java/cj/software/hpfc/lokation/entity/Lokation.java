package cj.software.hpfc.lokation.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "lokation")
public class Lokation
{
	@PartitionKey
	private int bucket;

	@ClusteringColumn
	private String bezeichnung;

	@Column(name = "geogr_breite")
	private float geogrBreite;

	@Column(name = "geogr_hoehe")
	private float geogrHoehe;

	private Lokation()
	{
		this.bucket = 1;
	}

	public Lokation(String pBezeichnung, float pGeogrBreite, float pGeogrHoehe)
	{
		this();
		this.setBezeichnung(pBezeichnung);
		this.setGeogrBreite(pGeogrBreite);
		this.setGeogrHoehe(pGeogrHoehe);
	}

	public String getBezeichnung()
	{
		return this.bezeichnung;
	}

	public void setBezeichnung(String pBezeichnung)
	{
		this.bezeichnung = pBezeichnung;
	}

	public float getGeogrBreite()
	{
		return this.geogrBreite;
	}

	public void setGeogrBreite(float pGeogrBreite)
	{
		this.geogrBreite = pGeogrBreite;
	}

	public float getGeogrHoehe()
	{
		return this.geogrHoehe;
	}

	public void setGeogrHoehe(float pGeogrHoehe)
	{
		this.geogrHoehe = pGeogrHoehe;
	}

	@Override
	public String toString()
	{
		ToStringBuilder lBuilder = new ToStringBuilder(this);
		//@formatter:off
		lBuilder.append("bezeichnung", this.bezeichnung)
			.append("geogrBreite", this.geogrBreite)
			.append("geogrHoehe", this.geogrHoehe);
		//@formatter:on
		return lBuilder.build();
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof Lokation)
		{
			Lokation lOther = (Lokation) pOther;
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
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.bezeichnung);
		int lResult = lBuilder.build();
		return lResult;
	}
}
