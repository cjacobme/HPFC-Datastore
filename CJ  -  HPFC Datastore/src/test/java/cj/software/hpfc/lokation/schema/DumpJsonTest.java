package cj.software.hpfc.lokation.schema;

import org.junit.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DumpJsonTest
{
	@Test
	public void dumpLokationPostInput()
	{
		LokationDetail lDetail = new LokationDetail("Nordpol", 90.0f, 0.0f);
		LokationAddPostIn lInput = new LokationAddPostIn(lDetail);

		Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().setPrettyPrinting()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		System.out.println(gson.toJson(lInput));
	}
}
