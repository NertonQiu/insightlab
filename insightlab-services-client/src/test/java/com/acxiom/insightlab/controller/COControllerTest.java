package com.acxiom.insightlab.controller;

import static com.acxiom.insightlab.test.Fakes.RANDOM_UUID;
import static com.acxiom.insightlab.test.Fakes.STRING;
import static com.acxiom.insightlab.test.Fakes.STRING_AS_UUID;

import java.io.IOException;

import org.easymock.EasyMock;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.co.service.COModelService;
import com.acxiom.insightlab.service.SecurityUtils;
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class COControllerTest {

	private COController controller;
	private COModelService mock;

	@Before
	public void setUp() {
		PowerMock.mockStatic(SecurityUtils.class);
		mock = EasyMock.createMock(COModelService.class);
		controller = new COController();
		Whitebox.setInternalState(controller, mock);
	}

	@Test
	public void testGetStorageUsage() throws JSONException,
			BaseDataApiException, IOException {

		JSONObject expectedResponse = new JSONObject();

		expectedResponse.put("id", STRING_AS_UUID);

		EasyMock.expect(
				mock.getModel(STRING_AS_UUID))
				.andReturn(expectedResponse);
		EasyMock.replay(mock);

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(RANDOM_UUID.toString());
		PowerMock.replay();

		EasyMock.expect(SecurityUtils.getUserName()).andReturn(STRING);

		PowerMock.replayAll();

		mockHttpServletResponse.setWriterAccessAllowed(true);
		controller.getModel(STRING_AS_UUID, mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);

		JSONObject actualResult = new JSONObject(
				mockHttpServletResponse.getContentAsString());
		Assert.assertEquals(expectedResponse.getString("id"),
				actualResult.getString("id"));
	}

}
