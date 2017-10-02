package cj.software.hpfc.lokation.service;

import javax.enterprise.context.Dependent;

import cj.software.hpfc.lokation.entity.Lokation;
import cj.software.hpfc.lokation.schema.LokationAddPostIn;
import cj.software.hpfc.lokation.schema.LokationDetail;

@Dependent
public class LokationSchemaToEntity
{
	public Lokation toLokation(LokationAddPostIn pIn)
	{
		LokationDetail lDetail = pIn.getLokationDetail();
		Lokation lResult = this.toLokation(lDetail);
		return lResult;
	}

	public Lokation toLokation(LokationDetail pLokationDetail)
	{
		Lokation lResult = new Lokation(pLokationDetail.getBezeichnung(), pLokationDetail.getGeogrBreite(),
				pLokationDetail.getGeogrLaenge());
		return lResult;
	}
}
