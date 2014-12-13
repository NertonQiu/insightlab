package com.acxiom.insightlab.controller;

import java.io.IOException;

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
import com.acxiom.insightlab.api.service.impl.MasterDataServiceImpl;
import com.acxiom.insightlab.service.SecurityUtils;
import static com.acxiom.insightlab.test.Fakes.STRING_AS_INT;
import static com.acxiom.insightlab.test.Fakes.STRING;
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class MasterDataControllerTest {

	private MasterDataController controller;
	private MasterDataServiceImpl mock;

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
		mock = EasyMock.createMock(MasterDataServiceImpl.class);

		controller = new MasterDataController();
		Whitebox.setInternalState(controller, mock);
	}

	@Test
	public void testGetSynDataResponsesByQuestion() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject mockResponse = new JSONObject();
		JSONArray resultArray = new JSONArray();
		JSONObject objectJson = new JSONObject();
		objectJson.put("id", STRING_AS_INT);
		objectJson.put("responseText", STRING);
		objectJson.put("responseValue", STRING);
		objectJson.put("questionID", STRING_AS_INT);

		resultArray.put(objectJson);
		mockResponse.put("results", resultArray);

		EasyMock.expect(
				mock.getResponseForSpecificMRIQuestions(EasyMock
						.anyObject(JSONObject.class))).andReturn(mockResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		JSONObject expectedResponse = new JSONObject();
		JSONArray expectedArray = new JSONArray();
		JSONObject dropdownObjectJson = new JSONObject();
		dropdownObjectJson.put("id", STRING_AS_INT);
		dropdownObjectJson.put("responseText", STRING);
		dropdownObjectJson.put("questionID", STRING_AS_INT);
		dropdownObjectJson.put("responseValue", STRING);
		expectedArray.put(dropdownObjectJson);
		expectedResponse.put("results", expectedArray);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getSynDataResponsesByQuestion("", mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetSynDataSubcategoriesByCategory()
			throws BaseDataApiException, JSONException, IOException {
		JSONObject mockResponse = new JSONObject();
		JSONArray resultArray = new JSONArray();
		JSONObject objectJson = new JSONObject();
		objectJson.put("id", STRING_AS_INT);
		objectJson.put("subcategoryName", STRING);
		objectJson.put("categoryID", STRING_AS_INT);

		resultArray.put(objectJson);
		mockResponse.put("results", resultArray);

		EasyMock.expect(
				mock.getSynDataSubcategoriesByCategory(EasyMock
						.anyObject(JSONObject.class))).andReturn(mockResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		JSONObject expectedResponse = new JSONObject();
		JSONArray expectedArray = new JSONArray();
		JSONObject dropdownObjectJson = new JSONObject();
		dropdownObjectJson.put("id", STRING_AS_INT);
		dropdownObjectJson.put("subcategoryName", STRING);
		dropdownObjectJson.put("categoryID", STRING_AS_INT);
		expectedArray.put(dropdownObjectJson);
		expectedResponse.put("results", expectedArray);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getSynDataSubcategoriesByCategory("",
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetPersonicxReferenceFiles() throws BaseDataApiException,
			JSONException, IOException {
		JSONObject mockResponse = new JSONObject();
		JSONArray resultArray = new JSONArray();

		resultArray.put(new JSONObject(
				"{\"fileName\":\"US Personicx\",\"fileID\":\"-1\"}"));
		resultArray.put(new JSONObject(
				"{\"fileName\":\"State Personicx\",\"fileID\":\"-2\"}"));
		resultArray.put(new JSONObject(
				"{\"fileName\":\"DMA Personicx\",\"fileID\":\"-4\"}"));
		resultArray.put(new JSONObject(
				"{\"fileName\":\"County Personicx\",\"fileID\":\"-3\"}"));
		resultArray.put(new JSONObject(
				"{\"fileName\":\"ZIP Personicx\",\"fileID\":\"-5\"}"));
		mockResponse.put("results", resultArray);

		EasyMock.expect(
				mock.getPersonicxReferenceFiles(EasyMock
						.anyObject(JSONObject.class))).andReturn(mockResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		JSONObject expectedResponse = new JSONObject();
		JSONArray expectedArray = new JSONArray();

		expectedArray.put(new JSONObject(
				"{\"fileName\":\"US Personicx\",\"fileID\":\"-1\"}"));
		expectedArray.put(new JSONObject(
				"{\"fileName\":\"State Personicx\",\"fileID\":\"-2\"}"));
		expectedArray.put(new JSONObject(
				"{\"fileName\":\"DMA Personicx\",\"fileID\":\"-4\"}"));
		expectedArray.put(new JSONObject(
				"{\"fileName\":\"County Personicx\",\"fileID\":\"-3\"}"));
		expectedArray.put(new JSONObject(
				"{\"fileName\":\"ZIP Personicx\",\"fileID\":\"-5\"}"));
		expectedResponse.put("results", expectedArray);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getPersonicxReferenceFiles(mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetSynDataQuestionsBySubcategory()
			throws BaseDataApiException, JSONException, IOException {
		JSONObject mockResponse = new JSONObject();
		JSONArray resultArray = new JSONArray();

		resultArray
				.put(new JSONObject(
						"{\"id\":\"1\",\"subcategoryID\":\"1\",\"questionText\":\"Responsiveness To Ads Across Media Segments\"}"));
		mockResponse.put("results", resultArray);

		EasyMock.expect(
				mock.getSynDataQuestionsBySubcategory(EasyMock
						.anyObject(JSONObject.class))).andReturn(mockResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.setParameter("subcategoryid", "1");

		JSONObject expectedResponse = new JSONObject();
		JSONArray expectedArray = new JSONArray();

		expectedArray
				.put(new JSONObject(
						"{\"id\":\"1\",\"subcategoryID\":\"1\",\"questionText\":\"Responsiveness To Ads Across Media Segments\"}"));
		expectedResponse.put("results", expectedArray);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getSynDataQuestionsBySubcategory("1",
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

}
