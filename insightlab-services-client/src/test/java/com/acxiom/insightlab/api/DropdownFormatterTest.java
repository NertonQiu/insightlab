package com.acxiom.insightlab.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.acxiom.insightlab.api.formatter.DropdownFormatter;

public class DropdownFormatterTest {
	
	@Test
	public void testFormatJSONObjectWithRootNull() throws JSONException {
		
		final String rootName = null;
		JSONObject jsonToFormat = new JSONObject("{\"root\":{\"k\":\"v\"}}");
		
		JSONObject expectedObject = new JSONObject("{\"root\":{\"k\":\"v\"}}");
		
	
		
		DropdownFormatter.format(jsonToFormat, rootName, "k");
		Assert.assertEquals(expectedObject.toString(), jsonToFormat.toString());
	}


}
