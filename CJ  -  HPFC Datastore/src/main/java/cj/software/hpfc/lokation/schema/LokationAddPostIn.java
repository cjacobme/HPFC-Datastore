package cj.software.hpfc.lokation.schema;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lokation-post-in")
@XmlAccessorType(XmlAccessType.FIELD)
public class LokationAddPostIn
		implements Serializable
{
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "lokation-detail")
	private LokationDetail lokationDetail;

	private LokationAddPostIn()
	{
	}

	public LokationAddPostIn(LokationDetail pLokationDetail)
	{
		this();
		this.setLokationDetail(pLokationDetail);
	}

	public LokationDetail getLokationDetail()
	{
		return this.lokationDetail;
	}

	private void setLokationDetail(LokationDetail pLokationDetail)
	{
		this.lokationDetail = Objects.requireNonNull(pLokationDetail);
	}
}
