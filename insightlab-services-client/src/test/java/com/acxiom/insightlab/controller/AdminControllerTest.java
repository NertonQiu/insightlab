package com.acxiom.insightlab.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AdminControllerTest {

	private AdminController adminController;
	private MockHttpServletResponse mockHttpServletResponse; 
	private MockHttpServletRequest mockHttpServletRequest;
	
	@Before
	public void setUp() throws Exception {
		adminController = new AdminController();
		mockHttpServletResponse = new MockHttpServletResponse();
		mockHttpServletRequest = new MockHttpServletRequest();
	}

	@Test
	public void testGetLogs() throws IOException {
		adminController.getLogs("fake", "true", mockHttpServletRequest, mockHttpServletResponse);
	}

	@Test
	public void testIndex() {
		assertEquals("admin/index", adminController.index());
	}

	@Test
	public void testHelp() {
		assertEquals("admin/help", adminController.help());
	}

}
