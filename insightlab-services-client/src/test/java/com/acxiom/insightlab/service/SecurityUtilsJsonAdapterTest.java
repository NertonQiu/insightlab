package com.acxiom.insightlab.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;

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

import com.acxiom.web.sso.dataAccess.model.ApplicationRoleDto;
import com.acxiom.web.sso.dataAccess.model.GlobalProfileDto;
import com.acxiom.web.sso.dataAccess.model.TenantDto;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class SecurityUtilsJsonAdapterTest {

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
	}

	@Test
	public void testGetGlobalProfileJson() throws JSONException {
		GlobalProfileDto expectedValue = new GlobalProfileDto();
		expectedValue.setUserDateFormat("1");
		expectedValue.setEmail("1");
		expectedValue.setEmailFormat(1);
		expectedValue.setFirstName("1");
		expectedValue.setLastName("1");
		expectedValue.setUserTimeFormat("1");
		expectedValue.setTimeZoneInfoID("1");
		expectedValue.setUserDomain("1");
		expectedValue.setWorkingCompanyId("27dcf370-cf93-11e2-8b8b-0800200c9a66");

		EasyMock.expect(SecurityUtils.getGlobalProfile()).andReturn(
				expectedValue);

		PowerMock.replay(SecurityUtils.class);

		JSONObject actual = SecurityUtilsJsonAdapter.getGlobalProfileJson();

		PowerMock.verify(SecurityUtils.class);

		Assert.assertEquals("1", actual.getString("first_name"));
		Assert.assertEquals("1", actual.getString("last_name"));
		Assert.assertEquals("1", actual.getString("email"));
		Assert.assertEquals("1", actual.getString("date_format"));
		Assert.assertEquals("1", actual.getString("time_format"));
		Assert.assertEquals("1", actual.getString("timezone_id"));

		Assert.assertEquals("27dcf370-cf93-11e2-8b8b-0800200c9a66",
				actual.getString("working_company_id"));
		Assert.assertEquals("1", actual.getString("user_domain"));
	}

	@Test
	public void testGetCompanyInfoJson() throws JSONException {
		TenantDto expectedValue = new TenantDto();
		expectedValue.setAcrId("1");
		expectedValue.setBusinessUnit("1");
		expectedValue.setClientNumber(1);
		UUID id = UUID.fromString("27dcf370-cf93-11e2-8b8b-0800200c9a66");
		expectedValue.setCompanyId(id.toString());
		expectedValue.setDescription("1");
		expectedValue.setDisplayName("1");
		expectedValue.setFileNumber(1);
		expectedValue.setGlNumber(1);
		expectedValue.setActive(true);
		expectedValue.setName("1");
		expectedValue.setProject("1");

		EasyMock.expect(SecurityUtils.getTenantInfo())
				.andReturn(expectedValue);

		PowerMock.replay(SecurityUtils.class);

		JSONObject actual = SecurityUtilsJsonAdapter.getCompanyInfoJson();

		PowerMock.verify(SecurityUtils.class);

		Assert.assertEquals("1", actual.getString("acr_id"));
		Assert.assertEquals("1", actual.getString("bussiness_unit"));
		Assert.assertEquals("1", actual.getString("description"));
		Assert.assertEquals("1", actual.getString("display_name"));
		Assert.assertEquals("1", actual.getString("name"));
		Assert.assertEquals("1", actual.getString("project"));

		Assert.assertEquals("27dcf370-cf93-11e2-8b8b-0800200c9a66",
				actual.getString("company_id"));
		Assert.assertEquals(1, actual.getInt("gl_number"));
		Assert.assertEquals(1, actual.getInt("file_number"));
		Assert.assertEquals(1, actual.getInt("client_number"));
		Assert.assertEquals(true, actual.getBoolean("is_active"));
	}

	@Test
	public void testGetUserRolesJson() throws JSONException {
		// fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetApplicationRolesJson() throws JSONException {
		List<ApplicationRoleDto> expectedValue = new ArrayList<ApplicationRoleDto>();
		ApplicationRoleDto role = new ApplicationRoleDto();
		role.setName("role1");
		role.setDescription("desc1");
		role.setApplicationCompanyUserRole(true);
		role.setSystemRole(true);
		role.setApplicationUserRole(true);
		expectedValue.add(role);
		EasyMock.expect(SecurityUtils.getApplicationRoles()).andReturn(
				expectedValue);

		PowerMock.replay(SecurityUtils.class);

		JSONArray actual = SecurityUtilsJsonAdapter.getApplicationRolesJson();

		PowerMock.verify(SecurityUtils.class);
		JSONArray expected = new JSONArray();

		expected.put(new JSONObject(
				"{\"is_application_user_role\":true,\"description\":\"desc1\",\"name\":\"role1\",\"is_application_company_user_role\":true,\"is_system_role\":true}"));

		Assert.assertEquals(expected.get(0).toString(), actual.get(0)
				.toString());
	}

	@Test
	public void testGetAuthCookieDetailsJson() throws JSONException {
		String cookieName = ".NAME";
		String cookieValue = "VALUE";
		Cookie expectedValue = new Cookie(cookieName, cookieValue);
		EasyMock.expect(SecurityUtils.getAuthCookieDetails()).andReturn(
				expectedValue);

		PowerMock.replay(SecurityUtils.class);

		JSONObject actual = SecurityUtilsJsonAdapter.getAuthCookieDetailsJson();

		PowerMock.verify(SecurityUtils.class);
		JSONObject expected = new JSONObject();
		expected.put("name", cookieName);
		expected.put("value", cookieValue);
		expected.put("max_age", -1);
		expected.put("version", 0);
		Assert.assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void testGetUserNameJson() throws JSONException {
		String expectedValue = "gpundi";
		EasyMock.expect(SecurityUtils.getUserName()).andReturn(expectedValue);

		PowerMock.replay(SecurityUtils.class);

		JSONObject actual = SecurityUtilsJsonAdapter.getUserNameJson();

		PowerMock.verify(SecurityUtils.class);
		JSONObject expected = new JSONObject();
		expected.put("username", expectedValue);

		Assert.assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void testIsAuthenticated() throws JSONException {
		boolean expectedValue = true;
/*		EasyMock.expect(SecurityUtils.isAuthenticated()).andReturn(
				expectedValue);

		PowerMock.replay(SecurityUtils.class);

		JSONObject actual = SecurityUtilsJsonAdapter.isAuthenticated();

		PowerMock.verify(SecurityUtils.class);
		JSONObject expected = new JSONObject();
		expected.put("authenticated", expectedValue);
		Assert.assertEquals(expected.toString(), actual.toString());*/
	}

}
