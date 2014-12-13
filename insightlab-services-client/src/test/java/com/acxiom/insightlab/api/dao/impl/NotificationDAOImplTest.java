package com.acxiom.insightlab.api.dao.impl;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.acxiom.insightlab.api.ApiClientJsonImpl;

public class NotificationDAOImplTest {

	private NotificationDAOImpl service;
	private ApiClientJsonImpl mockApiClient;

	@Before
	public void init() {
		service = new NotificationDAOImpl();
		mockApiClient = EasyMock.createStrictMock(ApiClientJsonImpl.class);
		service.setApiClient(mockApiClient);
	}

	@Test
	public void testSetApiClient() {
		NotificationDAOImpl notificationService = new NotificationDAOImpl();
		ApiClientJsonImpl apiClient = new ApiClientJsonImpl();
		notificationService.setApiClient(apiClient);
		assertEquals(apiClient, notificationService.getApiClient());
	}

}
