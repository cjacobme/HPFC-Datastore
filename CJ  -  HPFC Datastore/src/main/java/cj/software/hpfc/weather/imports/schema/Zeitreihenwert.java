package cj.software.hpfc.weather.imports.schema;

import java.time.OffsetDateTime;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.migesok.jaxb.adapter.javatime.OffsetDateTimeXmlAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Zeitreihenwert
		implements Comparable<Zeitreihenwert>
{
	@XmlJavaTypeAdapter(OffsetDateTimeXmlAdapter.class)
	private OffsetDateTime zeitpunkt;

	private double wert;

	private Zeitreihenwert()
	{
	}

	public Zeitreihenwert(OffsetDateTime pZeitpunkt, double pWert)
	{
		this();
		this.setZeitpunkt(pZeitpunkt);
		this.setWert(pWert);
	}

	public OffsetDateTime getZeitpunkt()
	{
		return this.zeitpunkt;
	}

	private void setZeitpunkt(OffsetDateTime pZeitpunkt)
	{
		this.zeitpunkt = Objects.requireNonNull(pZeitpunkt);
	}

	public double getWert()
	{
		return this.wert;
	}

	private void setWert(double pWert)
	{
		this.wert = pWert;
	}

	@Override
	public String toString()
	{
		ToStringBuilder lLBuilder = new ToStringBuilder(this);
		lLBuilder.append("zeitpunkt", this.zeitpunkt).append("wert", this.wert);
		return lLBuilder.build();
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.zeitpunkt);
		int lResult = lBuilder.build();
		return lResult;
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof Zeitreihenwert)
		{
			Zeitreihenwert lOther = (Zeitreihenwert) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder.append(this.zeitpunkt, lOther.zeitpunkt);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}

	@Override
	public int compareTo(Zeitreihenwert pOther)
	{
		//@formatter:off
		CompareToBuilder lBuilder = new CompareToBuilder()
				.append(this.zeitpunkt, pOther.zeitpunkt);
		//@formatter:on
		int lResult = lBuilder.build();
		return lResult;
	}
}
