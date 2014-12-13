package com.acxiom.insightlab.exception;

import static com.acxiom.insightlab.test.Fakes.INTERNAL_SERVER_ERROR;
import static com.acxiom.insightlab.test.Fakes.NOT_FOUND;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class ErrorMessageTest {

	private ErrorMessage errorMessage;

	

	@Before
	public void setUp() throws Exception {
		errorMessage = new ErrorMessage("error");
	}

	@Test
	public void testErrorMessageString() {
		errorMessage = new ErrorMessage("error");
	}

	@Test
	public void testErrorMessageStringInt() {
		errorMessage = new ErrorMessage("error", INTERNAL_SERVER_ERROR);
	}

	@Test
	public void testErrorMessageStringStringInt() {
		errorMessage = new ErrorMessage("error", "aaa", INTERNAL_SERVER_ERROR);
	}

	@Test
	public void testErrorMessageStringStringIntStackTraceElementArray() {
		errorMessage = new ErrorMessage("error", "aaa", INTERNAL_SERVER_ERROR,
				new StackTraceElement[] {});
	}

	@Test
	public void testBuildMessage() {
		errorMessage.buildMessage(true);
		errorMessage.buildMessage(false);
	}

	@Test
	public void testGetMessage() {
		ErrorMessage message = new ErrorMessage("something");
		Assert.assertEquals("something", message.getMessage());
	}

	@Test
	public void testSetMessage() {
		ErrorMessage message = new ErrorMessage("something");
		message.setMessage("1");
		Assert.assertEquals("1", message.getMessage());
	}

	@Test
	public void testGetCode() {
		ErrorMessage message = new ErrorMessage("something", NOT_FOUND);
		Assert.assertEquals(404, message.getCode());
	}

	@Test
	public void testSetCode() {
		ErrorMessage message = new ErrorMessage("something", NOT_FOUND);
		message.setCode(500);
		Assert.assertEquals(500, message.getCode());
	}

	@Test
	public void testGetDeveloperMessage() {
		ErrorMessage message = new ErrorMessage("something", "devmessage",
				INTERNAL_SERVER_ERROR);

		Assert.assertEquals("devmessage", message.getDeveloperMessage());
	}

	@Test
	public void testSetDeveloperMessage() {
		ErrorMessage message = new ErrorMessage("something", "devmessage",
				INTERNAL_SERVER_ERROR);
		message.setDeveloperMessage("1");
		Assert.assertEquals("1", message.getDeveloperMessage());
	}

}
