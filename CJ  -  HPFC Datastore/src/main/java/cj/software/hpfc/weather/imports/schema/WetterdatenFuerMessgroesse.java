package cj.software.hpfc.weather.imports.schema;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
public class WetterdatenFuerMessgroesse
{
	private Messgroesse messgroesse;

	@XmlElementWrapper(name = "zeitreihenwerte")
	@XmlElement(name = "zeitreihenwert")
	private SortedSet<Zeitreihenwert> zeitreihenWerte = new TreeSet<>();

	private WetterdatenFuerMessgroesse()
	{
	}

	public WetterdatenFuerMessgroesse(Messgroesse pMessgroesse)
	{
		this();
		this.setMessgroesse(pMessgroesse);
	}

	public Messgroesse getMessgroesse()
	{
		return this.messgroesse;
	}

	private void setMessgroesse(Messgroesse pMessgroesse)
	{
		this.messgroesse = pMessgroesse;
	}

	public boolean addZeitreihenwert(Zeitreihenwert pZeitreihenwert)
	{
		boolean lResult = this.zeitreihenWerte.add(pZeitreihenwert);
		return lResult;
	}

	public SortedSet<Zeitreihenwert> getZeitreihenwerte()
	{
		return Collections.unmodifiableSortedSet(this.zeitreihenWerte);
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.messgroesse);
		int lResult = lBuilder.build();
		return lResult;
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof WetterdatenFuerMessgroesse)
		{
			WetterdatenFuerMessgroesse lOther = (WetterdatenFuerMessgroesse) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder.append(this.messgroesse, lOther.messgroesse);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}
}
