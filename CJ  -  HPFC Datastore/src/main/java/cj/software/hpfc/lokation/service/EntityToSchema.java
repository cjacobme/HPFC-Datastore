package cj.software.hpfc.lokation.service;

import java.util.List;

import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.lokation.schema.LokationAddPostOut;
import cj.software.hpfc.lokation.schema.LokationDetail;
import cj.software.hpfc.lokation.schema.LokationGetOut;
import cj.software.hpfc.lokation.schema.LokationenGetOut;

public class EntityToSchema
{
	public LokationenGetOut toLokationenGetOut(List<String> pBezeichnungen)
	{
		LokationenGetOut lResult = new LokationenGetOut(pBezeichnungen);
		return lResult;
	}

	public LokationGetOut toLokationGetOut(Lokation pDetails)
	{
		LokationDetail lDetail = new LokationDetail(pDetails.getBezeichnung(), pDetails.getGeogrBreite(),
				pDetails.getGeogrHoehe());
		LokationGetOut lResult = new LokationGetOut(lDetail);
		return lResult;
	}

	public LokationAddPostOut toLokationAddPostOut(String pId)
	{
		LokationAddPostOut lResult = new LokationAddPostOut(pId);
		return lResult;
	}
}
