package cj.software.hpfc.lokation.schema;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lokation-get-output")
public class LokationGetOut implements Serializable
{
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "lokation-details")
	private LokationDetail detail;

	private LokationGetOut()
	{
	}

	public LokationGetOut(LokationDetail pDetail)
	{
		this();
		this.setDetail(pDetail);
	}

	public LokationDetail getDetail()
	{
		return this.detail;
	}

	private void setDetail(LokationDetail pDetail)
	{
		this.detail = Objects.requireNonNull(pDetail);
	}
}
