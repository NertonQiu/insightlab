package com.acxiom.insightlab.api.constant;

public final class NotificationServiceURIs {
	private NotificationServiceURIs() {
		throw new UnsupportedOperationException();
	}

	private static final String NOTIFICATION_SERVICE = "/notificationService";

	private static String getPathWithServiceNamePrefix(final String methodPath) {
		return NOTIFICATION_SERVICE + methodPath;
	}

	public static final String INSIGHT = getPathWithServiceNamePrefix("/Insight/{0}");

	public static final String MODEL_STATUS = getPathWithServiceNamePrefix("/modelStatus/{0}");

	public static final String INSIGHT_STATUS_LIST = getPathWithServiceNamePrefix("/InsightStatusList");

	public static final String MODEL_STATUS_LIST = getPathWithServiceNamePrefix("/ModelStatusList");

	public static final String GET_MODEL_SCORING_STATUS = getPathWithServiceNamePrefix("/ModelScoringStatus");

	public static final String GET_MODEL_SCORING_STATUS_LIST = getPathWithServiceNamePrefix("/ModelScoringStatusList");
}
