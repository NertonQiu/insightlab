package com.acxiom.insightlab.filter;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AntiCachingFilterTest {
	private AntiCachingFilter filter;

	@Before
	public void setUp() {
		filter = new AntiCachingFilter();
	}

	@Test
	public void testDestroy() {
		filter.destroy();
	}

	@Test
	public void testDoFilter() throws IOException, ServletException {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		filter.doFilter(mockHttpServletRequest, mockHttpServletResponse,
				new MockFilterChain());
	}

	@Test
	public void testInit() throws ServletException {
		filter.init(new MockFilterConfig());
	}
}
