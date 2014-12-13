package com.acxiom.insightlab.api.constant;

public final class MasterDataServiceURIs {
	private MasterDataServiceURIs() {
		throw new UnsupportedOperationException();
	}

	private static final String MASTER_DATA_SERVICE = "/masterDataService";

	private static String getPathWithServiceNamePrefix(final String methodPath) {
		return MASTER_DATA_SERVICE + methodPath;
	}

	public static final String GET_PERSONICX_REFERENCE_FILES = getPathWithServiceNamePrefix("/getPersonicxReferenceFiles");

	public static final String GET_ALL_SYN_DATA_CATEGORIES = getPathWithServiceNamePrefix("/getAllSynDataCategories");

	public static final String GET_SYN_DATA_QUESTIONS_BY_SUBCATEGORY = getPathWithServiceNamePrefix("/getSynDataQuestionsBySubcategory");

	public static final String GET_RESPONSE_FOR_MRI_QUESTION = getPathWithServiceNamePrefix("/getSynDataResponsesByQuestion");

	public static final String GET_SYN_DATA_SUBCATEGORIES_BY_CATEGORY = getPathWithServiceNamePrefix("/getSynDataSubcategoriesByCategory");
}
