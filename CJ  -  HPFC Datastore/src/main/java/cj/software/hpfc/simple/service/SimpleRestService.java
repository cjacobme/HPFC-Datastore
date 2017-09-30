package cj.software.hpfc.simple.service;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cj.software.hpfc.simple.model.KeyValuePair;

@Path("/simple")
public class SimpleRestService
{
	private static SortedSet<KeyValuePair> myEntries = new TreeSet<>();

	public SimpleRestService()
	{
		if (myEntries.isEmpty())
		{
			myEntries.add(new KeyValuePair("Key #1", "Value #1"));
			myEntries.add(new KeyValuePair("Key #2", "this is the value #2"));
		}
	}

	@GET
	@Path("/text")
	public String getHello()
	{
		return "Hello World";
	}

	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public KeyValuePair json()
	{
		KeyValuePair lResult = new KeyValuePair("Key #1", "This is the value for Key #1");
		return lResult;
	}

	@GET
	@Path("/managed")
	@Produces(MediaType.APPLICATION_JSON)
	public SortedSet<KeyValuePair> getMyEntries()
	{
		return SimpleRestService.myEntries;
	}

	@POST
	@Path("/managed")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public KeyValuePair add(KeyValuePair pNew)
	{
		boolean lSuccess = myEntries.add(pNew);
		if (lSuccess)
		{
			return pNew;
		}
		else
		{
			throw new IllegalArgumentException("already there");
		}
	}
}
