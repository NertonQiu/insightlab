package com.acxiom.insightlab.api.constant;

public final class ReportingServiceURIs {
	private ReportingServiceURIs() {
		throw new UnsupportedOperationException();
	}

	private static final String REPORTING_SERVICE = "/reportingService";

	private static String getPathWithServiceNamePrefix(final String methodPath) {
		return REPORTING_SERVICE + methodPath;
	}

	public static final String MODEL = getPathWithServiceNamePrefix("/Model");

	public static final String GET_PERSONICX_PORTRAIT_REPORT = getPathWithServiceNamePrefix("/getPersonicxPortraitReport");

	public static final String GET_SCATTERGRAM_REPORT = getPathWithServiceNamePrefix("/getScattergramReport");

	public static final String GET_DPA_CATEGORIES = getPathWithServiceNamePrefix("/getDPACategories");

	public static final String GET_DPA_QUESTIONS = getPathWithServiceNamePrefix("/getDPAQuestions");

	public static final String GET_DPA_RESPONSES = getPathWithServiceNamePrefix("/getDPAResponses");

	public static final String GET_DPA_REPORT = getPathWithServiceNamePrefix("/getDPAReport");

	public static final String GET_PERSONICX_TARGET_GROUPS = getPathWithServiceNamePrefix("/getPersonicxTargetGroups");

	public static final String GET_PROPENSITY_DETAILS_REPORT = getPathWithServiceNamePrefix("/getPropensityDetailReport");

	public static final String GET_AUDIENCE_PROPENSITIES_DPA_REPORT = getPathWithServiceNamePrefix("/getAudiencePropensitiesDPAReport");

	public static final String SAVE_PERSONICX_PORTRAIT = getPathWithServiceNamePrefix("/savePersonicxPortrait");

	public static final String GET_PERSONICX_PORTRAITS = getPathWithServiceNamePrefix("/getPersonicxPortraits");

	public static final String UPDATE_PERSONICX_PORTRAIT = getPathWithServiceNamePrefix("/updatePersonicxPortrait");

	public static final String DELETE_PERSONICX_PORTRAIT = getPathWithServiceNamePrefix("/deletePersonicxPortrait");

	public static final String GET_PERSONICX_PORTRAIT_STUB = getPathWithServiceNamePrefix("/getPersonicxPortraitStub");

	public static final String GET_PERSONICX_TARGET_GROUP_REPORT = getPathWithServiceNamePrefix("/getPersonicxTargetGroupReport");

	public static final String SAVE_PERSONICX_TARGET_GROUP = getPathWithServiceNamePrefix("/savePersonicxTargetGroup");

	public static final String SEGMENT_PERSONICX_PORTRAIT = getPathWithServiceNamePrefix("/TemporarySegmentPersonicxPortraitReport");

}