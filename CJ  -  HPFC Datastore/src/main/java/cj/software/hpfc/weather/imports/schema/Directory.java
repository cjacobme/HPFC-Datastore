package cj.software.hpfc.weather.imports.schema;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
public class Directory implements Serializable, Comparable<Directory>
{
	private static final long serialVersionUID = 1L;

	private String name;

	private boolean finished;

	private Directory()
	{
	}

	public Directory(String pName)
	{
		this(pName, false);
	}

	public Directory(String pName, boolean pFinished)
	{
		this();
		this.setName(pName);
		this.setFinished(pFinished);
	}

	public String getName()
	{
		return this.name;
	}

	private void setName(String pName)
	{
		this.name = pName;
	}

	public boolean isFinished()
	{
		return this.finished;
	}

	private void setFinished(boolean pFinished)
	{
		this.finished = pFinished;
	}

	@Override
	public String toString()
	{
		ToStringBuilder lBuilder = new ToStringBuilder(this);
		lBuilder.append("name", this.name).append("finished", this.finished);
		return lBuilder.build();
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.name);
		int lResult = lBuilder.build();
		return lResult;
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof Directory)
		{
			Directory lOther = (Directory) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder.append(this.name, lOther.name);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}

	@Override
	public int compareTo(Directory pOther)
	{
		CompareToBuilder lBuilder = new CompareToBuilder();
		lBuilder.append(this.name, pOther.name);
		int lResult = lBuilder.build();
		return lResult;
	}
}
