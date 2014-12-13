package com.acxiom.insightlab.controller;

import static com.acxiom.insightlab.test.Fakes.DOUBLE;
import static com.acxiom.insightlab.test.Fakes.EMPTY_CAPTURED_PARAMS;
import static com.acxiom.insightlab.test.Fakes.GLOBAL_PROFILE;
import static com.acxiom.insightlab.test.Fakes.INT;
import static com.acxiom.insightlab.test.Fakes.STRING;
import static com.acxiom.insightlab.test.Fakes.STRING_AS_INT;
import static com.acxiom.insightlab.test.Fakes.STRING_AS_UUID;
import static org.easymock.EasyMock.anyObject;

import java.io.IOException;
import java.util.UUID;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.service.impl.ReportingServiceImpl;
import com.acxiom.insightlab.service.SecurityUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ReportingControllerTest {

	private ReportingController controller;
	private ReportingServiceImpl mockService;

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
		mockService = EasyMock.createMock(ReportingServiceImpl.class);
		controller = new ReportingController();
		Whitebox.setInternalState(controller, mockService);
	}

	private final static class SavePersonicxTargetGroupTester {

		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			Assert.assertEquals(actual.getString("results"),
					expected.getString("results"));
		}

		private static JSONObject getMainData() throws JSONException {
			JSONObject data = new JSONObject();
			data.put("results", STRING);
			return data;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			return getMainData();
		}

		public static JSONObject getControllerResponse() throws JSONException {
			return getMainData();
		}
	}

	private final static class PersonicxPortraitReportTester {
		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			JSONArray actualDataArray = actual.getJSONArray("rows");
			JSONArray expectedDataArray = expected.getJSONArray("rows");

			Assert.assertNotNull(actualDataArray);
			Assert.assertNotNull(expectedDataArray);

			JSONObject actualDataObject = actualDataArray.getJSONObject(0);
			JSONObject expectedDataObject = expectedDataArray.getJSONObject(0);

			Assert.assertNotNull(actualDataObject);
			Assert.assertNotNull(expectedDataObject);

			Assert.assertEquals(actualDataObject.getString("age"),
					expectedDataObject.getString("age"));
			Assert.assertEquals(actualDataObject.getString("kids"),
					expectedDataObject.getString("kids"));

			Assert.assertEquals(actual.getString("insightID"),
					expected.getString("insightID"));
			Assert.assertEquals(actual.getString("insightDescription"),
					expected.getString("insightDescription"));
		}

		private static JSONObject getMainData() throws JSONException {
			JSONObject data = new JSONObject();

			data.put("age", STRING);
			data.put("kids", STRING);
			data.put("ageText", STRING);
			data.put("netWorth", STRING);
			data.put("groupCode", STRING);
			data.put("groupName", STRING);
			data.put("urbanicity", STRING);
			data.put("bubbleSize", STRING);
			data.put("clusterName", STRING);
			data.put("maritalStatus", STRING);
			data.put("homeOwnership", STRING);
			data.put("probabilityIndex", STRING);

			data.put("ib1270", INT);
			data.put("baseCount", INT);
			data.put("baseTotal", INT);
			data.put("targetCount", INT);
			data.put("targetTotal", INT);

			data.put("zScore", DOUBLE);
			data.put("penetration", DOUBLE);
			data.put("basePercent", DOUBLE);
			data.put("targetPercent", DOUBLE);

			return data;
		}

		private static JSONObject getReportObjectWithoutMainData()
				throws JSONException {
			JSONObject report = new JSONObject();
			report.put("insightID", STRING_AS_UUID);
			report.put("insightDescription", STRING);
			report.put("referenceQuestion", STRING);
			report.put("referenceResponse", STRING);
			return report;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			JSONObject report = getReportObjectWithoutMainData();
			JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("portraitData", portraitDataListJson);
			return report;
		}

		public static JSONObject getControllerResponse() throws JSONException {
			JSONObject report = getReportObjectWithoutMainData();
			JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("rows", portraitDataListJson);
			return report;
		}
	}

	private final static class ScattergramReportTester {
		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			JSONArray actualDataArray = actual.getJSONArray("rows");
			JSONArray expectedDataArray = expected.getJSONArray("rows");

			Assert.assertNotNull(actualDataArray);
			Assert.assertNotNull(expectedDataArray);

			JSONObject actualDataObject = actualDataArray.getJSONObject(0);
			JSONObject expectedDataObject = expectedDataArray.getJSONObject(0);

			Assert.assertNotNull(actualDataObject);
			Assert.assertNotNull(expectedDataObject);

			Assert.assertEquals(actualDataObject.getString("age"),
					expectedDataObject.getString("age"));
			Assert.assertEquals(actualDataObject.getString("kids"),
					expectedDataObject.getString("kids"));

			Assert.assertEquals(actual.getString("insightID"),
					expected.getString("insightID"));
			Assert.assertEquals(actual.getString("insightDescription"),
					expected.getString("insightDescription"));
		}

		private static JSONObject getMainData() throws JSONException {
			JSONObject data = new JSONObject();

			data.put("age", STRING);
			data.put("kids", STRING);
			data.put("income", STRING);
			data.put("ageText", STRING);
			data.put("netWorth", STRING);
			data.put("groupCode", STRING);
			data.put("groupName", STRING);
			data.put("urbanicity", STRING);
			data.put("bubbleSize", STRING);
			data.put("clusterName", STRING);
			data.put("maritalStatus", STRING);
			data.put("homeOwnership", STRING);
			data.put("probabilityIndex", STRING);

			data.put("ib1270", INT);
			data.put("baseCount", INT);
			data.put("baseTotal", INT);
			data.put("targetCount", INT);
			data.put("targetTotal", INT);

			data.put("zScore", DOUBLE);
			data.put("penetration", DOUBLE);
			data.put("basePercent", DOUBLE);
			data.put("targetPercent", DOUBLE);

			return data;
		}

		private static JSONObject getReportObjectWithoutMainData()
				throws JSONException {
			JSONObject report = new JSONObject();
			report.put("insightID", STRING_AS_UUID);
			report.put("insightDescription", STRING);
			report.put("refQuestion", STRING);
			report.put("refResponse", STRING);
			return report;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			JSONObject report = getReportObjectWithoutMainData();
			JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("scattergramData", portraitDataListJson);
			return report;
		}

		public static JSONObject getControllerResponse() throws JSONException {
			JSONObject report = getReportObjectWithoutMainData();
			JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("rows", portraitDataListJson);
			return report;
		}
	}

	private final static class DPACategoriesTester {
		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			JSONArray actualDataArray = actual.getJSONArray("results");
			JSONArray expectedDataArray = expected.getJSONArray("results");

			Assert.assertNotNull(actualDataArray);
			Assert.assertNotNull(expectedDataArray);

			JSONObject actualDataObject = actualDataArray.getJSONObject(0);
			JSONObject expectedDataObject = expectedDataArray.getJSONObject(0);

			Assert.assertNotNull(actualDataObject);
			Assert.assertNotNull(expectedDataObject);

			Assert.assertEquals(actualDataObject.getString("name"),
					expectedDataObject.getString("name"));
		}

		private static JSONObject getCategory() throws JSONException {
			JSONObject json = new JSONObject();
			json.put("name", STRING);
			return json;
		}

		private static JSONObject getFormattedCategory() throws JSONException {
			JSONObject json = new JSONObject();
			json.put("name", STRING);
			json.put("name", STRING);
			return json;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			JSONObject json = new JSONObject();
			JSONArray dataArray = new JSONArray();
			dataArray.put(getCategory());
			json.put("results", dataArray);
			return json;
		}

		public static JSONObject getControllerResponse() throws JSONException {
			JSONObject json = new JSONObject();
			JSONArray dataArray = new JSONArray();
			dataArray.put(getFormattedCategory());
			json.put("results", dataArray);
			return json;
		}
	}

	private final static class DPAQuestionsTester {
		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			JSONArray actualDataArray = actual.getJSONArray("results");
			JSONArray expectedDataArray = expected.getJSONArray("results");

			Assert.assertNotNull(actualDataArray);
			Assert.assertNotNull(expectedDataArray);

			JSONObject actualDataObject = actualDataArray.getJSONObject(0);
			JSONObject expectedDataObject = expectedDataArray.getJSONObject(0);

			Assert.assertNotNull(actualDataObject);
			Assert.assertNotNull(expectedDataObject);

			Assert.assertEquals(actualDataObject.getString("questionID"),
					expectedDataObject.getString("questionID"));
			Assert.assertEquals(actualDataObject.getString("questionText"),
					expectedDataObject.getString("questionText"));
		}

		private static JSONObject getQuestion() throws JSONException {
			JSONObject json = new JSONObject();
			json.put("questionID", STRING_AS_UUID);
			json.put("questionText", STRING);
			return json;
		}

		private static JSONObject getFormattedCategory() throws JSONException {
			JSONObject json = new JSONObject();
			json.put("questionID", STRING_AS_UUID);
			json.put("questionText", STRING);
			return json;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			JSONObject json = new JSONObject();
			JSONArray dataArray = new JSONArray();
			dataArray.put(getQuestion());
			json.put("results", dataArray);
			return json;
		}

		public static JSONObject getControllerResponse() throws JSONException {
			JSONObject json = new JSONObject();
			JSONArray dataArray = new JSONArray();
			dataArray.put(getFormattedCategory());
			json.put("results", dataArray);
			return json;
		}
	}

	private final static class DPAResponsesTester {
		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			JSONArray actualDataArray = actual.getJSONArray("rows");
			JSONArray expectedDataArray = expected.getJSONArray("rows");

			Assert.assertNotNull(actualDataArray);
			Assert.assertNotNull(expectedDataArray);

			JSONObject actualDataObject = actualDataArray.getJSONObject(0);
			JSONObject expectedDataObject = expectedDataArray.getJSONObject(0);

			Assert.assertNotNull(actualDataObject);
			Assert.assertNotNull(expectedDataObject);

			Assert.assertEquals(actualDataObject.getString("responseID"),
					expectedDataObject.getString("responseID"));
			Assert.assertEquals(actualDataObject.getString("responseValue"),
					expectedDataObject.getString("responseValue"));
			Assert.assertEquals(actualDataObject.getString("responseText"),
					expectedDataObject.getString("responseText"));
		}

		private static JSONObject getResponse() throws JSONException {
			final JSONObject json = new JSONObject();
			json.put("responseID", STRING_AS_UUID);
			json.put("responseValue", STRING);
			json.put("responseText", STRING);
			return json;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			final JSONObject json = new JSONObject();
			final JSONArray dataArray = new JSONArray();
			dataArray.put(getResponse());
			json.put("results", dataArray);
			return json;
		}

		public static JSONObject getControllerResponse() throws JSONException {
			final JSONObject json = new JSONObject();
			final JSONArray dataArray = new JSONArray();
			dataArray.put(getResponse());
			json.put("rows", dataArray);
			return json;
		}
	}

	private final static class DPAReportTester {
		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			JSONArray actualDataArray = actual.getJSONArray("rows");
			JSONArray expectedDataArray = expected.getJSONArray("rows");

			Assert.assertNotNull(actualDataArray);
			Assert.assertNotNull(expectedDataArray);

			final JSONObject actualDataObject = actualDataArray
					.getJSONObject(0);
			final JSONObject expectedDataObject = expectedDataArray
					.getJSONObject(0);

			Assert.assertNotNull(actualDataObject);
			Assert.assertNotNull(expectedDataObject);

			Assert.assertEquals(actualDataObject.getString("questionID"),
					expectedDataObject.getString("questionID"));

			Assert.assertEquals(actual.getString("reportType"),
					expected.getString("reportType"));
			Assert.assertEquals(actual.getString("reference"),
					expected.getString("reference"));
			Assert.assertEquals(actual.getString("category"),
					expected.getString("category"));
			Assert.assertEquals(actual.getString("target"),
					expected.getString("target"));
		}

		private static JSONObject getMainData() throws JSONException {
			final JSONObject data = new JSONObject();

			data.put("questionID", STRING);
			data.put("questionText", STRING);
			data.put("questionReportText", STRING);
			data.put("responseID", STRING);
			data.put("responseValue", STRING);
			data.put("responseText", STRING);
			data.put("responseReportText", STRING);
			data.put("index", INT);
			data.put("zScore", DOUBLE);
			data.put("boxedBaseCount", INT);
			data.put("boxedTargetCount", INT);
			data.put("boxedTargetTotal", INT);
			data.put("boxedTargetPercent", DOUBLE);

			data.put("boxedBaseTotal", INT);
			data.put("boxedBasePercent", DOUBLE);

			return data;
		}

		private static JSONObject getReportObjectWithoutMainData()
				throws JSONException {
			final JSONObject report = new JSONObject();
			report.put("reportType", STRING);
			report.put("reference", STRING);
			report.put("category", STRING);
			report.put("target", STRING);
			return report;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			final JSONObject report = getReportObjectWithoutMainData();
			final JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("reportData", portraitDataListJson);
			return report;
		}

		public static JSONObject getControllerResponse() throws JSONException {
			final JSONObject report = getReportObjectWithoutMainData();
			final JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("rows", portraitDataListJson);
			return report;
		}
	}

	private final static class PersonicxTargetGroupsTester {
		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			final JSONArray actualDataArray = actual.getJSONArray("rows");
			final JSONArray expectedDataArray = expected.getJSONArray("rows");

			Assert.assertNotNull(actualDataArray);
			Assert.assertNotNull(expectedDataArray);

			JSONObject actualDataObject = actualDataArray.getJSONObject(0);
			JSONObject expectedDataObject = expectedDataArray.getJSONObject(0);

			Assert.assertNotNull(actualDataObject);
			Assert.assertNotNull(expectedDataObject);

			Assert.assertEquals(actualDataObject.getString("id"),
					expectedDataObject.getString("id"));

			Assert.assertEquals(actual.getString("id"),
					expected.getString("id"));
			Assert.assertEquals(actual.getString("description"),
					expected.getString("description"));
		}

		private static JSONObject getMainData() throws JSONException {
			JSONObject data = new JSONObject();

			data.put("id", STRING);
			data.put("name", STRING);
			data.put("referenceID", STRING);
			data.put("referenceQuestion", STRING);
			data.put("referenceAnswer", STRING);
			data.put("segments", STRING);
			data.put("count", INT);
			data.put("percent", DOUBLE);
			data.put("createdDate", STRING);
			data.put("modifiedDate", STRING);
			data.put("userID", STRING);
			data.put("insightID", STRING);

			return data;
		}

		private static JSONObject getReportObjectWithoutMainData()
				throws JSONException {
			final JSONObject report = new JSONObject();
			report.put("id", STRING_AS_UUID);
			report.put("description", STRING);
			return report;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			final JSONObject report = getReportObjectWithoutMainData();
			final JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("results", portraitDataListJson);
			return report;
		}

		public static JSONObject getControllerResponse() throws JSONException {
			final JSONObject report = getReportObjectWithoutMainData();
			final JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("rows", portraitDataListJson);
			return report;
		}
	}

	private final static class PropensityDetailReportTester {

		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			final JSONArray actualDataArray = actual.getJSONArray("index");
			final JSONArray expectedDataArray = expected.getJSONArray("index");

			Assert.assertNotNull(actualDataArray);
			Assert.assertNotNull(expectedDataArray);

			int actualDataObject = actualDataArray.getInt(0);
			int expectedDataObject = expectedDataArray.getInt(0);

			Assert.assertNotNull(actualDataObject);
			Assert.assertNotNull(expectedDataObject);

			Assert.assertEquals(actual.getString("insightDescription"),
					expected.getString("insightDescription"));
			Assert.assertEquals(actual.getString("modelDescription"),
					expected.getString("modelDescription"));
			Assert.assertEquals(actual.getString("recordCount"),
					expected.getString("recordCount"));
		}

		private static JSONObject getMainData() throws JSONException {
			final JSONObject data = new JSONObject();
			final JSONArray indexArray = new JSONArray();
			indexArray.put(INT);

			data.put("insightDescription", STRING);
			data.put("modelDescription", STRING);
			data.put("recordCount", STRING);
			data.put("index", indexArray);

			return data;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			return getMainData();
		}

		public static JSONObject getControllerResponse() throws JSONException {
			return getMainData();
		}
	}

	private final static class AudiencePropensitiesDPAReportTester {

		public static void assertEqualJson(final JSONObject actual,
				final JSONObject expected) throws JSONException {
			Assert.assertNotNull(actual);
			Assert.assertNotNull(expected);

			final JSONArray actualDataArray = actual.getJSONArray("rows");
			final JSONArray expectedDataArray = expected.getJSONArray("rows");

			Assert.assertNotNull(actualDataArray);
			Assert.assertNotNull(expectedDataArray);

			final JSONObject actualDataObject = actualDataArray
					.getJSONObject(0);
			final JSONObject expectedDataObject = expectedDataArray
					.getJSONObject(0);

			Assert.assertNotNull(actualDataObject);
			Assert.assertNotNull(expectedDataObject);

			Assert.assertEquals(actualDataObject.getString("questionID"),
					expectedDataObject.getString("questionID"));

			Assert.assertEquals(actual.getString("reportType"),
					expected.getString("reportType"));
			Assert.assertEquals(actual.getString("reference"),
					expected.getString("reference"));
			Assert.assertEquals(actual.getString("category"),
					expected.getString("category"));
			Assert.assertEquals(actual.getString("target"),
					expected.getString("target"));

		}

		private static JSONObject getMainData() throws JSONException {
			final JSONObject data = new JSONObject();

			data.put("questionID", STRING);
			data.put("questionText", STRING);
			data.put("questionReportText", STRING);
			data.put("responseID", STRING);
			data.put("responseValue", STRING);
			data.put("responseText", STRING);
			data.put("responseReportText", STRING);
			data.put("index", INT);
			data.put("zScore", STRING);
			data.put("overUnderFlag", true);
			data.put("boxedTargetCount", STRING);
			data.put("boxedBaseCount", STRING);
			data.put("boxedTargetTotal", STRING);
			data.put("boxedTargetPercent", STRING);
			data.put("boxedBaseTotal", STRING);
			data.put("boxedBasePercent", STRING);

			return data;
		}

		private static JSONObject getReportObjectWithoutMainData()
				throws JSONException {
			final JSONObject report = new JSONObject();
			report.put("reportType", STRING);
			report.put("reference", STRING);
			report.put("category", STRING);
			report.put("target", STRING);

			return report;
		}

		public static JSONObject getServiceResponse() throws JSONException {
			final JSONObject report = getReportObjectWithoutMainData();
			final JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("reportData", portraitDataListJson);
			return report;
		}

		public static JSONObject getControllerResponse() throws JSONException {
			final JSONObject report = getReportObjectWithoutMainData();
			final JSONArray portraitDataListJson = new JSONArray();
			portraitDataListJson.put(getMainData());
			report.put("rows", portraitDataListJson);
			return report;
		}
	}

	@Test
	public void testSavePersonicxTargetGroup() throws JSONException,
			BaseDataApiException, IOException {
		final JSONObject serviceParams = new JSONObject();
		serviceParams.put("insightid", new String[] { STRING_AS_UUID });
		serviceParams.put("referenceId", new String[] { STRING });
		serviceParams.put("segment", new String[] { STRING });
		serviceParams.put("groupName", new String[] { STRING });

		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		final JSONObject serviceResponse = SavePersonicxTargetGroupTester
				.getServiceResponse();
		EasyMock.expect(
				mockService.savePersonicxTargetGroup(EasyMock
						.capture(capturedParams))).andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("insightid", STRING_AS_UUID);
		mockHttpServletRequest.addParameter("segment", STRING);
		mockHttpServletRequest.addParameter("groupName", STRING);
		mockHttpServletRequest.addParameter("referenceId", STRING_AS_INT);

		final JSONObject controllerResponse = SavePersonicxTargetGroupTester
				.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.savePersonicxTargetGroup(STRING, STRING_AS_UUID, STRING,
				STRING, STRING, new String[] { STRING },
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mockService);
		SavePersonicxTargetGroupTester.assertEqualJson(controllerResponse,
				new JSONObject(mockHttpServletResponse.getContentAsString()));
	}

	@Test
	public void testGetPersonicxPortraitReport() throws JSONException,
			BaseDataApiException, IOException {
		final JSONObject serviceResponse = PersonicxPortraitReportTester
				.getServiceResponse();
		EasyMock.expect(
				mockService.getPersonicxPortraitReport(EasyMock
						.capture(EMPTY_CAPTURED_PARAMS))).andReturn(
				serviceResponse);

		EasyMock.replay(mockService);

		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		final JSONObject controllerResponse = PersonicxPortraitReportTester
				.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getPersonicxPortraitReport("", "", "", "",
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mockService);
		PersonicxPortraitReportTester.assertEqualJson(controllerResponse,
				new JSONObject(mockHttpServletResponse.getContentAsString()));
	}

	@Test
	public void testGetScattergramReport() throws JSONException,
			BaseDataApiException, IOException {
		final JSONObject serviceParams = new JSONObject();
		serviceParams.put("insightid", new String[] { STRING_AS_UUID });
		serviceParams.put("referenceId", new String[] { STRING });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		final JSONObject serviceResponse = ScattergramReportTester
				.getServiceResponse();
		EasyMock.expect(
				mockService.getScattergramReport(EasyMock
						.capture(capturedParams))).andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("insightid", STRING_AS_UUID);
		mockHttpServletRequest.addParameter("referenceId", STRING);

		final JSONObject controllerResponse = ScattergramReportTester
				.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getScattergramReport(STRING_AS_UUID, STRING,
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mockService);
		ScattergramReportTester.assertEqualJson(controllerResponse,
				new JSONObject(mockHttpServletResponse.getContentAsString()));
	}

	@Test
	public void testGetDPACategories() throws JSONException,
			BaseDataApiException, IOException {
		final JSONObject serviceParams = new JSONObject();
		serviceParams.put("insightid", new String[] { STRING_AS_UUID });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject serviceResponse = DPACategoriesTester.getServiceResponse();
		EasyMock.expect(
				mockService.getDPACategories(EasyMock.capture(capturedParams)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("insightid", STRING_AS_UUID);

		JSONObject controllerResponse = DPACategoriesTester
				.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getDPACategories(STRING_AS_UUID, mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mockService);
		DPACategoriesTester.assertEqualJson(controllerResponse, new JSONObject(
				mockHttpServletResponse.getContentAsString()));
	}

	private void setSecurityUtilsMock() {

		EasyMock.expect(SecurityUtils.getGlobalProfile()).andReturn(
				GLOBAL_PROFILE);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");
		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(
				UUID.randomUUID().toString());
	}

	@Test
	public void testGetDPAQuestions() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("insightid", new String[] { STRING_AS_UUID });
		serviceParams.put("category", new String[] { STRING });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject serviceResponse = DPAQuestionsTester.getServiceResponse();
		EasyMock.expect(
				mockService.getDPAQuestions(EasyMock.capture(capturedParams)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("insightid", STRING_AS_UUID);
		mockHttpServletRequest.addParameter("category", STRING);

		JSONObject controllerResponse = DPAQuestionsTester
				.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getDPAQuestions(STRING, mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mockService);
		DPAQuestionsTester.assertEqualJson(controllerResponse, new JSONObject(
				mockHttpServletResponse.getContentAsString()));
	}

	@Test
	public void testGetDPAResponses() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("question", new String[] { STRING });
		serviceParams.put("category", new String[] { STRING });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject serviceResponse = DPAResponsesTester.getServiceResponse();
		EasyMock.expect(
				mockService.getDPAResponses(EasyMock.capture(capturedParams)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("question", STRING);
		mockHttpServletRequest.addParameter("category", STRING);

		JSONObject controllerResponse = DPAResponsesTester
				.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getDPAResponses(STRING, STRING, mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mockService);
		DPAResponsesTester.assertEqualJson(controllerResponse, new JSONObject(
				mockHttpServletResponse.getContentAsString()));
	}

	@Test
	public void testGetDPAReport() throws JSONException, BaseDataApiException,
			IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("insightid", new String[] { STRING_AS_UUID });
		serviceParams.put("category", new String[] { STRING });
		serviceParams.put("question", new String[] { STRING });
		serviceParams.put("overcount", new String[] { STRING });
		serviceParams.put("undercount", new String[] { STRING });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject serviceResponse = DPAReportTester.getServiceResponse();
		EasyMock.expect(
				mockService.getDPAReport(EasyMock.capture(capturedParams)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.addParameter("insightid", STRING_AS_UUID);
		mockHttpServletRequest.addParameter("question", STRING);
		mockHttpServletRequest.addParameter("category", STRING);
		mockHttpServletRequest.addParameter("overcount", STRING);
		mockHttpServletRequest.addParameter("undercount", STRING);

		JSONObject controllerResponse = DPAReportTester.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getDPAReport(STRING_AS_UUID, STRING, STRING, STRING, STRING,
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mockService);
		DPAReportTester.assertEqualJson(controllerResponse, new JSONObject(
				mockHttpServletResponse.getContentAsString()));
	}

	@Test
	public void testGetPersonicxTargetGroups() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("insightid", new String[] { STRING_AS_UUID });
		serviceParams.put("referenceid", new String[] { STRING });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject serviceResponse = PersonicxTargetGroupsTester
				.getServiceResponse();
		EasyMock.expect(
				mockService.getPersonicxTargetGroups(EasyMock
						.capture(capturedParams))).andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.addParameter("insightid", STRING_AS_UUID);
		mockHttpServletRequest.addParameter("referenceid", STRING);

		JSONObject controllerResponse = PersonicxTargetGroupsTester
				.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getPersonicxTargetGroups("", "", STRING_AS_UUID, STRING,
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mockService);
		PersonicxTargetGroupsTester.assertEqualJson(controllerResponse,
				new JSONObject(mockHttpServletResponse.getContentAsString()));
	}

	@Test
	public void testGetPropensityDetailReport() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("insightid", new String[] { STRING_AS_UUID });
		serviceParams.put("modelid", new String[] { STRING });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject serviceResponse = PropensityDetailReportTester
				.getServiceResponse();
		EasyMock.expect(
				mockService.getPropensityDetailReport(EasyMock
						.capture(capturedParams))).andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.addParameter("insightid", STRING_AS_UUID);
		mockHttpServletRequest.addParameter("modelid", STRING);

		JSONObject controllerResponse = PropensityDetailReportTester
				.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getPropensityDetailReport(STRING_AS_UUID, STRING,
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mockService);
		PropensityDetailReportTester.assertEqualJson(controllerResponse,
				new JSONObject(mockHttpServletResponse.getContentAsString()));
	}

	@Test
	public void testGetAudiencePropensitiesDPAReport() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("insightid", new String[] { STRING_AS_UUID });
		serviceParams.put("propensitycategoryid", new String[] { STRING });
		serviceParams.put("overcount", new String[] { STRING });
		serviceParams.put("undercount", new String[] { STRING });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject serviceResponse = AudiencePropensitiesDPAReportTester
				.getServiceResponse();
		EasyMock.expect(
				mockService.getAudiencePropensitiesDPAReport(EasyMock
						.capture(capturedParams))).andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.addParameter("insightid", STRING_AS_UUID);
		mockHttpServletRequest.addParameter("propensitycategoryid", STRING);
		mockHttpServletRequest.addParameter("overcount", STRING);
		mockHttpServletRequest.addParameter("undercount", STRING);

		JSONObject controllerResponse = AudiencePropensitiesDPAReportTester
				.getControllerResponse();
		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller
				.getAudiencePropensitiesDPAReport(STRING_AS_UUID, STRING,
						STRING, STRING, mockHttpServletRequest,
						mockHttpServletResponse);
		EasyMock.verify(mockService);

		AudiencePropensitiesDPAReportTester.assertEqualJson(controllerResponse,
				new JSONObject(mockHttpServletResponse.getContentAsString()));

	}

	@Test
	public void testSavePersonicxPortrait() throws JSONException,
			BaseDataApiException, IOException {

		JSONObject serviceResponse = new JSONObject();
		serviceResponse.put("some data key", "some data value");

		EasyMock.expect(
				mockService.savePersonicxPortrait(anyObject(JSONObject.class)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.savePersonicxPortrait(STRING, STRING_AS_INT, STRING,
				STRING_AS_INT, STRING, STRING, mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mockService);

		Assert.assertEquals(serviceResponse.getString("some data key"),
				new JSONObject(mockHttpServletResponse.getContentAsString())
						.getString("some data key"));
	}

	@Test
	public void testGetPersonicxPortraits() throws JSONException,
			BaseDataApiException, IOException {

		JSONObject serviceResponse = new JSONObject();
		serviceResponse.put("some data key", "some data value");

		EasyMock.expect(
				mockService.getPersonicxPortraits(anyObject(JSONObject.class)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getPersonicxPortraits(STRING, STRING,
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mockService);

		Assert.assertEquals(serviceResponse.getString("some data key"),
				new JSONObject(mockHttpServletResponse.getContentAsString())
						.getString("some data key"));
	}

	@Test
	public void testUpdatePersonicxPortrait() throws JSONException,
			BaseDataApiException, IOException {

		JSONObject serviceResponse = new JSONObject();
		serviceResponse.put("some data key", "some data value");

		EasyMock.expect(
				mockService
						.updatePersonicxPortrait(anyObject(JSONObject.class)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.updatePersonicxPortrait(STRING, STRING,
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mockService);

		Assert.assertEquals(serviceResponse.getString("some data key"),
				new JSONObject(mockHttpServletResponse.getContentAsString())
						.getString("some data key"));
	}

	@Test
	public void testDeletePersonicxPortrait() throws JSONException,
			BaseDataApiException, IOException {

		JSONObject serviceResponse = new JSONObject();
		serviceResponse.put("some data key", "some data value");

		EasyMock.expect(
				mockService
						.deletePersonicxPortrait(anyObject(JSONObject.class)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.deletePersonicxPortrait(STRING, mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mockService);

		Assert.assertEquals(serviceResponse.getString("some data key"),
				new JSONObject(mockHttpServletResponse.getContentAsString())
						.getString("some data key"));
	}

	@Test
	public void testGetPersonicxPortraitStub() throws JSONException,
			BaseDataApiException, IOException {

		JSONObject serviceResponse = new JSONObject();
		serviceResponse.put("some data key", "some data value");

		EasyMock.expect(
				mockService
						.getPersonicxPortraitStub(anyObject(JSONObject.class)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getPersonicxPortraitStub(STRING, mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mockService);

		Assert.assertEquals(serviceResponse.getString("some data key"),
				new JSONObject(mockHttpServletResponse.getContentAsString())
						.getString("some data key"));
	}

	@Test
	public void testGetPersonicxTargetGroupReport() throws JSONException,
			BaseDataApiException, IOException {

		JSONObject serviceResponse = new JSONObject();
		serviceResponse.put("some data key", "some data value");

		EasyMock.expect(
				mockService
						.getPersonicxTargetGroupReport(anyObject(JSONObject.class)))
				.andReturn(serviceResponse);
		EasyMock.replay(mockService);
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		setSecurityUtilsMock();
		PowerMock.replayAll();
		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getPersonicxTargetGroupReport(new String[] {},
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mockService);

		Assert.assertEquals(serviceResponse.getString("some data key"),
				new JSONObject(mockHttpServletResponse.getContentAsString())
						.getString("some data key"));
	}
}
