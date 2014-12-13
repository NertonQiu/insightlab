package com.acxiom.insightlab.api.service.impl;

import static com.acxiom.insightlab.test.Fakes.CLIENT_ID;
import static com.acxiom.insightlab.test.Fakes.CREATED_DATE;
import static com.acxiom.insightlab.test.Fakes.DESCRIPTION;
import static com.acxiom.insightlab.test.Fakes.HAS_CLIENT_QUESTIONS;
import static com.acxiom.insightlab.test.Fakes.ID;
import static com.acxiom.insightlab.test.Fakes.MODELED;
import static com.acxiom.insightlab.test.Fakes.MODIFIED_DATE;
import static com.acxiom.insightlab.test.Fakes.PRIVATE;
import static com.acxiom.insightlab.test.Fakes.PROPERTIES_EXISTS;
import static com.acxiom.insightlab.test.Fakes.RECORD_COUNT;
import static com.acxiom.insightlab.test.Fakes.SCORED;
import static com.acxiom.insightlab.test.Fakes.SIZE;
import static com.acxiom.insightlab.test.Fakes.SOURCE_IL;
import static com.acxiom.insightlab.test.Fakes.STATUS_COMPLETED;
import static com.acxiom.insightlab.test.Fakes.STATUS_PREP;
import static com.acxiom.insightlab.test.Fakes.STORAGE;
import static com.acxiom.insightlab.test.Fakes.TOTAL_COUNT;
import static com.acxiom.insightlab.test.Fakes.USER_ID;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.dao.impl.InsightsDAOImpl;

public class InsightsServiceImplTest {

	private InsightsDAOImpl mockDao;
	private InsightsServiceImpl service;

	@Before
	public void setUp() throws Exception {
		service = new InsightsServiceImpl();
		mockDao = EasyMock.createStrictMock(InsightsDAOImpl.class);
		Whitebox.setInternalState(service, mockDao);
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

		expect(mockDao.getInsightsList(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.getInsightsList(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

	@Test
	public void testGetInsightsValuesList() throws JSONException,
			BaseDataApiException {
		JSONObject expectedResult = new JSONObject();
		JSONArray insightsList = new JSONArray();

		JSONObject preparedInsight = new JSONObject();
		preparedInsight.put("id", ID);
		preparedInsight.put("description", DESCRIPTION);
		preparedInsight.put("clientID", CLIENT_ID);
		preparedInsight.put("status", STATUS_PREP);
		preparedInsight.put("propensitiesExist", PROPERTIES_EXISTS);
		preparedInsight.put("source", SOURCE_IL);
		preparedInsight.put("createdDate", CREATED_DATE);
		preparedInsight.put("modifiedDate", MODIFIED_DATE);
		preparedInsight.put("userID", USER_ID);
		preparedInsight.put("size", SIZE);
		preparedInsight.put("recordCount", RECORD_COUNT);
		preparedInsight.put("hasClientQuestions", HAS_CLIENT_QUESTIONS);
		preparedInsight.put("private", PRIVATE);
		preparedInsight.put("modeled", MODELED);
		preparedInsight.put("scored", SCORED);

		JSONObject completedInsight = new JSONObject();
		completedInsight.put("id", ID);
		completedInsight.put("description", DESCRIPTION);
		completedInsight.put("clientID", CLIENT_ID);
		completedInsight.put("status", STATUS_COMPLETED);
		completedInsight.put("propensitiesExist", PROPERTIES_EXISTS);
		completedInsight.put("source", SOURCE_IL);
		completedInsight.put("createdDate", CREATED_DATE);
		completedInsight.put("modifiedDate", MODIFIED_DATE);
		completedInsight.put("userID", USER_ID);
		completedInsight.put("size", SIZE);
		completedInsight.put("recordCount", RECORD_COUNT);
		completedInsight.put("hasClientQuestions", HAS_CLIENT_QUESTIONS);
		completedInsight.put("private", PRIVATE);
		completedInsight.put("modeled", MODELED);
		completedInsight.put("scored", SCORED);

		insightsList.put(preparedInsight);
		insightsList.put(preparedInsight);
		insightsList.put(completedInsight);

		expectedResult.put("insights", insightsList);
		expectedResult.put("totalCount", TOTAL_COUNT);
		expectedResult.put("storage", STORAGE);

		JSONObject params = new JSONObject();
		params.put("skipcount", new String[] { "8" });
		params.put("clientid", new String[] { "1" });

		expect(mockDao.getInsightsValuesList(params)).andReturn(expectedResult);

		replay(mockDao);
		JSONObject actual = service.getInsightsValuesList(params);
		final int onlyOneCompletedInsight = 1;
		assertEquals(onlyOneCompletedInsight, actual.getJSONArray("insights")
				.length());
		verify(mockDao);
	}

	@Test
	public void testDeleteInsights() throws JSONException, BaseDataApiException {
		JSONObject expected = new JSONObject(
				"{\"result\":\"Deletion process successful\"}");

		JSONObject params = new JSONObject();
		params.put("insightids", new String[] { "1" });
		params.put("clientid", new String[] { "1" });
		params.put("userid", new String[] { "gpundi" });

		expect(mockDao.deleteInsights(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.deleteInsights(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

	@Test
	public void testGetClientInfo() throws JSONException, BaseDataApiException {
		JSONObject expected = new JSONObject(
				"{\"result\":[{\"clientID\": null," + "\"status\": null,"
						+ "\"createdDate\": null," + "\"modifiedDate\": null,"
						+ "\"userID\": null," + "\"size\": 0,"
						+ "\"recordCount\": 0,"
						+ "\"hasClientQuestions\": true,"
						+ "\"private\": null," + "\"scored\": null,"
						+ "\"id\": \"9420349289060219214\","
						+ "\"description\": \"20130423_2\","
						+ "\"modeled\": null},{" + "\"clientID\": null,"
						+ "\"status\": null," + "\"createdDate\": null,"
						+ "\"modifiedDate\": null," + "\"userID\": null,"
						+ "\"size\": 0," + "\"recordCount\": 0,"
						+ "\"hasClientQuestions\": true,"
						+ "\"private\": null," + "\"scored\": null,"
						+ "\"id\": \"9527109650906052587\","
						+ "\"description\": \"8\"," + "\"modeled\": null}]}");

		JSONObject params = new JSONObject();
		params.put("clientid", new String[] { "1" });
		params.put("userid", new String[] { "gpundi" });

		expect(mockDao.getClientInfo(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.getClientInfo(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

	@Test
	public void testScoreAnInsight() throws JSONException, BaseDataApiException {
		final JSONObject expected = new JSONObject("{}");

		JSONObject params = new JSONObject();
		params.put("insightid", new String[] { "11167426386044717876" });
		params.put("modelsource", new String[] { "testsource" });
		params.put("modelid", new String[] { "1" });

		expect(mockDao.scoreAnInsight(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.scoreAnInsight(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

	@Test
	public void testGetRecentInsights() throws JSONException,
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

		expect(mockDao.getRecentInsights(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.getRecentInsights(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

}
