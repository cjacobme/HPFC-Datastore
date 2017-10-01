package cj.software.hpfc.lokation.service;

import java.util.List;

import javax.enterprise.context.Dependent;

import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.lokation.schema.LokationAddPostOut;
import cj.software.hpfc.lokation.schema.LokationDetail;
import cj.software.hpfc.lokation.schema.LokationGetOut;
import cj.software.hpfc.lokation.schema.LokationenGetOut;

@Dependent
public class EntityToSchema
{
	public EntityToSchema()
	{
	}

	public LokationenGetOut toLokationenGetOut(List<String> pBezeichnungen)
	{
		LokationenGetOut lResult = new LokationenGetOut(pBezeichnungen);
		return lResult;
	}

	public LokationGetOut toLokationGetOut(Lokation pDetails)
	{
		LokationDetail lDetail = new LokationDetail(pDetails.getBezeichnung(), pDetails.getGeogrBreite(),
				pDetails.getGeogrLaenge());
		LokationGetOut lResult = new LokationGetOut(lDetail);
		return lResult;
	}

	public LokationAddPostOut toLokationAddPostOut(String pId)
	{
		LokationAddPostOut lResult = new LokationAddPostOut(pId);
		return lResult;
	}
}
