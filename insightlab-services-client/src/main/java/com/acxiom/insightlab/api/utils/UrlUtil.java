package com.acxiom.insightlab.api.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.service.ServletUtils;

/**
 * Helps to work with Url, url strings, build query string from JSONObject, etc.
 * 
 * @author dmytro.plekhotkin
 * 
 */
public final class UrlUtil {

	/**
	 * Private constructor. For using only as helper class.
	 */
	private UrlUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Converts JSONObject to query string.
	 * 
	 * @param params
	 *            Query string parameters.
	 * @return URL query string
	 * @throws BaseDataApiException
	 */
	// TODO: reimplement this function for accordance with PMD, Checkstyle
	// rules.
	public static String jsonParamsToQueryString(final JSONObject params)
			throws BaseDataApiException {
		String query = "";

		if (params != null) {

			final String[] names = JSONObject.getNames(params);
			if (names != null) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < names.length; i++) {
					sb.append((i == 0 ? "" : "&")).append(names[i]).append("=");
					try {
						Object obj = params.get(names[i]);
						if (obj instanceof JSONArray) {
							final String value = ((JSONArray) params.get(names[i]))
									.getString(0);
							final String encodedValue = URLEncoder.encode(value, "UTF-8");
							sb.append(encodedValue);
						} else if (obj instanceof Number) {
							sb.append(obj.toString());
						} else if (obj instanceof String) {
							final String encodedValue = URLEncoder.encode(obj+"", "UTF-8");
							sb.append(encodedValue);
						} else if (obj instanceof String[]) {
							String[] parValues = (String[]) obj;
							for (int valueIndex = 0; valueIndex < parValues.length; valueIndex++) {
								String value = parValues[valueIndex];
								final String encodedValue = URLEncoder.encode(value, "UTF-8");
								if (valueIndex > 0) {
									sb.append("&").append(names[i]).append("=");
								}
								sb.append(encodedValue);
							}
						}
					} catch (JSONException e) {
						throw new BaseDataApiException(e.getMessage(), e);
					} catch (UnsupportedEncodingException e) {
						throw new BaseDataApiException(e.getMessage(), e);
					}
				}
				query = sb.toString();
			}
		}
		return query;
	}

	/**
	 * @deprecated
	 * @param path
	 * @return
	 * @throws BaseDataApiException
	 */
	public static URL buildUrl(final String endPoint, final String path,
			final JSONObject params) throws BaseDataApiException {
		String query = UrlUtil.jsonParamsToQueryString(params);

		URL url;

		try {
			if (!query.isEmpty()) {
				query = '?' + query;
			}
			url = new URL(endPoint + path + query);
		} catch (MalformedURLException e) {
			throw new BaseDataApiException(e);
		}

		return url;
	}

}