package com.acxiom.insightlab.controller;

import static com.acxiom.insightlab.test.Fakes.APP_NAME;
import static com.acxiom.insightlab.test.Fakes.CO_ENDPOINT;
import static com.acxiom.insightlab.test.Fakes.FILE_CENTER_URL;
import static com.acxiom.insightlab.test.Fakes.PATH_TO;
import static com.acxiom.insightlab.test.Fakes.URL_STRING;

import java.io.IOException;

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
import org.powermock.reflect.Whitebox;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.acxiom.insightlab.service.SecurityUtils;
import com.acxiom.insightlab.sso.service.UserServiceClientJsonAdapter;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class UiControllerTest {

	private UiController controller;

	private UserServiceClientJsonAdapter mock;

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
		mock = EasyMock.createMock(UserServiceClientJsonAdapter.class);

		controller = new UiController();
		controller.setCoEndpoint(URL_STRING);
		controller.setFileCenterURL(URL_STRING);
		Whitebox.setInternalState(controller, mock);

	}

	@Test
	public void testGetProperties() throws JSONException, IOException {
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		mockHttpServletRequest.setRequestURI(PATH_TO + "/properties");
		controller.getProperties(mockHttpServletRequest,
				mockHttpServletResponse);

		JSONObject expectedResult = new JSONObject();
		expectedResult.put(CO_ENDPOINT, URL_STRING);
		expectedResult.put(FILE_CENTER_URL, PATH_TO + "/fileCenter");

		JSONObject actualResult = new JSONObject(
				mockHttpServletResponse.getContentAsString());
		Assert.assertEquals(expectedResult.getString(CO_ENDPOINT),
				actualResult.getString(CO_ENDPOINT));

		Assert.assertEquals(expectedResult.getString(FILE_CENTER_URL),
				actualResult.getString(FILE_CENTER_URL));

	}

	@Test
	public void testRedirectToFileCenter() throws IOException {
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		controller.redirectToFileCenter(mockHttpServletRequest,
				mockHttpServletResponse);
	}

	@Test
	public void testIsAppUser() throws IOException, JSONException {
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		JSONObject isAppUserJson = new JSONObject();
		isAppUserJson.put("isUser", true);
		isAppUserJson.put("userName", "gpundi");
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		EasyMock.expect(mock.isAppUser(APP_NAME)).andReturn(isAppUserJson);
		EasyMock.replay(mock);

		controller.isAppUser(APP_NAME, mockHttpServletRequest,
				mockHttpServletResponse);

		JSONObject actualResult = new JSONObject(
				mockHttpServletResponse.getContentAsString());

		Assert.assertEquals(isAppUserJson.getString("isUser"),
				actualResult.getString("isUser"));
	}

	@Test
	public void testGetFileCenterURL() {
		controller.setFileCenterURL(URL_STRING);
		Assert.assertEquals(URL_STRING, controller.getFileCenterURL());
	}

	@Test
	public void testGetCoEndpoint() {
		controller.setCoEndpoint(URL_STRING);
		Assert.assertEquals(URL_STRING, controller.getCoEndpoint());
	}

}
