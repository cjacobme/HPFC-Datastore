package cj.software.hpfc.lokation.schema;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lokationen-get-output")
@XmlAccessorType(XmlAccessType.FIELD)
public class LokationenGetOut implements Serializable
{
	private static final long serialVersionUID = 1L;

	@XmlElementWrapper(name = "bezeichnungen")
	private List<String> bezeichnungen;

	private LokationenGetOut()
	{
	}

	public LokationenGetOut(List<String> pBezeichnungen)
	{
		this();
		this.setBezeichnungen(pBezeichnungen);
	}

	public List<String> getBezeichnungen()
	{
		return Collections.unmodifiableList(this.bezeichnungen);
	}

	private void setBezeichnungen(List<String> pBezeichnungen)
	{
		this.bezeichnungen = pBezeichnungen;
	}

}
