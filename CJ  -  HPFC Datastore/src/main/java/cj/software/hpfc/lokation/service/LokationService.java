package cj.software.hpfc.lokation.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cj.software.hpfc.lokation.dao.LokationenDAO;
import cj.software.hpfc.lokation.model.LokationenGetOut;

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
}
