package com.acxiom.insightlab.api.model;

import static com.acxiom.insightlab.test.Fakes.CLIENT_ID;
import static com.acxiom.insightlab.test.Fakes.DATE_STRING;
import static com.acxiom.insightlab.test.Fakes.DESCRIPTION;
import static com.acxiom.insightlab.test.Fakes.INSIGHT_DESCRIPTION;
import static com.acxiom.insightlab.test.Fakes.INSIGHT_ID;
import static com.acxiom.insightlab.test.Fakes.IS_PRIVATE;
import static com.acxiom.insightlab.test.Fakes.SOURCE;
import static com.acxiom.insightlab.test.Fakes.STATUS;
import static com.acxiom.insightlab.test.Fakes.TYPE;
import static com.acxiom.insightlab.test.Fakes.USER_ID;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
public class ViewModelTest {

	
	
	private static final String ID = "3";
	
	private ViewModel viewModel;

	@Before
	public void setUp() throws Exception {
		viewModel = new ViewModel();

	}

	@Test
	public void testSetId() {
		viewModel.setId(ID);
		assertEquals(ID, viewModel.getId());
	}

	@Test
	public void testSetDescription() {
		viewModel.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, viewModel.getDescription());
	}

	@Test
	public void testSetUserID() {
		viewModel.setUserID(USER_ID);
		assertEquals(USER_ID, viewModel.getUserID());
	}

	@Test
	public void testSetInsightID() {
		viewModel.setInsightID(INSIGHT_ID);
		assertEquals(INSIGHT_ID, viewModel.getInsightID());
	}

	@Test
	public void testSetInsightDescription() {
		viewModel.setInsightDescription(INSIGHT_DESCRIPTION);
		assertEquals(INSIGHT_DESCRIPTION, viewModel.getInsightDescription());
	}

	@Test
	public void testSetTenantID() {
		viewModel.setTenantID(CLIENT_ID);
		assertEquals(CLIENT_ID, viewModel.getTenantID());
	}

	@Test
	public void testSetCreatedDate() {
		viewModel.setCreatedDate(DATE_STRING);
		assertEquals(DATE_STRING, viewModel.getCreatedDate());
	}

	@Test
	public void testSetSource() {
		viewModel.setSource(SOURCE);
		assertEquals(SOURCE, viewModel.getSource());
	}

	@Test
	public void testSetType() {
		viewModel.setType(TYPE);
		assertEquals(TYPE, viewModel.getType());
	}

	@Test
	public void testSetStatus() {
		viewModel.setStatus(STATUS);
		assertEquals(STATUS, viewModel.getStatus());
	}

	@Test
	public void testSetIsPrivate() {
		viewModel.setIsPrivate(IS_PRIVATE);
		assertEquals(IS_PRIVATE, viewModel.getIsPrivate());
	}

}
