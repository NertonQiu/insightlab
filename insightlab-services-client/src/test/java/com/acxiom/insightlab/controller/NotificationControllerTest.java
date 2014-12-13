package com.acxiom.insightlab.controller;

import static com.acxiom.insightlab.test.Fakes.STRING;
import static com.acxiom.insightlab.test.Fakes.STRING_AS_INT;

import java.io.IOException;

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
import com.acxiom.insightlab.api.service.impl.NotificationServiceImpl;
import com.acxiom.insightlab.service.SecurityUtils;
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class NotificationControllerTest {
	
	private NotificationController controller;
	private NotificationServiceImpl mock;

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
		mock = EasyMock.createMock(NotificationServiceImpl.class);

		controller = new NotificationController();
		Whitebox.setInternalState(controller, mock);
	}

	@Test
	public void testGetInsightStatusList() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();

		serviceParams
				.put("insightids", new String[] { "18248540551966399988" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedResult = new JSONObject("{\"results\": [{"
				+ "\"status\": \"INSIGHT_INPROGRESS\","
				+ "\"percentageComplete\": \"80\","
				+ "\"insightID\": \"13375356447729093951\"},{"
				+ "\"status\": \"INSIGHT_PREP\","
				+ "\"PercentageComplete\": \"0\","
				+ "\"insightID\": \"13763087196186621232\"}]}");

		
		EasyMock.expect(
				mock.getInsightStatusList(EasyMock.capture(capturedParams)))
				.andReturn(expectedResult);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("insightids",
				"18248540551966399988");

		

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getInsightStatusList(
				new String[] { "18248540551966399988" },
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResult.toString(),
				mockHttpServletResponse.getContentAsString());
	}
	
	@Test
	public void testGetModelStatusList() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject serviceParams = new JSONObject();

		serviceParams
				.put("insightids", new String[] { "18248540551966399988" });
		Capture<JSONObject> capturedParams = new Capture<JSONObject>();
		capturedParams.setValue(serviceParams);

		JSONObject expectedResponse = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject objectJson = new JSONObject();
		objectJson.put("modelID", STRING_AS_INT);
		objectJson.put("percentage", STRING_AS_INT);
		objectJson.put("status", STRING);
	
		array.put(objectJson);
		expectedResponse.put("results", array);

		
		EasyMock.expect(
				mock.getModelStatusList(EasyMock.capture(capturedParams)))
				.andReturn(expectedResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.addParameter("modelid",
				"18248540551966399988");

		

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getModelStatusList(
				new String[] { "18248540551966399988" },
				mockHttpServletRequest, mockHttpServletResponse);
		EasyMock.verify(mock);
		Assert.assertEquals(expectedResponse.toString(),
				mockHttpServletResponse.getContentAsString());
	}

}
