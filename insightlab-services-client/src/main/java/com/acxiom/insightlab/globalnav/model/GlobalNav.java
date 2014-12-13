package com.acxiom.insightlab.globalnav.model;

import java.net.URL;

public class GlobalNav {

	private URL styleGuideRoot;
	private String htmlHead;
	private String globalNavHeader;
	private String lessVariables;
	private String errorMessage;
	private int errorCode;

	public GlobalNav() {

	}

	public GlobalNav(final String htmlHead, final String globalNavHeader,
			final URL styleGuideRoot, final String lessVariables,
			final String errorMessage, final int errorCode) {

		this.styleGuideRoot = styleGuideRoot;
		this.htmlHead = htmlHead;
		this.globalNavHeader = globalNavHeader;
		this.lessVariables = lessVariables;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public URL getStyleGuideRoot() {
		return styleGuideRoot;
	}

	public void setStyleGuideRoot(final URL styleGuideRoot) {
		this.styleGuideRoot = styleGuideRoot;
	}

	public String getHtmlHead() {
		return htmlHead;
	}

	public void setHtmlHead(String htmlHead) {
		this.htmlHead = htmlHead;
	}

	public String getGlobalNavHeader() {
		return globalNavHeader;
	}

	public void setGlobalNavHeader(final String globalNavHeader) {
		this.globalNavHeader = globalNavHeader;
	}

	public String getLessVariables() {
		return lessVariables;
	}

	public void setLessVariables(final String lessVariables) {
		this.lessVariables = lessVariables;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}
}
