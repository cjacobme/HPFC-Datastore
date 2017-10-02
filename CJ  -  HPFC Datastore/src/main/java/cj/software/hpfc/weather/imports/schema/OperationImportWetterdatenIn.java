package cj.software.hpfc.weather.imports.schema;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.migesok.jaxb.adapter.javatime.OffsetDateTimeXmlAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationImportWetterdatenIn
{
	@XmlElement(name = "import-file")
	private File importFile;

	@XmlElement(name = "prognose-zeitpunkt")
	@XmlJavaTypeAdapter(OffsetDateTimeXmlAdapter.class)
	private OffsetDateTime prognosezeitpunkt;

	@XmlElementWrapper(name = "lokationen")
	@XmlElement(name = "wetterdaten-fuer-lokation")
	private Set<WetterdatenFuerLokation> lokationsdaten = new HashSet<>();

	private OperationImportWetterdatenIn()
	{
	}

	public OperationImportWetterdatenIn(File pImportFile, OffsetDateTime pPrognosezeitpunkt)
	{
		this();
		this.setImportFile(pImportFile);
		this.setPrognosezeitpunkt(pPrognosezeitpunkt);
	}

	public File getImportFile()
	{
		return this.importFile;
	}

	private void setImportFile(File pImportFile)
	{
		this.importFile = Objects.requireNonNull(pImportFile);
	}

	public OffsetDateTime getPrognosezeitpunkt()
	{
		return this.prognosezeitpunkt;
	}

	private void setPrognosezeitpunkt(OffsetDateTime pPrognosezeitpunkt)
	{
		this.prognosezeitpunkt = Objects.requireNonNull(pPrognosezeitpunkt);
	}

	public boolean addWetterdatenFuerLokation(WetterdatenFuerLokation pDaten)
	{
		boolean lResult = this.lokationsdaten.add(pDaten);
		return lResult;
	}

	public Set<WetterdatenFuerLokation> getWetterdatenFuerLokation()
	{
		return Collections.unmodifiableSet(this.lokationsdaten);
	}
}
