package com.acxiom.insightlab.sso.service;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class UserServiceClientJsonAdapterTest {

	private UserServiceClient userServiceMock;
	private UserServiceClientJsonAdapter adapter;
	
	@Before
	public void setUp() throws Exception {
		userServiceMock = EasyMock.createMock(UserServiceClient.class);
		adapter = new UserServiceClientJsonAdapter();
		Whitebox.setInternalState(adapter, userServiceMock);
	}

	@Test
	public void testIsAppUser() throws JSONException {
		EasyMock.expect(
				userServiceMock.isAppUser("testApp")).andReturn(true);

		EasyMock.replay(userServiceMock);
		

		JSONObject result = adapter.isAppUser("testApp");

		assertEquals("{\"isUser\":true}", result.toString());
		
		
	}

}
