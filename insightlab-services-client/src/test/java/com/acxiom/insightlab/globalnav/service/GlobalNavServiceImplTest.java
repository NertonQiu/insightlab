package com.acxiom.insightlab.globalnav.service;

import static com.acxiom.insightlab.test.Fakes.GLOBAL_NAV_HEAD;
import static com.acxiom.insightlab.test.Fakes.GLOBAL_NAV_HEADER;
import static com.acxiom.insightlab.test.Fakes.STYLE_GUIDE_ROOT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.Cookie;

import org.easymock.EasyMock;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;

import com.acxiom.insightlab.globalnav.model.GlobalNav;
import com.acxiom.insightlab.service.SecurityUtils;
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class GlobalNavServiceImplTest {

	private GlobalNavService globalNavService;
	private RestTemplate restTemplateMock;

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);

		restTemplateMock = EasyMock.createMock(RestTemplate.class);
		globalNavService = new GlobalNavServiceImpl();

		Whitebox.setInternalState(globalNavService, restTemplateMock);

		Field endpoint = GlobalNavServiceImpl.class
				.getDeclaredField("endpoint");
		endpoint.setAccessible(true);
		endpoint.set(globalNavService, "http://example.com/");

		Field applicationName = GlobalNavServiceImpl.class
				.getDeclaredField("applicationName");
		applicationName.setAccessible(true);
		applicationName.set(globalNavService, "/testapp");

		Field homeLinkName = GlobalNavServiceImpl.class
				.getDeclaredField("homeLinkName");
		homeLinkName.setAccessible(true);
		homeLinkName.set(globalNavService, "testlink");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetNavigation() throws JSONException, MalformedURLException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();

		GlobalNav expectedGlobalNavResponse = new GlobalNav();
		expectedGlobalNavResponse.setGlobalNavHeader(GLOBAL_NAV_HEADER);
		expectedGlobalNavResponse.setHtmlHead(GLOBAL_NAV_HEAD);
		expectedGlobalNavResponse.setStyleGuideRoot(new URL(
				STYLE_GUIDE_ROOT));
		EasyMock.expect(
				restTemplateMock.exchange(EasyMock.isA(String.class),
						EasyMock.isA(HttpMethod.class),
						EasyMock.isA(HttpEntity.class),
						EasyMock.isA(Class.class), EasyMock.isA(Object.class),
						EasyMock.isA(Object.class), EasyMock.isA(Object.class),
						EasyMock.isA(Object.class))).andReturn(
				new ResponseEntity<GlobalNav>(expectedGlobalNavResponse,
						HttpStatus.OK));
		EasyMock.replay(restTemplateMock);

		EasyMock.expect(SecurityUtils.getAuthCookieDetails()).andReturn(
				new Cookie(".DPPAUTH", "test cookie value"));

		PowerMock.replayAll();

		GlobalNav globalNav = globalNavService.getNavigation(mockRequest);
		globalNav.setErrorCode(100);
		assertNotNull(globalNav);
		assertEquals(GLOBAL_NAV_HEADER, globalNav.getGlobalNavHeader());
		assertEquals(GLOBAL_NAV_HEAD, globalNav.getHtmlHead());
		assertEquals(STYLE_GUIDE_ROOT, globalNav.getStyleGuideRoot()
				.toString());
	}

}
