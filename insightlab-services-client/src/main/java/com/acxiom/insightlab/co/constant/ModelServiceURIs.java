package com.acxiom.insightlab.co.constant;

public final class ModelServiceURIs {
	private ModelServiceURIs() {
		throw new UnsupportedOperationException();
	}

	private static final String VERSION = "/v1";

	private static String getPathWithServiceNamePrefix(final String methodPath) {
		return VERSION + methodPath;
	}

	public static final String GET_CO_MODELS = getPathWithServiceNamePrefix("/co-models");
	public static final String GET_CO_MODEL = getPathWithServiceNamePrefix("/co-model");

}
