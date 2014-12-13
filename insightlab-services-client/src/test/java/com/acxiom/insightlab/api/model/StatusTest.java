package com.acxiom.insightlab.api.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import static com.acxiom.insightlab.test.Fakes.PERCENTAGE;
import static com.acxiom.insightlab.test.Fakes.STATUS_STRING;
import static com.acxiom.insightlab.test.Fakes.MODEL_ID;
public class StatusTest {

	private Status status;

	
	@Before
	public void setUp() throws Exception {
		status = new Status();
	}

	@Test
	public void testSetPercentage() {
		status.setPercentage(PERCENTAGE);
		assertEquals(PERCENTAGE, status.getPercentage());
	}

	@Test
	public void testSetStatus() {
		status.setStatus(STATUS_STRING);
		assertEquals(STATUS_STRING, status.getStatus());
	}

	@Test
	public void testSetModelID() {
		status.setModelID(MODEL_ID);
		assertEquals(MODEL_ID, status.getModelID());
	}

}
