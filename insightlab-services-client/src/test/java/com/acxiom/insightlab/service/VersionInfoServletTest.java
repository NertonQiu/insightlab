package com.acxiom.insightlab.service;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

public class VersionInfoServletTest {

	private VersionInfoServlet servlet;
	
	@Before
	public void setUp() throws Exception {
		servlet = new VersionInfoServlet();
		servlet.init(new MockServletConfig());
	}

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		
		servlet.doGet(mockRequest, mockResponse);
		
		// TODO: assert response?
	}

}
