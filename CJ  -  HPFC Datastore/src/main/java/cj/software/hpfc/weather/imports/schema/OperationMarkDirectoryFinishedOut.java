package cj.software.hpfc.weather.imports.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "operation-mark-directory-finished-out")
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationMarkDirectoryFinishedOut
{
	private Directory directory;

	private OperationMarkDirectoryFinishedOut()
	{
	}

	public OperationMarkDirectoryFinishedOut(Directory pDirectory)
	{
		this();
		this.setDirectory(pDirectory);
	}

	public Directory getDirectory()
	{
		return this.directory;
	}

	private void setDirectory(Directory pDirectory)
	{
		this.directory = pDirectory;
	}

}
