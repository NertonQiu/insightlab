package com.acxiom.insightlab.co.model;

import static com.acxiom.insightlab.test.Fakes.CREATED_DATE;
import static com.acxiom.insightlab.test.Fakes.DESCRIPTION;
import static com.acxiom.insightlab.test.Fakes.EQUATION;
import static com.acxiom.insightlab.test.Fakes.ID;
import static com.acxiom.insightlab.test.Fakes.USER_ID;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.acxiom.insightlab.co.model.COModel;

public class COModelTest {


	private COModel model;
	@Before
	public void setUp() throws Exception {
		model = new COModel();
	}

	@Test
	public void testSetUserID() {
		model.setClientID(USER_ID);
		assertEquals(USER_ID, model.getClientID());
	}

	@Test
	public void testSetDescription() {
		model.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, model.getDescription());
	}

	@Test
	public void testSetId() {
		model.setId(ID);
		assertEquals(ID, model.getId());
	}

	@Test
	public void testSetClientID() {
		model.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, model.getDescription());
	}

	@Test
	public void testSetEquation() {
		model.setEquation(EQUATION);
		assertEquals(EQUATION, model.getEquation());
	}

	@Test
	public void testSetCreatedDate() {
		model.setCreatedDate(CREATED_DATE);
		assertEquals(CREATED_DATE, model.getCreatedDate());
	}

}
