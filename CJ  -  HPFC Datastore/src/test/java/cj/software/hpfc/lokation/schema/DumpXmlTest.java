package cj.software.hpfc.lokation.schema;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

public class DumpXmlTest
{
	@Test
	public void dumpLokationPostInput() throws JAXBException
	{
		LokationDetail lDetail = new LokationDetail("Nordpol", 90.0f, 0.0f);
		LokationAddPostIn lInput = new LokationAddPostIn(lDetail);

		JAXBContext lCtx = JAXBContext.newInstance(LokationAddPostIn.class);
		Marshaller lMarshaller = lCtx.createMarshaller();
		lMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		lMarshaller.marshal(lInput, System.out);
	}
}
