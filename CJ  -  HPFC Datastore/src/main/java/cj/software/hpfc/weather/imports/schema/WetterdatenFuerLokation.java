package cj.software.hpfc.weather.imports.schema;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import cj.software.hpfc.lokation.schema.LokationDetail;

@XmlAccessorType(XmlAccessType.FIELD)
public class WetterdatenFuerLokation
{
	@XmlElement(name = "lokation")
	private LokationDetail lokation;

	@XmlElementWrapper(name = "messgroessen")
	@XmlElement(name = "wetterdaten-fuer-messgroessen")
	private Set<WetterdatenFuerMessgroesse> wetterdatenFuerMessgroessen = new HashSet<>();

	private WetterdatenFuerLokation()
	{
	}

	public WetterdatenFuerLokation(LokationDetail pLokation)
	{
		this();
		this.setLokation(pLokation);
	}

	public LokationDetail getLokation()
	{
		return this.lokation;
	}

	private void setLokation(LokationDetail pLokation)
	{
		this.lokation = pLokation;
	}

	public boolean addWetterdatenFuerMessgroessen(WetterdatenFuerMessgroesse pDaten)
	{
		boolean lResult = this.wetterdatenFuerMessgroessen.add(pDaten);
		return lResult;
	}

	public Set<WetterdatenFuerMessgroesse> getWetterdatenFuerMessgroessen()
	{
		return Collections.unmodifiableSet(this.wetterdatenFuerMessgroessen);
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.lokation);
		int lResult = lBuilder.build();
		return lResult;
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof WetterdatenFuerLokation)
		{
			WetterdatenFuerLokation lOther = (WetterdatenFuerLokation) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder.append(this.lokation, lOther.lokation);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}
}
