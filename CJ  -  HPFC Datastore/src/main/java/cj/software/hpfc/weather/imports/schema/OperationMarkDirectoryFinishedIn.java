package cj.software.hpfc.weather.imports.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "operation-mark-directory-finished-in")
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationMarkDirectoryFinishedIn
{
	@XmlElement(name = "directory")
	private String directory;

	private OperationMarkDirectoryFinishedIn()
	{
	}

	public OperationMarkDirectoryFinishedIn(String pDirectoryName)
	{
		this();
		this.setDirectory(pDirectoryName);
	}

	public String getDirectory()
	{
		return this.directory;
	}

	private void setDirectory(String pDirectory)
	{
		this.directory = pDirectory;
	}
}
