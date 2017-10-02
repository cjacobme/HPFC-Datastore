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

@Table(name = "directory_finished")
public class ImportDirectory implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final int BUCKET_NO = 1;

	@PartitionKey
	private int bucket;

	@ClusteringColumn
	@Column(name = "directory_name")
	private String directoryName;

	@Column(name = "directory_finished")
	private boolean finished;

	private ImportDirectory()
	{
		this.bucket = BUCKET_NO;
	}

	public ImportDirectory(String pDirectoryName, boolean pFinished)
	{
		this();
		this.setDirectoryName(pDirectoryName);
		this.setFinished(pFinished);
	}

	public String getDirectoryName()
	{
		return this.directoryName;
	}

	private void setDirectoryName(String pDirectoryName)
	{
		this.directoryName = Objects.requireNonNull(pDirectoryName);
	}

	public boolean isFinished()
	{
		return this.finished;
	}

	private void setFinished(boolean pFinished)
	{
		this.finished = pFinished;
	}

	@Override
	public String toString()
	{
		ToStringBuilder lBuilder = new ToStringBuilder(this);
		lBuilder.append("directoryName", this.directoryName).append("finished", this.finished);
		return lBuilder.build();
	}

	@Override
	public boolean equals(Object pOther)
	{
		boolean lResult;
		if (pOther instanceof ImportDirectory)
		{
			ImportDirectory lOther = (ImportDirectory) pOther;
			EqualsBuilder lBuilder = new EqualsBuilder();
			lBuilder.append(this.directoryName, lOther.directoryName);
			lResult = lBuilder.build();
		}
		else
		{
			lResult = false;
		}
		return lResult;
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder lBuilder = new HashCodeBuilder();
		lBuilder.append(this.directoryName);
		int lResult = lBuilder.build();
		return lResult;
	}
}
