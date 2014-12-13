package com.acxiom.insightlab.co.model;

import static com.acxiom.insightlab.test.Fakes.CLIENT_ID;
import static com.acxiom.insightlab.test.Fakes.CLIENT_ID2;
import static com.acxiom.insightlab.test.Fakes.CREATED_DATE;
import static com.acxiom.insightlab.test.Fakes.CREATED_DATE2;
import static com.acxiom.insightlab.test.Fakes.DESCRIPTION;
import static com.acxiom.insightlab.test.Fakes.DESCRIPTION2;
import static com.acxiom.insightlab.test.Fakes.EQUATION;
import static com.acxiom.insightlab.test.Fakes.ID;
import static com.acxiom.insightlab.test.Fakes.ID2;
import static com.acxiom.insightlab.test.Fakes.USER_ID;
import static com.acxiom.insightlab.test.Fakes.USER_ID2;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.acxiom.insightlab.co.model.COModel;
import com.acxiom.insightlab.co.model.ModelAdapter;
public class ModelAdapterTest {

	
	private COModel model;
	private ModelAdapter modelAdapter;
	
	@Before
	public void setUp() throws Exception {
		model = new COModel();
		model.setClientID(CLIENT_ID);
		model.setCreatedDate(CREATED_DATE);
		model.setDescription(DESCRIPTION);
		model.setEquation(EQUATION);
		model.setId(ID);
		model.setUserID(USER_ID);
		
		
		modelAdapter = new ModelAdapter(model);
	}

	@Test
	public void testSetId() {
		modelAdapter.setId(ID2);
		assertEquals(ID2, modelAdapter.getId());
	}

	@Test
	public void testSetDescription() {
		modelAdapter.setDescription(DESCRIPTION2);
		assertEquals(DESCRIPTION2, modelAdapter.getDescription());
	}

	@Test
	public void testSetUserID() {
		modelAdapter.setUserID(USER_ID2);
		assertEquals(USER_ID2, modelAdapter.getUserID());
	}

	@Test
	public void testSetTenantID() {
		modelAdapter.setTenantID(CLIENT_ID2);
		assertEquals(CLIENT_ID2, modelAdapter.getTenantID());
	}

	@Test
	public void testSetCreatedDate() {
		modelAdapter.setCreatedDate(CREATED_DATE2);
		assertEquals(CREATED_DATE2, modelAdapter.getCreatedDate());
	}

}
