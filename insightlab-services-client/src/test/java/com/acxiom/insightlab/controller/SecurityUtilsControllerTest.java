package com.acxiom.insightlab.controller;

import static com.acxiom.insightlab.test.Fakes.STRING;

import java.io.IOException;

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
import org.springframework.mock.web.MockHttpServletResponse;

import com.acxiom.insightlab.service.SecurityUtilsJsonAdapter;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtilsJsonAdapter.class)
public class SecurityUtilsControllerTest {



	private SecurityUtilsController controller;

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtilsJsonAdapter.class);
		controller = new SecurityUtilsController();
	}

	@Test
	public void testGetUserName() throws JSONException, IOException {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		JSONObject userNameJson = new JSONObject();
		userNameJson.put("userName", "fake name");

		EasyMock.expect(SecurityUtilsJsonAdapter.getUserNameJson()).andReturn(
				userNameJson);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getUserName(mockHttpServletResponse);

		Assert.assertEquals(userNameJson.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetUserDetails() throws JSONException, IOException {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		JSONObject json = new JSONObject();
		json.put("field", STRING);

		EasyMock.expect(SecurityUtilsJsonAdapter.getUserDetailsJson())
				.andReturn(json);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getUserDetails(mockHttpServletResponse);

		Assert.assertEquals(json.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetGlobalProfile() throws JSONException, IOException {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		JSONObject json = new JSONObject();
		json.put("field", STRING);

		EasyMock.expect(SecurityUtilsJsonAdapter.getGlobalProfileJson())
				.andReturn(json);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getGlobalProfile(mockHttpServletResponse);

		Assert.assertEquals(json.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetCompanyInfo() throws JSONException, IOException {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		JSONObject json = new JSONObject();
		json.put("field", STRING);

		EasyMock.expect(SecurityUtilsJsonAdapter.getCompanyInfoJson())
				.andReturn(json);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getCompanyInfo(mockHttpServletResponse);

		Assert.assertEquals(json.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetUserRoles() throws JSONException, IOException {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		JSONObject json = new JSONObject();
		json.put("field", STRING);
		JSONArray arrayJson = new JSONArray();
		arrayJson.put(json);
		EasyMock.expect(SecurityUtilsJsonAdapter.getUserRolesJson()).andReturn(
				arrayJson);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getUserRoles(mockHttpServletResponse);

		final JSONObject expected = new JSONObject();
		expected.put("user_roles", arrayJson);
		Assert.assertEquals(expected.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetApplicationRoles() throws JSONException, IOException {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		JSONObject json = new JSONObject();
		json.put("field", STRING);

		JSONArray arrayJson = new JSONArray();
		arrayJson.put(json);

		EasyMock.expect(SecurityUtilsJsonAdapter.getApplicationRolesJson())
				.andReturn(arrayJson);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getApplicationRoles(mockHttpServletResponse);
		final JSONObject expected = new JSONObject();
		expected.put("application_roles", arrayJson);
		Assert.assertEquals(expected.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testGetAuthCookieDetails() throws JSONException, IOException {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		JSONObject json = new JSONObject();
		json.put("field", STRING);

		EasyMock.expect(SecurityUtilsJsonAdapter.getAuthCookieDetailsJson())
				.andReturn(json);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getAuthCookieDetails(mockHttpServletResponse);

		Assert.assertEquals(json.toString(),
				mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testIsAuthenticated() throws JSONException, IOException {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		JSONObject json = new JSONObject();
		json.put("field", STRING);

		EasyMock.expect(SecurityUtilsJsonAdapter.isAuthenticated()).andReturn(
				json);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.isAuthenticated(mockHttpServletResponse);

		Assert.assertEquals(json.toString(),
				mockHttpServletResponse.getContentAsString());
	}
}
