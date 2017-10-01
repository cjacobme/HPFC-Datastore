package cj.software.hpfc.lokation.service;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cj.software.hpfc.lokation.dao.LokationenDAO;
import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.lokation.schema.LokationAddPostIn;
import cj.software.hpfc.lokation.schema.LokationAddPostOut;
import cj.software.hpfc.lokation.schema.LokationGetOut;
import cj.software.hpfc.lokation.schema.LokationenGetOut;

@Path("/lokationen")
@Produces(MediaType.APPLICATION_JSON)
@Dependent
public class LokationService
{
	private LokationenDAO dao = new LokationenDAO();

	private EntityToSchema entityToSchema = new EntityToSchema();

	@Inject
	private SchemaToEntity schemaToEntity;

	@GET
	public LokationenGetOut getLokationen()
	{
		List<String> lBezeichnungen = this.dao.readLokationBezeichnungen();
		LokationenGetOut lResult = this.entityToSchema.toLokationenGetOut(lBezeichnungen);
		return lResult;
	}

	@GET
	@Path("{bezeichnung}")
	public LokationGetOut getLokationDetail(@PathParam("bezeichnung") String pSearchedBezeichnung)
	{
		Lokation lGelesen = this.dao.readLokationDetails(pSearchedBezeichnung);
		LokationGetOut lResult = this.entityToSchema.toLokationGetOut(lGelesen);
		return lResult;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public LokationAddPostOut addNewLokation(LokationAddPostIn pSource)
	{
		Lokation lLokation = this.schemaToEntity.toLokationDetail(pSource);
		String lReturned = this.dao.addLokation(lLokation);
		LokationAddPostOut lResult = this.entityToSchema.toLokationAddPostOut(lReturned);
		return lResult;
	}
}
