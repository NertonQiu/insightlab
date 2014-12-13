package com.acxiom.insightlab.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.acxiom.insightlab.api.constant.MasterDataServiceURIs;

public class ServicesMemcachingFilterTest {
	private ServicesMemcachingFilter filter;
	
	@Before
	public void setUp() {
		filter = new ServicesMemcachingFilter();
	}
	
	@Test
	public void testSetResponseHeaders() {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		
		filter.setResponseHeaders(mockHttpServletResponse);
		Assert.assertEquals("application/json", mockHttpServletResponse.getContentType());
		Assert.assertEquals("utf-8", mockHttpServletResponse.getCharacterEncoding());
		Assert.assertEquals("*", mockHttpServletResponse.getHeaderValue("Access-Control-Allow-Origin"));
	}

	@Test
	public void testIsCachingAllowedForRequest() {
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.setPathInfo(MasterDataServiceURIs.GET_ALL_SYN_DATA_CATEGORIES);
		filter.isCachingAllowedForRequest(mockHttpServletRequest);
		Assert.assertTrue(filter.isCachingAllowedForRequest(mockHttpServletRequest));
	}

	@Test
	public void testServicesMemcachingFilter() {
		ServicesMemcachingFilter memcachedFilter = new ServicesMemcachingFilter();
		Assert.assertNotNull(memcachedFilter);
	}

}
