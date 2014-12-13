package com.acxiom.insightlab.api.service.impl;

import org.easymock.EasyMock;
import org.junit.Before;
import org.powermock.reflect.Whitebox;

import com.acxiom.insightlab.api.dao.impl.NotificationDAOImpl;

public class NotificationServiceImplTest {

	private NotificationDAOImpl mockDao;
	private NotificationServiceImpl service;

	@Before
	public void setUp() throws Exception {
		service = new NotificationServiceImpl();
		mockDao = EasyMock.createStrictMock(NotificationDAOImpl.class);
		Whitebox.setInternalState(service, mockDao);
	}

}
