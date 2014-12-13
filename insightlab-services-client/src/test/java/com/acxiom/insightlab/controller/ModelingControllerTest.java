package com.acxiom.insightlab.controller;

import static com.acxiom.insightlab.test.Fakes.GLOBAL_PROFILE;
import static com.acxiom.insightlab.test.Fakes.STRING;
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
import com.acxiom.insightlab.api.service.impl.ModelingServiceImpl;
import com.acxiom.insightlab.service.SecurityUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ModelingControllerTest {

	private ModelingController controller;
	private ModelingServiceImpl mock;

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
		mock = EasyMock.createMock(ModelingServiceImpl.class);

		controller = new ModelingController();
		Whitebox.setInternalState(controller, mock);
	}

	@Test
	public void testCreateModel() throws JSONException, BaseDataApiException,
			IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("insightid", new String[] { "13659499926987345629" });
		serviceParams.put("modelname", new String[] { "test" });

		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedResult = new JSONObject(
				"{\"id\": \"13659499926987345629\","
						+ "\"type\": \"lookalike model\"}");

		EasyMock.expect(mock.createModel(EasyMock.capture(capturedParams)))
				.andReturn(expectedResult);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest
				.addParameter("insightid", "13659499926987345629");
		mockHttpServletRequest.addParameter("modelname", "test");

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.createModel("", "", mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResult.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetModelReport() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("modelid", new String[] { "1" });

		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedServiceResponse = new JSONObject(
				"{\"isPrivate\": 0," + " \"type\": null,"
						+ " \"gain\": [{\"total\": 1205," + "\"rank\": 1,"
						+ "\"baseline\": 1,"
						+ "\"upperBound\": 0.82155464204702,"
						+ "\"lowerBound\": 0.35217162333712,"
						+ "\"targetPercent\": 0.4406639004,"
						+ "\"modelID\": \"1\"," + "\"ks\": 16.7,"
						+ "\"lift\": 396," + "\"cumulativeTotal\": 1205,"
						+ "\"cumulativeTotalPercent\": 0.0502229817,"
						+ "\"targetNumber\": 531,"
						+ "\"cumulativeTarget\": 531,"
						+ "\"cumulativeReference\": 674,"
						+ "\"cumulativeTargetPercent\": 0.1990254873,"
						+ "\"cumulativeReferencePercent\": 0.0316060961,"
						+ "\"liftCurve\": 3.96," + "\"reportSource\": \"T\","
						+ "\"rankGraph\": 0}],\"scorecard\": [{"
						+ "\"modelID\": \"1\","
						+ "\"description\": \"Other Merchandise/Services\","
						+ "\"averageRank\": 1," + "\"count\": 10,"
						+ "\"contribution\": 24.21,"
						+ "\"ibElement\": \"IB6726\"}]," + "\"ibRank\": {"
						+ "\"modelID\": \"1\"," + "\"rankTotal\": 100000,"
						+ "\"cumulativeRank\": [101,303," + " 606," + "1010,"
						+ "1515," + "2121," + " 2828," + "3636," + "4545,"
						+ "14555]," + "\"rank\": [" + "101," + "202," + "303,"
						+ "404," + " 505," + " 606," + "707," + " 808,"
						+ " 909," + " 10010]," + "\"percent\": ["
						+ "  0.00101," + "0.00202," + "0.00303," + "0.00404,"
						+ " 0.00505," + " 0.00606," + " 0.00707," + " 0.00808,"
						+ "0.00909," + "0.1001]}," + "\"id\": \"1\","
						+ "\"description\": \"default model name\","
						+ "\"userID\": null," + "\"insightID\": null,"
						+ "\"insightDescription\": null,"
						+ "\"clientID\": null," + "\"createdDate\": null,"
						+ "\"status\": null}");

		EasyMock.expect(mock.fetchModelReport(EasyMock.capture(capturedParams)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("modelid", "1");

		JSONObject expectedResult = expectedServiceResponse;

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);
		EasyMock.expect(SecurityUtils.getGlobalProfile()).andReturn(
				GLOBAL_PROFILE);
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getModelReport("", mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResult.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testDeleteModel() throws JSONException, BaseDataApiException,
			IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("modelids", new String[] { "1" });
		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedServiceResponse = new JSONObject(
				"{\"result\": [{\"isPrivate\": 0," + "\"type\": null,"
						+ "\"ModelID\": \"9770038622290248609\","
						+ "\"description\": \"The new model X\","
						+ "\"UserID\": \"kenich\","
						+ "\"id\": \"11013573005114598210\","
						+ "\"InsightDescription\": \"The next test\","
						+ "\"ClientID\": \"117\","
						+ "\"CreatedDate\": \"2013-04-24 09:54:58\","
						+ "\"Status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}},{" + "\"isPrivate\": 0,"
						+ "\"type\": null,"
						+ "\"ModelID\": \"10511723515316131150\","
						+ "\"description\": \"default model name\","
						+ "\"UserID\": \"kenich\","
						+ "\"id\": \"18275485967599939947\","
						+ "\"InsightDescription\": \"insight three\","
						+ "\"ClientID\": \"117\","
						+ "\"CreatedDate\": \"2013-04-22 14:28:55\","
						+ "\"Status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}}]}");

		EasyMock.expect(mock.deleteModels(EasyMock.capture(capturedParams)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("modelids", "1");

		JSONObject expectedResult = expectedServiceResponse;

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.deleteModel(new String[] { "" }, mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResult.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetModelLibrary() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("response_format", new String[] { "table" });
		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedServiceResponse = new JSONObject(
				"{\"results\": [{\"description\": \"default model name\","
						+ "\"id\": \"11517404493877613814\",\"source\": \"CO\",\"userID\": \"1\",\"dateAddedToLibrary\": \"2013-01-02\"},{"
						+ "\"description\": \"default model name\","
						+ "\"id\": \"12523075299001768542\",\"source\": \"CO\",\"userID\": \"1\",\"dateAddedToLibrary\": \"2013-01-02\"}]}");

		EasyMock.expect(
				mock.fetchModelLibraryData(EasyMock.capture(capturedParams)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("response_format", "table");

		JSONObject expectedResult = new JSONObject(
				"{\"rows\": [{\"description\": \"default model name\","
						+ "\"id\": \"11517404493877613814\",\"source\": \"CO\",\"userID\": \"1\",\"dateAddedToLibrary\": \"2013-01-02\"},{"
						+ "\"description\": \"default model name\","
						+ "\"id\": \"12523075299001768542\",\"source\": \"CO\",\"userID\": \"1\",\"dateAddedToLibrary\": \"2013-01-02\"}]}");

		expectedResult.put("settings", GLOBAL_PROFILE);
		EasyMock.expect(SecurityUtils.getGlobalProfile()).andReturn(
				GLOBAL_PROFILE);

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getModelLibrary("table", mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		JSONObject actualResult = new JSONObject(
				mockHttpServletResponse.getContentAsString());
		Assert.assertNotNull(actualResult.optJSONObject("settings"));
		Assert.assertNotNull(actualResult.optJSONArray("rows"));
	}

	@Test
	public void testGetModelList() throws JSONException, BaseDataApiException,
			IOException {
		JSONObject serviceParams = new JSONObject();

		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedServiceResponse = new JSONObject(
				"{\"models\": [{\"isPrivate\": 0," + "\"type\": null,"
						+ "\"id\": \"9770038622290248609\","
						+ "\"description\": \"The new model X\","
						+ "\"userID\": \"kenich\","
						+ "\"insightID\": \"11013573005114598210\","
						+ "\"insightDescription\": \"The next test\","
						+ "\"clientID\": \"117\","
						+ "\"createdDate\": \"2013-04-24 09:54:58\","
						+ "\"status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}},{" + "\"isPrivate\": 0,"
						+ "\"type\": null,"
						+ "\"id\": \"10511723515316131150\","
						+ "\"description\": \"default model name\","
						+ "\"userID\": \"kenich\","
						+ "\"insightID\": \"18275485967599939947\","
						+ "\"insightDescription\": \"insight three\","
						+ "\"clientID\": \"117\","
						+ "\"createdDate\": \"2013-04-22 14:28:55\","
						+ "\"status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}}]}");
		EasyMock.expect(mock.fetchModelList(EasyMock.capture(capturedParams)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		JSONObject expectedResult = new JSONObject(
				"{\"rows\": [{\"isPrivate\": 0," + "\"type\": null,"
						+ "\"id\": \"9770038622290248609\","
						+ "\"description\": \"The new model X\","
						+ "\"userID\": \"kenich\","
						+ "\"insightID\": \"11013573005114598210\","
						+ "\"insightDescription\": \"The next test\","
						+ "\"clientID\": \"117\","
						+ "\"createdDate\": \"2013-04-24 09:54:58\","
						+ "\"status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}},{" + "\"isPrivate\": 0,"
						+ "\"type\": null,"
						+ "\"id\": \"10511723515316131150\","
						+ "\"description\": \"default model name\","
						+ "\"userID\": \"kenich\","
						+ "\"insightID\": \"18275485967599939947\","
						+ "\"insightDescription\": \"insight three\","
						+ "\"clientID\": \"117\","
						+ "\"createdDate\": \"2013-04-22 14:28:55\","
						+ "\"status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}}]}");
		expectedResult.put("settings", GLOBAL_PROFILE);
		EasyMock.expect(SecurityUtils.getGlobalProfile()).andReturn(
				GLOBAL_PROFILE);

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getModelList("", "", "", "", mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		JSONObject actualResult = new JSONObject(
				mockHttpServletResponse.getContentAsString());
		Assert.assertNotNull(actualResult.optJSONObject("settings"));

		final JSONArray actualRows = actualResult.optJSONArray("rows");
		Assert.assertNotNull(actualRows);

		Assert.assertNotEquals(
				actualRows.getJSONObject(0).getString("createdDate"),
				actualRows.getJSONObject(1).getString("createdDate"));
	}

	@Test
	public void testModelPropensity() throws JSONException,
			BaseDataApiException, IOException {
		// TODO:
	}

	@Test
	public void testSaveModelToLibrary() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("modelid", new String[] { "1" });
		serviceParams.put("source", new String[] { "A" });
		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedServiceResponse = new JSONObject(
				"{\"results\": \"Data Saved Successfully\"}");
		EasyMock.expect(
				mock.saveModelToLibrary(EasyMock.capture(capturedParams)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.setParameter("modelid", "1");
		mockHttpServletRequest.setParameter("source", "A");

		JSONObject expectedResult = expectedServiceResponse;

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.saveModelToLibrary(new String[] { "" }, "",
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResult.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testSearchModels() throws JSONException, BaseDataApiException,
			IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("filter", new String[] { "1" });
		serviceParams.put("source", new String[] { "A" });
		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedServiceResponse = new JSONObject(
				"{\"results\": [{\"description\": \"default model name\","
						+ "\"id\": \"11517404493877613814\"},{"
						+ "\"description\": \"default model name\","
						+ "\"id\": \"12523075299001768542\"}]}");
		EasyMock.expect(mock.searchModels(EasyMock.capture(capturedParams)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.setParameter("filter", "1");
		mockHttpServletRequest.setParameter("source", "A");
		JSONObject expectedResult = new JSONObject(
				"{\"rows\": [{\"description\": \"default model name\","
						+ "\"id\": \"11517404493877613814\"},{"
						+ "\"description\": \"default model name\","
						+ "\"id\": \"12523075299001768542\"}]}");
		expectedResult.put("settings", GLOBAL_PROFILE);
		EasyMock.expect(SecurityUtils.getGlobalProfile()).andReturn(
				GLOBAL_PROFILE);
		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.searchModels("", "", mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		JSONObject actualResult = new JSONObject(
				mockHttpServletResponse.getContentAsString());
		Assert.assertNotNull(actualResult.optJSONObject("settings"));
		Assert.assertNotNull(actualResult.optJSONArray("rows"));
	}

	@Test
	public void testSetModelStatus() throws JSONException,
			BaseDataApiException, IOException {

		JSONObject serviceParams = new JSONObject();

		serviceParams.put("modelids", new String[] { STRING_AS_UUID });
		serviceParams.put("statusmsg", new String[] { STRING });

		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedResponse = new JSONObject();

		expectedResponse.put("results", STRING);

		EasyMock.expect(
				mock.updateModelStatusForMultipleModels(EasyMock
						.capture(capturedParams))).andReturn(expectedResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.setModelStatus(STRING_AS_UUID, STRING,
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());

	}

	@Test
	public void testDeleteModelFromLibrary() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();
		serviceParams.put("modelid", new String[] { "1" });
		serviceParams.put("source", new String[] { "A" });
		serviceParams.put("clientid", new String[] { "1" });
		serviceParams.put("userid", new String[] { "gpundi" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedServiceResponse = new JSONObject(
				"{\"ID\": \"13659499926987345629\","
						+ "\"Type\": \"lookalike model\"}");
		EasyMock.expect(
				mock.deleteModelFromLibrary(EasyMock.capture(capturedParams)))
				.andReturn(expectedServiceResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.setParameter("modelid", "1");
		mockHttpServletRequest.setParameter("source", "A");
		JSONObject expectedResult = expectedServiceResponse;
		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.deleteModelFromLibrary(new String[] { "" }, "",
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResult.toString(),
				mockHttpServletResponse.getContentAsString());
	}

}
