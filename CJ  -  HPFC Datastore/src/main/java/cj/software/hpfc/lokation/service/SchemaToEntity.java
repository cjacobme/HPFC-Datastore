package cj.software.hpfc.lokation.service;

import javax.enterprise.context.Dependent;

import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.lokation.schema.LokationAddPostIn;
import cj.software.hpfc.lokation.schema.LokationDetail;

@Dependent
public class SchemaToEntity
{
	public Lokation toLokationDetail(LokationAddPostIn pIn)
	{
		LokationDetail lDetail = pIn.getLokationDetail();
		Lokation lResult = new Lokation(lDetail.getBezeichnung(), lDetail.getGeogrBreite(), lDetail.getGeogrHoehe());
		return lResult;
	}
}
