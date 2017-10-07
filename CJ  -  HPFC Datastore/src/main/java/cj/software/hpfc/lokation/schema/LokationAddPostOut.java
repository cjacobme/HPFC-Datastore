package cj.software.hpfc.lokation.schema;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lokation-post-out")
@XmlAccessorType(XmlAccessType.FIELD)
public class LokationAddPostOut
		implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String bezeichnung;

	private LokationAddPostOut()
	{
	}

	public LokationAddPostOut(String pBezeichnung)
	{
		this();
		this.setBezeichnung(pBezeichnung);
	}

	public String getBezeichnung()
	{
		return this.bezeichnung;
	}

	private void setBezeichnung(String pBezeichnung)
	{
		this.bezeichnung = pBezeichnung;
	}
}
