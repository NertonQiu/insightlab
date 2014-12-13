package com.acxiom.insightlab.api.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.acxiom.insightlab.api.BaseDataApiException;

public class JsonUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testStringToJSONObject() throws BaseDataApiException, JSONException {
		String string = "text";
		
		JSONObject expected = new JSONObject("{\"results\":\"text\"}");
		JSONObject actual =  JsonUtils.stringToJSONObject(string);
		
		Assert.assertEquals(expected.toString(), actual.toString());
		
		expected = new JSONObject("{\"field\":\"value\"}");
		actual =  JsonUtils.stringToJSONObject("{\"field\":\"value\"}");
		Assert.assertEquals(expected.toString(), actual.toString());
		
		expected = new JSONObject("{\"results\":[\"test1\",\"test2\"]}");
		actual =  JsonUtils.stringToJSONObject("[\"test1\",\"test2\"]");
		Assert.assertEquals(expected.toString(), actual.toString());
	}

}
