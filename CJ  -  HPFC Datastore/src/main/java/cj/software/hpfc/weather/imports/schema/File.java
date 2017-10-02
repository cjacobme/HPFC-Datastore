package cj.software.hpfc.weather.imports.schema;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class File
{
	private Directory directory;

	@XmlElement(name = "file-name")
	private String fileName;

	@XmlElement
	private boolean finished;

	private File()
	{
	}

	public File(Directory pDirectory, String pFileName, boolean pFinished)
	{
		this();
		this.setDirectory(pDirectory);
		this.setFileName(pFileName);
		this.setFinished(pFinished);
	}

	public Directory getDirectory()
	{
		return this.directory;
	}

	private void setDirectory(Directory pDirectory)
	{
		this.directory = Objects.requireNonNull(pDirectory);
	}

	public String getFileName()
	{
		return this.fileName;
	}

	private void setFileName(String pFileName)
	{
		this.fileName = Objects.requireNonNull(pFileName);
	}

	public boolean isFinished()
	{
		return this.finished;
	}

	private void setFinished(boolean pFinished)
	{
		this.finished = pFinished;
	}

}
