package cj.software.hpfc.lokation.service;

import java.util.List;

import cj.software.hpfc.lokation.model.LokationenGetOut;

public class EntityToSchema
{
	public LokationenGetOut toLokationenGetOut(List<String> pBezeichnungen)
	{
		LokationenGetOut lResult = new LokationenGetOut(pBezeichnungen);
		return lResult;
	}
}
