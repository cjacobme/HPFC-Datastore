package cj.software.hpfc.lokation.schema;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import cj.software.hpfc.weather.imports.schema.OperationMarkDirectoryFinishedIn;

public class DumpXmlTest
{
	@Test
	public void dumpLokationPostInput() throws JAXBException
	{
		LokationDetail lDetail = new LokationDetail("Nordpol", 90.0f, 0.0f);
		LokationAddPostIn lInput = new LokationAddPostIn(lDetail);
		this.dump(LokationAddPostIn.class, lInput);
	}

	private void dump(Class<?> pClass, Object pObject) throws JAXBException
	{
		System.out.println("============ " + pClass.getSimpleName() + " ============");
		JAXBContext lCtx = JAXBContext.newInstance(pClass);
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
		this.dump(OperationMarkDirectoryFinishedIn.class, lRoot);
	}
}
