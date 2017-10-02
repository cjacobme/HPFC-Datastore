package cj.software.hpfc.weather.imports.schema;

import java.util.Objects;
import java.util.SortedSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "directories-list-get-out")
public class DirectoriesListGetOut
{
	@XmlElementWrapper(name = "directories")
	@XmlElement(name = "directory")
	private SortedSet<Directory> directories;

	private DirectoriesListGetOut()
	{
	}

	public DirectoriesListGetOut(SortedSet<Directory> pDirectories)
	{
		this();
		this.setDirectories(pDirectories);
	}

	public SortedSet<Directory> getDirectories()
	{
		return this.directories;
	}

	private void setDirectories(SortedSet<Directory> pDirectories)
	{
		this.directories = Objects.requireNonNull(pDirectories);
	}
}
