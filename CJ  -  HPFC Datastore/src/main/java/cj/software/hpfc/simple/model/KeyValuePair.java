package cj.software.hpfc.simple.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class KeyValuePair implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String key;

	private String value;

	public KeyValuePair(String pKey, String pValue)
	{
		this.setKey(pKey);
		this.setValue(pValue);
	}

	public String getKey()
	{
		return this.key;
	}

	private void setKey(String pKey)
	{
		this.key = pKey;
	}

	public String getValue()
	{
		return this.value;
	}

	private void setValue(String pValue)
	{
		this.value = pValue;
	}

	@Override
	public String toString()
	{
		ToStringBuilder lBuilder = new ToStringBuilder(this);
		lBuilder.append("key", this.key).append("value", this.value);
		return lBuilder.build();
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.key);
		int lResult = lBuilder.build();
		return lResult;
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof KeyValuePair)
		{
			KeyValuePair lOther = (KeyValuePair) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder.append(this.key, lOther.key);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}
}
