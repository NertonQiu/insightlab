package com.acxiom.insightlab.api.dao.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.acxiom.insightlab.api.ApiClientJson;
import com.acxiom.insightlab.api.ApiClientJsonImpl;
import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.constant.InsightsServiceURIs;

public class InsightsDAOImplTest {

	private ApiClientJson mockApiClient;
	private InsightsDAOImpl insightsService;

	@Before
	public void setUp() throws Exception {
		insightsService = new InsightsDAOImpl();
		mockApiClient = EasyMock.createStrictMock(ApiClientJsonImpl.class);
		insightsService.setApiClient(mockApiClient);

	}

	@Test
	public void testSetApiClient() {
		InsightsDAOImpl service = new InsightsDAOImpl();
		ApiClientJsonImpl apiClient = new ApiClientJsonImpl();
		service.setApiClient(apiClient);
		assertEquals(apiClient, service.getApiClient());
	}

	@Test
	public void testGetInsightsList() throws JSONException,
			BaseDataApiException {

		JSONObject expected = new JSONObject("{\"insights\": [{"
				+ "\"clientID\": \"1\"," + "\"status\": \"INSIGHT_COMPLETED\","
				+ "\"createdDate\": \"2013-05-30 08:53:28\","
				+ "\"modifiedDate\": \"2013-05-30 09:49:15\","
				+ "\"userID\": \"gpundi\"," + "\"size\": 1600004000,"
				+ "\"recordCount\": 400001," + "\"hasClientQuestions\": null,"
				+ "\"scored\": null," + "\"private\": null,"
				+ "\"id\": \"18248540551966399988\","
				+ "\"description\": \"400K-3\"," + "\"modeled\": false}],"
				+ "\"totalCount\": 9," + "\"storage\": \"8400024000\"}");

		JSONObject params = new JSONObject();
		params.put("skipcount", new String[] { "8" });
		params.put("clientid", new String[] { "1" });

		expect(mockApiClient.get(InsightsServiceURIs.INSIGHT_LIST, params))
				.andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = insightsService.getInsightsList(params);
		assertEquals(expected, actual);
		verify(mockApiClient);

	}

	@Test
	public void testGetInsightsValuesList() throws JSONException, BaseDataApiException {
		JSONObject expected = new JSONObject("{\"insights\": [{"
				+ "\"clientID\": \"1\"," + "\"status\": \"INSIGHT_COMPLETED\","
				+ "\"createdDate\": \"2013-05-30 08:53:28\","
				+ "\"modifiedDate\": \"2013-05-30 09:49:15\","
				+ "\"userID\": \"gpundi\"," + "\"size\": 1600004000,"
				+ "\"recordCount\": 400001," + "\"hasClientQuestions\": null,"
				+ "\"scored\": null," + "\"private\": null,"
				+ "\"id\": \"18248540551966399988\","
				+ "\"description\": \"400K-3\"," + "\"modeled\": false}],"
				+ "\"totalCount\": 9," + "\"storage\": \"8400024000\"}");

		JSONObject params = new JSONObject();
		params.put("skipcount", new String[] { "8" });
		params.put("clientid", new String[] { "1" });

		expect(mockApiClient.get(InsightsServiceURIs.INSIGHT_LIST, params))
				.andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = insightsService.getInsightsList(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetInsightsCount() throws JSONException,
			BaseDataApiException {
		JSONObject expected = new JSONObject("{\"insightRequested\": \"9\","
				+ "\"insightReady\": \"9\"," + "\"modelCreated\": \"11\","
				+ "\"totalRecordCount\": \"2100006\","
				+ "\"storageUsed\": \"8400024000\","
				+ "\"storagePurchased\": \"5000\"}");

		JSONObject params = new JSONObject();
		params.put("tenantid", new String[] { "7fbf1573-6e61-45b6-9073-bab7b6b44443" });

		expect(
				mockApiClient.get(InsightsServiceURIs.INSIGHTS_COUNT + "/7fbf1573-6e61-45b6-9073-bab7b6b44443",
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = insightsService.getInsightsCount(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testDeleteInsights() throws JSONException, BaseDataApiException {
		JSONObject expected = new JSONObject(
				"{\"result\":\"Deletion process successful\"}");

		JSONObject params = new JSONObject();
		params.put("insightids", new String[] { "1" });
		params.put("clientid", new String[] { "7fbf1573-6e61-45b6-9073-bab7b6b44443" });
		params.put("userid", new String[] { "gpundi" });

		expect(mockApiClient.put(InsightsServiceURIs.DELETE_INSIGHTS, params))
				.andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = insightsService.deleteInsights(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetClientInfo() throws JSONException, BaseDataApiException {
		JSONObject expected = new JSONObject(
				"{\"result\":[{\"clientID\": null,"+
		        "\"status\": null,"+
		        "\"createdDate\": null,"+
		        "\"modifiedDate\": null,"+
		        "\"userID\": null,"+
		        "\"size\": 0,"+
		        "\"recordCount\": 0,"+
		        "\"hasClientQuestions\": true,"+
		        "\"private\": null,"+
		        "\"scored\": null,"+
		        "\"id\": \"9420349289060219214\","+
		        "\"description\": \"20130423_2\","+
		        "\"modeled\": null},{"+
		        	"\"clientID\": null,"+
		        "\"status\": null,"+
		        "\"createdDate\": null,"+
		        "\"modifiedDate\": null,"+
		        "\"userID\": null,"+
		        "\"size\": 0,"+
		        "\"recordCount\": 0,"+
		        "\"hasClientQuestions\": true,"+
		        "\"private\": null,"+
		        "\"scored\": null,"+
		        "\"id\": \"9527109650906052587\","+
		        "\"description\": \"8\","+
		        "\"modeled\": null}]}");

		JSONObject params = new JSONObject();
		params.put("clientid", new String[] { "1" });
		params.put("userid", new String[] { "gpundi" });

		expect(mockApiClient.get(InsightsServiceURIs.CLIENT_INFO, params))
				.andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = insightsService.getClientInfo(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
		
	}

	@Test
	public void testScoreAnInsight() throws JSONException, BaseDataApiException {
		
		final JSONObject expected = new JSONObject("{}");

		JSONObject params = new JSONObject();
		params.put("insightid", new String[] { "11167426386044717876" });
		params.put("modelsource", new String[] { "testsource" });
		params.put("modelid", new String[] { "1" });

		expect(
				mockApiClient.post(InsightsServiceURIs.SCORE,
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = insightsService.scoreAnInsight(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
		
	}

	@Test
	public void testGetResentInsights() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject("{\"insights\": [" + "{"
				+ "\"clientID\": \"117\","
				+ "\"status\": \"INSIGHT_COMPLETED\","
				+ "\"createdDate\": \"2013-04-24 09:51:23\","
				+ "\"modifiedDate\": \"2013-04-24 09:57:04\","
				+ "\"userID\": \"kenich\"," + "\"size\": 39996000,"
				+ "\"recordCount\": 9999," + "\"hasClientQuestions\": null,"
				+ "\"private\": null," + "\"scored\": false,"
				+ "\"id\": \"11013573005114598210\","
				+ "\"description\": \"The next test\"," + "\"modeled\": true"
				+ "}]," + "\"totalCount\": 0," + "\"storage\": null}");

		JSONObject params = new JSONObject();
		params.put("count", new String[] { "1" });
		params.put("clientid", new String[] { "1" });
		params.put("userid", new String[] { "1" });

		expect(
				mockApiClient.get(InsightsServiceURIs.GET_RECENT_INSIGHTS,
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = insightsService.getRecentInsights(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

}
