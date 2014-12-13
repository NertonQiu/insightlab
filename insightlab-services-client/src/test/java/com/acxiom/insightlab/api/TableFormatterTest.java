package com.acxiom.insightlab.api;

import static com.acxiom.insightlab.test.Fakes.DATE_TIME_SETTINGS;
import static com.acxiom.insightlab.test.Fakes.GLOBAL_PROFILE;

import org.easymock.EasyMock;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.acxiom.insightlab.api.formatter.TableFormatter;
import com.acxiom.insightlab.service.SecurityUtils;
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class TableFormatterTest {

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
	}

	@Test
	public void testFormatJSONObjectStringString() throws JSONException {

		final String rowsKey = "rowsKey";
		JSONObject jsonToFormat = new JSONObject(
				"{\"rowsKey\":[{\"a1\":\"b1\",\"a2\":\"b2\"},{\"a1\":\"c1\",\"a2\":\"c2\"}]}");

		JSONObject expectedObject = new JSONObject(
				"{\"rows\":[{\"a1\":\"b1\",\"a2\":\"b2\"},{\"a1\":\"c1\",\"a2\":\"c2\"}]}");
		expectedObject.put("settings", DATE_TIME_SETTINGS);
		EasyMock.expect(SecurityUtils.getGlobalProfile()).andReturn(
				GLOBAL_PROFILE);
		PowerMock.replayAll();

		TableFormatter.format(jsonToFormat, rowsKey);
		Assert.assertEquals(expectedObject.toString(), jsonToFormat.toString());
	}

}
