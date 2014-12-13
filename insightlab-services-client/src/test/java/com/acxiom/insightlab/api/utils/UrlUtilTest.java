package com.acxiom.insightlab.api.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static com.acxiom.insightlab.test.Fakes.FLOAT;
import com.acxiom.insightlab.api.BaseDataApiException;

public class UrlUtilTest {


	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testJsonParamsToQueryString() throws JSONException,
			BaseDataApiException {
		JSONArray array = new JSONArray();
		array.put("1");
		JSONObject params = new JSONObject();
		params.put("param1", array);
		params.put("param2", "null");
		params.put("param3", new String[] { "value3" });
		params.put("param4", 1);
		params.put("param5", "value5");
		params.put("param6", new String[] { "value1", "value2" });
		params.put("param7", FLOAT);
		String queryString = UrlUtil.jsonParamsToQueryString(params);
		String expected = "param1=1&param2=null&param3=value3&param4=1&param5=value5&param6=value1&param6=value2&param7=5.0";
		Assert.assertEquals(expected, queryString);
	}

	@Test
	public void testBuildUrl() throws MalformedURLException, JSONException,
			BaseDataApiException {
		JSONObject params = new JSONObject();
		params.put("param1", new String[] { "value1" });
		params.put("param2", new String[] { "value2" });
		params.put("param3", new String[] { "value3" });
		params.put("param4", new String[] { "value4" });
		params.put("param5", new String[] { "value5" });

		final String endPoint = "http://example.com";
		final String path = "/application/path";
		@SuppressWarnings("deprecation")
		URL url = UrlUtil.buildUrl(endPoint, path, params);

		URL expected = new URL(
				"http://example.com/application/path?param1=value1&param2=value2&param3=value3&param4=value4&param5=value5");
		Assert.assertEquals(expected, url);
	}

}
