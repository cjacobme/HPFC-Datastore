package cj.software.hpfc.simple.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import cj.software.hpfc.simple.model.KeyValuePair;

@Path("/simple")
public class SimpleRestService
{
	@GET
	@Path("/text")
	public String getHello()
	{
		return "Hello World";
	}

	@GET
	@Path("/json")
	public KeyValuePair json()
	{
		KeyValuePair lResult = new KeyValuePair("Key #1", "This is the value for Key #1");
		return lResult;
	}
}
