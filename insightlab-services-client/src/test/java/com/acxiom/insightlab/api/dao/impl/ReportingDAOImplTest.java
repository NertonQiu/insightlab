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
import com.acxiom.insightlab.api.constant.ReportingServiceURIs;

public class ReportingDAOImplTest {

	private ApiClientJson mockApiClient;
	private ReportingDAOImpl reportingDao;

	@Before
	public void setUp() throws Exception {
		reportingDao = new ReportingDAOImpl();
		mockApiClient = EasyMock.createStrictMock(ApiClientJsonImpl.class);
		reportingDao.setApiClient(mockApiClient);

	}

	@Test
	public void testSetApiClient() {
		ReportingDAOImpl service = new ReportingDAOImpl();
		ApiClientJsonImpl apiClient = new ApiClientJsonImpl();
		service.setApiClient(apiClient);
		assertEquals(apiClient, service.getApiClient());
	}

	@Test
	public void testSavePersonicxTargetGroup() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"result\": \"Status Updated Successfully\"}");

		JSONObject params = new JSONObject();

		params.put("insightid", new String[] { "18248540551966399988" });
		params.put("username", new String[] { "gpundi" });
		params.put("groupname", new String[] { "test group" });
		params.put("segment", new String[] { "test segment" });
		params.put("referenceid", new String[] { "1" });

		expect(
				mockApiClient.post(ReportingServiceURIs.SAVE_PERSONICX_TARGET_GROUP,
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao.savePersonicxTargetGroup(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetPersonicxPortraitReport() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject("{\"id\": \"1\","
				+ "\"InsightDescription\": \"insight one\","
				+ "\"ReferenceQuestion\": \"National\","
				+ "\"ReferenceResponse\": null," + "\"portraitData\": [{"
				+ "\"ib1270\": 1," + "\"clusterName\": \"Summit Estates\","
				+ "\"groupName\": \"Fortunes & Families\","
				+ "\"maritalStatus\": \"Married\"," + "\"age\": \"36-55\","
				+ "\"income\": \"Wealthy\"," + "\"netWorth\": \"$2MM+\","
				+ "\"homeOwnership\": \"Owner\","
				+ "\"kids\": \"School-age Kids\"," + "\"groupCode\": \"11B\","
				+ "\"urbanicity\": \"City & Surrounds\","
				+ "\"ageText\": \"Career\"," + "\"BaseCount\": 3248775,"
				+ "\"TargetCount\": 661," + "\"BaseTotal\": 234511935,"
				+ "\"TargetTotal\": 22971," + "\"BasePercent\": 0.01385335,"
				+ "\"TargetPercent\": 0.028775412,"
				+ "\"Penetration\": 0.01385335," + "\"Index\": 207,"
				+ "\"ProbabilityIndex\": \"Highly Likely\","
				+ "\"ZScore\": 19.3475884764893,"
				+ "\"BubbleSize\": \"MEDIUM\"" + "}]}");

		JSONObject params = new JSONObject();

		params.put("insightid", new String[] { "18248540551966399988" });
		params.put("referenceid", new String[] { "1" });
		params.put("portraitid", new String[] { "1" });

		expect(
				mockApiClient.get(
						ReportingServiceURIs.GET_PERSONICX_PORTRAIT_REPORT,
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao.getPersonicxPortraitReport(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetScattergramReport() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"insightID\": \"1\","
						+ "\"insightDescription\": \"insight one\","
						+ "\"referenceQuestion\": \"Responsiveness To Ads Across Media Segments\","
						+ "\"referenceResponse\": \"Ad Adverse\","
						+ "\"scattergramData\": [{" + "\"ib1270\": 1,"
						+ "\"clusterName\": \"Summit Estates\","
						+ "\"groupName\": null,"
						+ "\"maritalStatus\": \"Married\","
						+ "\"age\": \"36-55\"," + "\"income\": \"Wealthy\","
						+ "\"netWorth\": \"$2MM+\","
						+ "\"homeOwnership\": \"Owner\","
						+ "\"kids\": \"School-age Kids\","
						+ "\"groupCode\": null,"
						+ "\"urbanicity\": \"City & Surrounds\","
						+ "\"ageText\": null," + "\"index1\": 207,"
						+ "\"index2\": 78,"
						+ "\"referencePercent\": 0.01385335,"
						+ "\"quadrant\": \"Strengths\","
						+ "\"chartIndex1\": 207," + "\"chartIndex2\": 78}]}");

		JSONObject params = new JSONObject();

		params.put("insightid", new String[] { "18248540551966399988" });
		params.put("referenceid", new String[] { "1" });

		expect(
				mockApiClient.get(ReportingServiceURIs.GET_SCATTERGRAM_REPORT,
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao.getScattergramReport(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetDPACategories() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject("{\"result\": [{"
				+ "\"name\": \"Personicx Digital\"},{"
				+ "\"name\": \"Demographic\"},{"
				+ "\"name\": \"Social Media\"},{"
				+ "\"name\": \"Economic Assessment\"},{"
				+ "\"name\": \"Interest\"},{"
				+ "\"name\": \"Financial Models\"},{"
				+ "\"name\": \"Audience Propensities\"},{"
				+ "\"name\": \"Geographic\"}]}");

		JSONObject params = new JSONObject();
		expect(
				mockApiClient.get(ReportingServiceURIs.GET_DPA_CATEGORIES,
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao.getDPACategories(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetDPAQuestions() throws JSONException,
			BaseDataApiException {

		final JSONObject expected = new JSONObject(
				"{\"result\": [{\"QuestionID\": 147,"
						+ "\"questionText\": \"Economic Stability Indicator\"},{"
						+ "\"questionID\": 148,"
						+ "\"questionText\": \"UnderBanked Indicator\"},{"
						+ "\"questionID\": 149,"
						+ "\"questionText\": \"NetWorth - Gold\"},{"
						+ "\"questionID\": 150,"
						+ "\"questionText\": \"HeavyTransactors\"}]}");

		JSONObject params = new JSONObject();

		params.put("category", new String[] { "Geographic" });

		expect(
				mockApiClient.get(ReportingServiceURIs.GET_DPA_QUESTIONS,
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao.getDPAQuestions(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetDPAResponses() throws JSONException,
			BaseDataApiException {

		final JSONObject expected = new JSONObject(
				"{\"result\": [{\"responseID\": 887,"
						+ "\"responseValue\": null,"
						+ "\"responseText\": \"Less than $1\"},{"
						+ "\"responseID\": 888," + "\"responseValue\": null,"
						+ "\"responseText\": \"$1 - $4,999\"}]}");

		JSONObject params = new JSONObject();
		params.put("insightid", new String[] { "18248540551966399988" });
		params.put("category", new String[] { "Geographic" });

		expect(mockApiClient.get(ReportingServiceURIs.GET_DPA_REPORT, params))
				.andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao.getDPAReport(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetDPAReport() throws JSONException, BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"DPAReport\": {"
						+ "\"Category\": \"Financial_Models\","
						+ "\"Reference\": \"US\","
						+ "\"reportData\": [{"
						+ "\"Index\": 11,"
						+ "\"QuestionID\": 2,"
						+ "\"QuestionText\": \"Brand Propensities\","
						+ "\"ResponseID\": 1683,"
						+ "\"ResponseText\": \"L'Oreal Revitalift Facial Moisturizer\"},],"
						+ "\"ReportType\": \"A\"," + "\"Target\":\"tippy\"}}");

		JSONObject params = new JSONObject();
		params.put("insightid", new String[] { "18248540551966399988" });
		params.put("category", new String[] { "Geographic" });

		expect(mockApiClient.get(ReportingServiceURIs.GET_DPA_REPORT, params))
				.andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao.getDPAReport(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetPersonicxTargetGroups() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject("{\"id\": \"1\","
				+ "\"description\": \"insight one\","
				+ "\"targetGroups\": [{\"createdDate\": null,"
				+ "\"modifiedDate\": null," + "\"userID\": null,"
				+ "\"insightID\": null," + "\"id\": \"8\","
				+ "\"name\": \"testttt\"," + "\"referenceID\": \"-1\","
				+ "\"referenceQuestion\": \"National\","
				+ "\"referenceAnswer\": null,"
				+ "\"segments\": [\"1\",\"4\",\"7\"]," + "\"count\": 661,"
				+ "\"percent\": 0.028775412}]}");

		JSONObject params = new JSONObject();
		params.put("insightid", new String[] { "18248540551966399988" });
		params.put("referenceid", new String[] { "970" });

		expect(
				mockApiClient.get(
						ReportingServiceURIs.GET_PERSONICX_TARGET_GROUPS,
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao.getPersonicxTargetGroups(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetPropensityDetailReport() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"insightDescription\": \"insight one\","
						+ "\"modelDescription\": \"In Market for a New Domestic Luxury Vehicle\","
						+ "\"recordCount\": 22997,"
						+ "\"index\": [103,118,113,103,118,108,103,74,69,93]}");

		JSONObject params = new JSONObject();
		params.put("insightid", new String[] { "18248540551966399988" });
		params.put("modelid", new String[] { "9770038622290248609" });

		expect(
				mockApiClient.get(
						ReportingServiceURIs.GET_PROPENSITY_DETAILS_REPORT,
						params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao.getPropensityDetailReport(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

	@Test
	public void testGetAudiencePropensitiesDPAReport() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"reportData\": [{\"questionID\": \"0\","
						+ "\"questionText\": \"In Market Propensities\","
						+ "\"responseID\": \"148\","
						+ "\"responseValue\": null,"
						+ "\"responseText\": \"Credit Card Usage for Monthly Expenses\","
						+ "\"index\": 118," + "\"zScore\": null,"
						+ "\"overUnderFlag\": true,"
						+ "\"boxedTargetCount\": null,"
						+ "\"boxedTargetTotal\": null,"
						+ "\"boxedTargetPercent\": null,"
						+ "\"boxedBaseCount\": null,"
						+ "\"boxedBaseTotal\": null,"
						+ "\"boxedBasePercent\": null},{\"questionID\": \"0\","
						+ "\"questionText\": \"In Market Propensities\","
						+ "\"responseID\": \"137\","
						+ "\"responseValue\": null,"
						+ "\"responseText\": \"In Market for Home Purchase\","
						+ "\"index\": 113," + "\"zScore\": null,"
						+ "\"overUnderFlag\": false,"
						+ "\"boxedTargetCount\": null,"
						+ "\"boxedTargetTotal\": null,"
						+ "\"boxedTargetPercent\": null,"
						+ "\"boxedBaseCount\": null,"
						+ "\"boxedBaseTotal\": null,"
						+ "\"boxedBasePercent\": null}],\"reportType\": \"A\","
						+ "\"reference\": \"US\","
						+ "\"category\": \"Audience Propensities\","
						+ "\"target\": \"insight one\"}");

		JSONObject params = new JSONObject();
		params.put("insightid", new String[] { "18248540551966399988" });
		params.put("propensitycategoryid", new String[] { "0" });
		params.put("overcount", new String[] { "1" });
		params.put("undercount", new String[] { "1" });

		expect(
				mockApiClient
						.get(ReportingServiceURIs.GET_AUDIENCE_PROPENSITIES_DPA_REPORT,
								params)).andReturn(expected);

		replay(mockApiClient);
		JSONObject actual = reportingDao
				.getAudiencePropensitiesDPAReport(params);
		assertEquals(expected, actual);
		verify(mockApiClient);
	}

}
