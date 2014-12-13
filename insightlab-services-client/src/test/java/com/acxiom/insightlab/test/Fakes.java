package com.acxiom.insightlab.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.easymock.Capture;
import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.model.Status;
import com.acxiom.web.sso.dataAccess.model.GlobalProfileDto;

public final class Fakes {
	private Fakes() {
		throw new UnsupportedOperationException();
	}

	public static final int CACHE_LIFETIME_1 = 50000;
	public static final int CACHE_LIFETIME_2 = 70000;
	public static final int PORT = 11211;
	public static final String HOST = "localhost";
	public static final String DESCRIPTION = "some description";
	public static final String ID = "100500";
	public static final String SOURCE = "CO";
	public static final String NULL = null;
	public static final String PERCENTAGE = "1";
	public static final String MODEL_ID = "1";
	public static final String STATUS_STRING = "fake status";
	public static final String USER_ID = "1";
	public static final String CLIENT_ID = "2";
	public static final String INSIGHT_DESCRIPTION = "fake insight description";
	public static final String INSIGHT_ID = "1111";
	public static final int IS_PRIVATE = 1;
	public static final Status STATUS = new Status();
	public static final String TYPE = "fake type";
	public static final Date CREATED_DATE = new Date();
	public static final String DATE_STRING = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss").format(CREATED_DATE);
	public static final float FLOAT = 5f;
	public static final String STATUS_COMPLETED = "INSIGHT_COMPLETED";
	public static final String STATUS_PREP = "INSIGHT_PREP";
	public static final boolean PROPERTIES_EXISTS = false;
	public static final String SOURCE_IL = "IL";
	public static final int DATE = 2;
	public static final int DAY = 5;
	public static final int HOURS = 10;
	public static final int MINUTES = 51;
	public static final int MONTH = 7;
	public static final int SECONDS = 53;
	public static final long TIME = 1375440713000L;
	public static final int TIMEZONE_OFFSET = 0;
	public static final int YEAR = 113;
	public static final String MODIFIED_DATE = "2013-06-07 12:56:11";

	public static final int SIZE = 1;
	public static final int RECORD_COUNT = 1;
	public static final Boolean HAS_CLIENT_QUESTIONS = null;
	public static final Boolean PRIVATE = null;
	public static final boolean MODELED = false;
	public static final Boolean SCORED = null;
	public static final int TOTAL_COUNT = 10;
	public static final int STORAGE = 10000;
	public static final String TENANT_ID = UUID.randomUUID().toString();
	public static final GlobalProfileDto GLOBAL_PROFILE = new GlobalProfileDto();
	public static final JSONObject DATE_TIME_SETTINGS = new JSONObject();

	public static final String EQUATION = "2+2=4";
	
	public static final String USER_ID2 = "2";
	public static final String CLIENT_ID2 = "2";
	public static final String DESCRIPTION2 = "fake description2";
	public static final String ID2 = "2";
	public static final Date CREATED_DATE2 = new Date(1376655811400L);

	public static final UUID RANDOM_UUID = UUID.randomUUID();
	
	
	
	public static final boolean BOOLEAN = true;
	public static final String STYLE_GUIDE_ROOT = "http://rootexample.com/";
	public static final String GLOBAL_NAV_HEAD = "head";
	public static final String GLOBAL_NAV_HEADER = "body";
	
	public static final int INTERNAL_SERVER_ERROR = 500;
	public static final int NOT_FOUND = 404;
	public static final String URL_STRING = "http://www.example.com/";
	public static final String PATH_TO = "/path/to";
	public static final String APP_NAME = "app_name";
	public static final String FILE_CENTER_URL = "fileCenterURL";
	public static final String CO_ENDPOINT = "coEndpoint";
	
	public static final int INT = 1;
	public static final double DOUBLE = 1.0;
	public static final String STRING_AS_INT = "1";
	public static final Capture<JSONObject> EMPTY_CAPTURED_PARAMS = new Capture<JSONObject>();

	
	public static final String STRING = "fake string";
	public static final String STRING_AS_UUID = "0000000000000000";
	
	

	static {
		GLOBAL_PROFILE.setUserDateFormat("MM/dd/yyyy");
		GLOBAL_PROFILE.setUserTimeFormat("h:mm tt");
		GLOBAL_PROFILE.setTimeZoneInfoID("Central Standard Time");

		try {
			DATE_TIME_SETTINGS.put("utcOffset",
					GLOBAL_PROFILE.getUtcOffset());
			DATE_TIME_SETTINGS.put("dateFormat",
					GLOBAL_PROFILE.getUserDateFormat());
			DATE_TIME_SETTINGS.put("timeFormat",
					GLOBAL_PROFILE.getUserTimeFormat());
			DATE_TIME_SETTINGS.put("timeZoneId",
					GLOBAL_PROFILE.getTimeZoneInfoID());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static {
		EMPTY_CAPTURED_PARAMS.setValue(new JSONObject());
	}
	
}
