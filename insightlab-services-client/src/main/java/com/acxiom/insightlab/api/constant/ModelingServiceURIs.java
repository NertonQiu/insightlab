package com.acxiom.insightlab.api.constant;

public final class ModelingServiceURIs {
	private ModelingServiceURIs() {
		throw new UnsupportedOperationException();
	}

	private static final String MODELING_SERVICE = "/modelingService";

	private static String getPathWithServiceNamePrefix(final String methodPath) {
		return MODELING_SERVICE + methodPath;
	}

	public static final String MODEL = getPathWithServiceNamePrefix("/Model");

	public static final String MODEL_STATUS = getPathWithServiceNamePrefix("/modelStatus/{0}/{1}");

	public static final String SET_MODEL_STATUS = getPathWithServiceNamePrefix("/setModelStatus");

	public static final String GET_MODEL_LIST = getPathWithServiceNamePrefix("/getModelList");

	// PUT
	public static final String DELETE_MODELS = getPathWithServiceNamePrefix("/deleteModel");

	// GET
	public static final String SEARCH_MODELS = getPathWithServiceNamePrefix("/searchModels");

	// Fetch model library data GET
	public static final String GET_MODEL_LIBRARY = getPathWithServiceNamePrefix("/getModelLibrary");

	// save model library POST
	public static final String SAVE_MODEL_TO_LIBRARY = getPathWithServiceNamePrefix("/saveModelToLibrary");

	public static final String DELETE_MODEL_FROM_LIBRARY = getPathWithServiceNamePrefix("/deleteModelFromLibrary");

	public static final String CREATE_MODEL_COUNTS = getPathWithServiceNamePrefix("/counts");
	public static final String GET_MODEL_COUNTS = getPathWithServiceNamePrefix("/counts");

	public static final String CREATE_SEGMENT = getPathWithServiceNamePrefix("/segment");
	public static final String UPDATE_SEGMENT = getPathWithServiceNamePrefix("/segment");

}
