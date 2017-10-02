package cj.software.hpfc.weather.imports.schema;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationImportWetterdatenOut
{
	private File file;

	private OperationImportWetterdatenOut()
	{
	}

	public OperationImportWetterdatenOut(File pFile)
	{
		this();
		this.setFile(pFile);
	}

	public File getFile()
	{
		return this.file;
	}

	private void setFile(File pFile)
	{
		this.file = Objects.requireNonNull(pFile);
	}
}
