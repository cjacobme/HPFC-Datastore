package cj.software.hpfc.lokation.schema;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import cj.software.hpfc.weather.imports.schema.OperationMarkDirectoryFinishedIn;
import cj.software.hpfc.weather.imports.schema.OperationMarkFileFinishedIn;

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
	public void dumpOperationMarkFileFinishedIn() throws JAXBException
	{
		OperationMarkFileFinishedIn lOperation = new OperationMarkFileFinishedIn("Verzeichnis 1909", "Ole.txt");
		this.dump(lOperation);
	}
}
