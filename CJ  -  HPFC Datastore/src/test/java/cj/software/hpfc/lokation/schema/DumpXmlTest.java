package cj.software.hpfc.lokation.schema;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import cj.software.hpfc.weather.imports.schema.Directory;
import cj.software.hpfc.weather.imports.schema.File;
import cj.software.hpfc.weather.imports.schema.Messgroesse;
import cj.software.hpfc.weather.imports.schema.OperationImportWetterdatenIn;
import cj.software.hpfc.weather.imports.schema.OperationMarkDirectoryFinishedIn;
import cj.software.hpfc.weather.imports.schema.WetterdatenFuerLokation;
import cj.software.hpfc.weather.imports.schema.WetterdatenFuerMessgroesse;
import cj.software.hpfc.weather.imports.schema.Zeitreihenwert;

public class DumpXmlTest
{
	@Test
	public void dumpLokationPostInput() throws JAXBException
	{
		LokationDetail lDetail = new LokationDetail("Nordpol", 90.0f, 0.0f);
		LokationAddPostIn lInput = new LokationAddPostIn(lDetail);
		this.dump(lInput);
	}

	private void dump(Object pObject) throws JAXBException
	{
		Class<?> lClass = pObject.getClass();
		System.out.println("============ " + lClass.getSimpleName() + " ============");
		JAXBContext lCtx = JAXBContext.newInstance(lClass);
		Marshaller lMarshaller = lCtx.createMarshaller();
		lMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		lMarshaller.marshal(pObject, System.out);
		System.out.println();
	}

	@Test
	public void dumpOperationMarkDirectoryFinishedIn() throws JAXBException
	{
		OperationMarkDirectoryFinishedIn lRoot = new OperationMarkDirectoryFinishedIn("Verzeichnis 4711");
		this.dump(lRoot);
	}

	@Test
	public void dumpImportWetterdaten() throws JAXBException
	{
		Directory lDirectory = new Directory("Verzeichnis 1909");
		File lImportFile = new File(lDirectory, "Ole Bvb!", true);
		OperationImportWetterdatenIn lOperation = new OperationImportWetterdatenIn(lImportFile,
				OffsetDateTime.of(2017, 9, 23, 6, 0, 0, 0, ZoneOffset.UTC));
		lOperation.addWetterdatenFuerLokation(this.createWetterdatenFuerLokation("Essen", 51.45f, 7.01f));
		lOperation.addWetterdatenFuerLokation(this.createWetterdatenFuerLokation("Nordkap", 71.17f, 25.78f));
		this.dump(lOperation);
	}

	private WetterdatenFuerLokation createWetterdatenFuerLokation(
			String pLokationBezeichnung,
			float pGeogrBreite,
			float pGeogrLaenge)
	{
		LokationDetail lLokation = new LokationDetail(pLokationBezeichnung, pGeogrBreite, pGeogrLaenge);
		WetterdatenFuerLokation lResult = new WetterdatenFuerLokation(lLokation);
		int lBias = 0;
		for (Messgroesse bMessgroesse : Messgroesse.values())
		{
			lResult.addWetterdatenFuerMessgroessen(this.createWetterdatenFuerMessgroesse(bMessgroesse, lBias));
			lBias++;
		}
		return lResult;
	}

	private WetterdatenFuerMessgroesse createWetterdatenFuerMessgroesse(Messgroesse pMessgroesse, int pBias)
	{
		WetterdatenFuerMessgroesse lResult = new WetterdatenFuerMessgroesse(pMessgroesse);

		OffsetDateTime lZeitpunkt = OffsetDateTime.of(2017, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC);
		double lValue = pBias + 1.42;
		lResult.addZeitreihenwert(new Zeitreihenwert(lZeitpunkt, lValue));

		for (int bI = 0; bI < 4; bI++)
		{
			lZeitpunkt = lZeitpunkt.plusHours(1);
			lValue += 0.1;
			lResult.addZeitreihenwert(new Zeitreihenwert(lZeitpunkt, lValue));
		}
		return lResult;
	}
}
