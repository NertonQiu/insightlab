package com.acxiom.insightlab.controller;

import static com.acxiom.insightlab.test.Fakes.INT;
import static com.acxiom.insightlab.test.Fakes.RANDOM_UUID;

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
import com.acxiom.insightlab.api.service.ClientService;
import com.acxiom.insightlab.service.SecurityUtils;
import com.acxiom.web.sso.dataAccess.model.TenantDto;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ClientControllerTest {
	
	@SuppressWarnings("deprecation")
	private ClientController controller;
	private ClientService mock;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		PowerMock.mockStatic(SecurityUtils.class);
		mock = EasyMock.createMock(ClientService.class);
		controller = new ClientController();
		Whitebox.setInternalState(controller, mock);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetStorageUsage() throws JSONException,
			BaseDataApiException, IOException {
		JSONObject storageUsage = new JSONObject();

		

		
		
		storageUsage.put("storageUsage", INT);
		EasyMock.expect(mock.getStorageUsage(RANDOM_UUID.toString())).andReturn(
				storageUsage);
		
		EasyMock.replay(mock);
		
		TenantDto companyInfo = new TenantDto();
		companyInfo.setCompanyId(RANDOM_UUID.toString());
		EasyMock.expect(SecurityUtils.getTenantInfo()).andReturn(companyInfo);

		PowerMock.replayAll();
		
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		controller.getStorageUsage(mockHttpServletRequest,
				mockHttpServletResponse);
		EasyMock.verify(mock);
		
		JSONObject actualResult = new JSONObject(mockHttpServletResponse.getContentAsString());
		Assert.assertEquals(storageUsage.getString("storageUsage"),
				actualResult.getString("storageUsage"));
	}

}
