package cj.software.hpfc.lokation.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cj.software.hpfc.lokation.dao.LokationenDAO;
import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.lokation.schema.LokationGetOut;
import cj.software.hpfc.lokation.schema.LokationenGetOut;

@Path("/lokationen")
@Produces(MediaType.APPLICATION_JSON)
public class LokationService
{
	private LokationenDAO dao = new LokationenDAO();

	private EntityToSchema entityToSchema = new EntityToSchema();

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
}
