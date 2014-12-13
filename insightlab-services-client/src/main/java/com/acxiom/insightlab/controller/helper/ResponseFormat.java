package com.acxiom.insightlab.controller.helper;

public enum ResponseFormat {
	TABLE("table"), DROPDOWN("dropdown");

	ResponseFormat(final String formatType) {
		this.formatType = formatType;
	}

	private final String formatType;

	@Override
	public String toString() {
		return formatType;
	}
}
