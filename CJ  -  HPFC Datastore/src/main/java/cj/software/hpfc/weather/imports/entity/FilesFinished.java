package cj.software.hpfc.weather.imports.entity;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "files_finished")
public class FilesFinished implements Serializable
{
	private static final long serialVersionUID = 1L;

	@PartitionKey
	@Column(name = "directory_name")
	private String directoryName;

	@ClusteringColumn
	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_finished")
	private boolean fileFinished;

	private FilesFinished()
	{
	}

	public FilesFinished(String pDirectoryName, String pFileName)
	{
		this();
		this.setDirectoryName(pDirectoryName);
		this.setFileName(pFileName);
		this.setFileFinished(true);
	}

	public String getDirectoryName()
	{
		return this.directoryName;
	}

	private void setDirectoryName(String pDirectoryName)
	{
		this.directoryName = Objects.requireNonNull(pDirectoryName);
	}

	public String getFileName()
	{
		return this.fileName;
	}

	private void setFileName(String pFileName)
	{
		this.fileName = Objects.requireNonNull(pFileName);
	}

	public boolean isFileFinished()
	{
		return this.fileFinished;
	}

	private void setFileFinished(boolean pFileFinished)
	{
		this.fileFinished = pFileFinished;
	}

	@Override
	public String toString()
	{
		ToStringBuilder lBuilder = new ToStringBuilder(this);
		//@formatter:off
		lBuilder.append("directoryName", this.directoryName)
			.append("fileName", this.fileName)
			.append("fileFinished", this.fileFinished);
		//@formatter:on
		return lBuilder.build();
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.directoryName).append(this.fileName);
		int lResult = lBuilder.build();
		return lResult;
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof FilesFinished)
		{
			FilesFinished lOther = (FilesFinished) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder.append(this.directoryName, lOther.directoryName).append(this.fileName, lOther.fileName);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}
}
