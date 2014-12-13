package com.acxiom.insightlab.sso.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.servlet.http.Cookie;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.acxiom.insightlab.service.SecurityUtils;
import com.acxiom.insightlab.test.Fakes;
import com.acxiom.web.sso.dataAccess.UserServiceFacade;
import com.acxiom.web.sso.dataAccess.model.RoleInCompanyDto;
import com.acxiom.web.sso.dataAccess.model.RolesInApplicationDto;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class UserServiceClientTest {

	private UserServiceClient userServiceClient;

	private UserServiceFacade userServiceMock;

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
		userServiceMock = EasyMock.createMock(UserServiceFacade.class);
		userServiceClient = new UserServiceClient();
		Whitebox.setInternalState(userServiceClient, userServiceMock);
	}

	@Test
	public void testIsAppUser() {
		Cookie cookie = new Cookie(".DPAUTH", "cookie value");

		RoleInCompanyDto role = new RoleInCompanyDto();
		role.setRoleName("user");
		RolesInApplicationDto roleList = new RolesInApplicationDto();
		roleList.setRoles(new ArrayList<RoleInCompanyDto>());
		roleList.getRoles().add(role);
		

		EasyMock.expect(
				userServiceMock.getApplicationRoles(cookie, "testApp",
						Fakes.RANDOM_UUID.toString())).andReturn(roleList);

		EasyMock.replay(userServiceMock);
		EasyMock.expect(SecurityUtils.getAuthCookieDetails()).andReturn(cookie);
		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(
				Fakes.RANDOM_UUID.toString());
		PowerMock.replayAll();

		boolean result = userServiceClient.isAppUser("testApp");

		assertEquals(true, result);
	}
}
