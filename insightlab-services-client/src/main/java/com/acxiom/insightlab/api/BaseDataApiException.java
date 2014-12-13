package com.acxiom.insightlab.api;

public class BaseDataApiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8232534612412134705L;

	private int code;

	public BaseDataApiException() {
		super();
	}

	public BaseDataApiException(final String message) {
		super(message);
	}

	public BaseDataApiException(final Throwable t) {
		super(t);
	}

	public BaseDataApiException(final String message, final Throwable t) {
		super(message, t);
	}

	public BaseDataApiException(final String message, final int code) {
		super(message);
		this.code = code;
	}

	public BaseDataApiException(final String message, final int code,
			final Throwable t) {
		super(message, t);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
