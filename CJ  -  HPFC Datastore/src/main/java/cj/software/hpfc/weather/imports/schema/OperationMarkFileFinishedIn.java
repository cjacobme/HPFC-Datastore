package cj.software.hpfc.weather.imports.schema;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "operation-mark-file-finished-in")
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationMarkFileFinishedIn
{
	@XmlElement(name = "directory-name")
	private String dirName;

	@XmlElement(name = "file-name")
	private String fileName;

	private OperationMarkFileFinishedIn()
	{
	}

	public OperationMarkFileFinishedIn(String pDirName, String pFileName)
	{
		this();
		this.setDirName(pDirName);
		this.setFileName(pFileName);
	}

	public String getDirName()
	{
		return this.dirName;
	}

	private void setDirName(String pDirName)
	{
		this.dirName = Objects.requireNonNull(pDirName);
	}

	public String getFileName()
	{
		return this.fileName;
	}

	private void setFileName(String pFileName)
	{
		this.fileName = Objects.requireNonNull(pFileName);
	}
}
