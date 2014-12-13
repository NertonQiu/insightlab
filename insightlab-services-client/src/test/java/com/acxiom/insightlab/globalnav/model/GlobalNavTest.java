package com.acxiom.insightlab.globalnav.model;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GlobalNavTest {

	private GlobalNav model;

	@Before
	public void setUp() throws Exception {
		model = new GlobalNav();
	}

	@Test
	public void testGlobalNav() {
		model = new GlobalNav();
	}

	@Test
	public void testGlobalNavParametrizedConstructor()
			throws MalformedURLException {
		URL styleGuideRoot = new URL("http://styleguiderootexample.com");
		String htmlHead = "some html code here";
		String globalNavHeader = "some header markup here";
		String lessVariables = "some variables?";
		String errorMessage = "error here";
		int errorCode = 500;
		GlobalNav globalNavModel = new GlobalNav(htmlHead, globalNavHeader,
				styleGuideRoot, lessVariables, errorMessage, errorCode);
		Assert.assertEquals(errorCode, globalNavModel.getErrorCode());
		Assert.assertEquals(errorMessage, globalNavModel.getErrorMessage());
		Assert.assertEquals(globalNavHeader,
				globalNavModel.getGlobalNavHeader());
		Assert.assertEquals(lessVariables, globalNavModel.getLessVariables());
		Assert.assertEquals(styleGuideRoot, globalNavModel.getStyleGuideRoot());
	}

	@Test
	public void testGetStyleGuideRoot() throws MalformedURLException {
		URL styleGuideRoot = new URL("http://styleguiderootexample.com");
		model.setStyleGuideRoot(styleGuideRoot);
		Assert.assertEquals(styleGuideRoot, model.getStyleGuideRoot());
	}

	@Test
	public void testSetStyleGuideRoot() throws MalformedURLException {
		URL styleGuideRoot = new URL("http://styleguiderootexample.com");
		model.setStyleGuideRoot(styleGuideRoot);
		Assert.assertEquals(styleGuideRoot, model.getStyleGuideRoot());
		URL styleGuideRoot2 = new URL("http://styleguiderootexample2.com");
		model.setStyleGuideRoot(styleGuideRoot2);
		Assert.assertEquals(styleGuideRoot2, model.getStyleGuideRoot());
	}

	@Test
	public void testGetHtmlHead() {
		String htmlHead = "some html code here";
		model.setHtmlHead(htmlHead);
		Assert.assertEquals(htmlHead, model.getHtmlHead());
	}

	@Test
	public void testSetHtmlHead() {
		String htmlHead = "some html code here";
		model.setHtmlHead(htmlHead);
		Assert.assertEquals(htmlHead, model.getHtmlHead());
		String htmlHead2 = "some html code here2";
		model.setHtmlHead(htmlHead2);
		Assert.assertEquals(htmlHead2, model.getHtmlHead());
	}

	@Test
	public void testGetGlobalNavHeader() {
		String expected = "global nav header";
		model.setGlobalNavHeader(expected);
		Assert.assertEquals(expected, model.getGlobalNavHeader());
	}

	@Test
	public void testSetGlobalNavHeader() {
		String expected = "global nav header";
		model.setGlobalNavHeader(expected);
		Assert.assertEquals(expected, model.getGlobalNavHeader());
		String expected2 = "global nav header2";
		model.setGlobalNavHeader(expected2);
		Assert.assertEquals(expected2, model.getGlobalNavHeader());
	}

	@Test
	public void testGetLessVariables() {
		String expected = "less vars";
		model.setLessVariables(expected);
		Assert.assertEquals(expected, model.getLessVariables());
	}

	@Test
	public void testSetLessVariables() {
		String expected = "less vars";
		model.setLessVariables(expected);
		Assert.assertEquals(expected, model.getLessVariables());
		String expected2 = "less vars2";
		model.setLessVariables(expected2);
		Assert.assertEquals(expected2, model.getLessVariables());
	}

	@Test
	public void testGetErrorMessage() {
		String expected = "error";
		model.setErrorMessage(expected);
		Assert.assertEquals(expected, model.getErrorMessage());
	}

	@Test
	public void testSetErrorMessage() {
		String expected = "error";
		model.setErrorMessage(expected);
		Assert.assertEquals(expected, model.getErrorMessage());
		String expected2 = "error2";
		model.setErrorMessage(expected2);
		Assert.assertEquals(expected2, model.getErrorMessage());
	}

	@Test
	public void testGetErrorCode() {
		int expected = 500;
		model.setErrorCode(expected);
		Assert.assertEquals(expected, model.getErrorCode());
	}

	@Test
	public void testSetErrorCode() {
		int expected = 500;
		model.setErrorCode(expected);
		Assert.assertEquals(expected, model.getErrorCode());
		int expected2 = 404;
		model.setErrorCode(expected2);
		Assert.assertEquals(expected2, model.getErrorCode());
	}

}
