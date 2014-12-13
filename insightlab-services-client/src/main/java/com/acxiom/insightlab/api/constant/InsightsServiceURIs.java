package com.acxiom.insightlab.api.constant;

public final class InsightsServiceURIs {
	private InsightsServiceURIs() {
		throw new UnsupportedOperationException();
	}

	private static final String INSIGHTS = "/Insights";

	private static String getPathWithServiceNamePrefix(final String methodPath) {
		return INSIGHTS + methodPath;
	}

	public static final String INSIGHT_LIST = getPathWithServiceNamePrefix("/getInsightList");

	public static final String INSIGHTS_COUNT = getPathWithServiceNamePrefix("/count");

	public static final String DELETE_INSIGHTS = getPathWithServiceNamePrefix("/DeleteInsight");

	public static final String CLIENT_INFO = getPathWithServiceNamePrefix("/ClientInfo");

	public static final String SCORE = getPathWithServiceNamePrefix("/Score");

	public static final String GET_RECENT_INSIGHTS = getPathWithServiceNamePrefix("/getRecentInsights");

	public static final String GET_INSIGHT = getPathWithServiceNamePrefix("/getInsight");
}