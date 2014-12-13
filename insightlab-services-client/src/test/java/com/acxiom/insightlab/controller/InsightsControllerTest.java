package com.acxiom.insightlab.controller;

import static com.acxiom.insightlab.test.Fakes.BOOLEAN;
import static com.acxiom.insightlab.test.Fakes.GLOBAL_PROFILE;
import static com.acxiom.insightlab.test.Fakes.INT;
import static com.acxiom.insightlab.test.Fakes.STRING;
import static com.acxiom.insightlab.test.Fakes.STRING_AS_INT;
import static com.acxiom.insightlab.test.Fakes.STRING_AS_UUID;
import static com.acxiom.insightlab.test.Fakes.TENANT_ID;

import java.io.IOException;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.service.impl.InsightsServiceImpl;
import com.acxiom.insightlab.service.SecurityUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class InsightsControllerTest {

	private InsightsController controller;
	private InsightsServiceImpl mock;

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
		mock = EasyMock.createMock(InsightsServiceImpl.class);
		controller = new InsightsController();

		Whitebox.setInternalState(controller, mock);
	}

	@Test
	public void testInsightList() throws Exception {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("p1", new String[] { "v1" });
		serviceParams.put("p2", new String[] { "v2" });
		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedServiceResponse = new JSONObject("{\"insights\": [{"
				+ "\"clientID\": \"1\"," + "\"status\": \"INSIGHT_COMPLETED\","
				+ "\"createdDate\": \"2013-05-30 08:53:28\","
				+ "\"modifiedDate\": \"2013-05-30 09:49:15\","
				+ "\"userID\": \"gpundi\"," + "\"size\": 1600004000,"
				+ "\"recordCount\": 400001," + "\"hasClientQuestions\": null,"
				+ "\"scored\": null," + "\"private\": null,"
				+ "\"id\": \"18248540551966399988\","
				+ "\"description\": \"400K-3\"," + "\"modeled\": false}],"
				+ "\"totalCount\": 9," + "\"storage\": \"8400024000\"}");

		EasyMock.expect(mock.getInsightsList(EasyMock.capture(capturedParams)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("p1", "v1");
		mockHttpServletRequest.addParameter("p2", "v2");

		JSONObject expectedResult = new JSONObject(
				"{\"storage\": \"8400024000\"," + "\"rows\": [{"
						+ "\"clientID\": \"1\","
						+ "\"status\": \"INSIGHT_COMPLETED\","

						+ "\"createdDate\": \"2013-05-30 08:53:28\","
						+ "\"modifiedDate\": \"2013-05-30 09:49:15\","
						+ "\"userID\": \"gpundi\"," + "\"size\": 1600004000,"
						+ "\"recordCount\": 400001,"
						+ "\"hasClientQuestions\": null," + "\"scored\": null,"
						+ "\"private\": null,"
						+ "\"id\": \"18248540551966399988\","
						+ "\"description\": \"400K-3\","
						+ "\"modeled\": false}]," + "\"totalCount\": 9}");

		final JSONObject dateTimeSettings = new JSONObject();
		dateTimeSettings.put("utcOffset", GLOBAL_PROFILE.getUtcOffset());
		dateTimeSettings.put("dateFormat", GLOBAL_PROFILE.getUserDateFormat());
		dateTimeSettings.put("timeFormat", GLOBAL_PROFILE.getUserTimeFormat());
		dateTimeSettings.put("timeZoneId", GLOBAL_PROFILE.getTimeZoneInfoID());
		expectedResult.put("settings", dateTimeSettings);

		EasyMock.expect(SecurityUtils.getGlobalProfile()).andReturn(
				GLOBAL_PROFILE);
		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.insightList("", "", false, "", false, "", "",
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		JSONObject actualResult = new JSONObject(
				mockHttpServletResponse.getContentAsString());
		Assert.assertNotNull(actualResult.optJSONObject("settings"));
		Assert.assertNotNull(actualResult.optJSONArray("rows"));

	}

	@Test
	public void testGetInsightsValuesList() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("p1", new String[] { "v1" });
		serviceParams.put("p2", new String[] { "v2" });
		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedServiceResponse = new JSONObject("{\"insights\": [{"
				+ "\"clientID\": \"1\"," + "\"status\": \"INSIGHT_COMPLETED\","
				+ "\"createdDate\": \"2013-05-30 08:53:28\","
				+ "\"modifiedDate\": \"2013-05-30 09:49:15\","
				+ "\"userID\": \"gpundi\"," + "\"size\": 1600004000,"
				+ "\"recordCount\": 400001," + "\"hasClientQuestions\": null,"
				+ "\"scored\": null," + "\"private\": null,"
				+ "\"id\": \"18248540551966399988\","
				+ "\"description\": \"400K-3\"," + "\"modeled\": false}],"
				+ "\"totalCount\": 9," + "\"storage\": \"8400024000\"}");

		EasyMock.expect(
				mock.getInsightsValuesList(EasyMock.capture(capturedParams)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("p1", "v1");
		mockHttpServletRequest.addParameter("p2", "v2");

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getInsightsValuesList("", "", false, "", false, "", "",
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		// TODO: add assertions.
		Assert.assertNotNull(mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testCount() throws JSONException, BaseDataApiException,
			IOException {

		JSONObject expectedServiceResponse = new JSONObject(
				"{\"insightRequested\": \"9\"," + "\"insightReady\": \"9\","
						+ "\"modelCreated\": \"11\","
						+ "\"totalRecordCount\": \"2100006\","
						+ "\"storageUsed\": \"8400024000\","
						+ "\"storagePurchased\": \"5000\"}");

		EasyMock.expect(
				mock.getInsightsCount(EasyMock.anyObject(JSONObject.class)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.count(mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedServiceResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testDeleteInsight() throws JSONException, BaseDataApiException,
			IOException {
		JSONObject serviceParams = new JSONObject();

		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });
		serviceParams
				.put("insightids", new String[] { "18248540551966399988" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedResponse = new JSONObject(
				"{\"result\":\"Deletion process successful\"}");

		EasyMock.expect(mock.deleteInsights(EasyMock.capture(capturedParams)))
				.andReturn(expectedResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.deleteInsight(new String[] { "18248540551966399988" },
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetClientInfo() throws JSONException, BaseDataApiException,
			IOException {
		JSONObject serviceParams = new JSONObject();

		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });

		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedResponse = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject objectJson = new JSONObject();
		objectJson.put("id", STRING);
		objectJson.put("description", STRING);
		objectJson.put("clientID", STRING);
		objectJson.put("status", STRING);
		objectJson.put("createdDate", STRING);
		objectJson.put("modifiedDate", STRING);
		objectJson.put("userID", STRING);
		objectJson.put("size", INT);
		objectJson.put("recordCount", INT);
		objectJson.put("hasClientQuestions", BOOLEAN);
		objectJson.put("private", STRING);
		objectJson.put("modeled", STRING);
		objectJson.put("scored", STRING);
		array.put(objectJson);
		expectedResponse.put("results", array);

		EasyMock.expect(mock.getClientInfo(EasyMock.capture(capturedParams)))
				.andReturn(expectedResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getClientInfo(mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testScoreAnInsight() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();

		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });
		serviceParams
				.put("insightids", new String[] { "00000000000000000000" });
		serviceParams.put("modelSource", new String[] { "C" });
		serviceParams.put("modelid", new String[] { "00000000000000000000" });

		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedResponse = new JSONObject();

		expectedResponse.put("results", STRING);

		EasyMock.expect(mock.scoreAnInsight(EasyMock.capture(capturedParams)))
				.andReturn(expectedResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.scoreAnInsight("00000000000000000000", "C",
				"00000000000000000000", mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetRecentInsights() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();

		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });

		serviceParams.put("count", new String[] { STRING_AS_INT });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedResponse = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject objectJson = new JSONObject();
		objectJson.put("id", STRING_AS_UUID);
		objectJson.put("description", STRING);
		objectJson.put("clientID", STRING_AS_INT);
		objectJson.put("status", STRING);
		objectJson.put("propensitiesExist", BOOLEAN);
		objectJson.put("source", STRING);
		objectJson.put("modifiedDate", STRING);
		objectJson.put("createdDate", STRING);
		objectJson.put("userID", STRING);
		objectJson.put("size", INT);
		objectJson.put("recordCount", INT);
		objectJson.put("hasClientQuestions", STRING);
		objectJson.put("private", STRING);
		objectJson.put("modeled", BOOLEAN);
		objectJson.put("scored", STRING);
		array.put(objectJson);
		expectedResponse.put("insights", array);

		EasyMock.expect(
				mock.getRecentInsights(EasyMock.capture(capturedParams)))
				.andReturn(expectedResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		EasyMock.expect(SecurityUtils.getGlobalProfile()).andReturn(
				GLOBAL_PROFILE);
		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getRecentInsights(INT, BOOLEAN, mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		// TODO: add more asserts
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

}
