package com.acxiom.insightlab.api.dao.impl;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.constant.ClientServiceURIs;
import com.acxiom.insightlab.api.dao.ClientDAO;
import com.acxiom.insightlab.api.utils.JsonUtils;

/**
 * This API service is client for back-end service application implemented by
 * Taras Kadykalo.
 * 
 * 
 * @author dmytro plekhotkin
 * 
 */
@Component
public class ClientDAOImpl implements ClientDAO {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClientDAOImpl.class);

	@Value("${accountmanager.api.endpoint}")
	private String endpoint;

	@Value("${acxiomweb.api.key}")
	private String apiKey;

	@Value("${acxiomweb.api.secret}")
	private String apiSecret;
	
	
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public JSONObject getStorageUsage(final String companyId)
			throws BaseDataApiException, JSONException {

		JSONObject responseJson = null;
		HttpEntity<?> entity = getHttpEntity();

		try {
			ResponseEntity<String> response = restTemplate.exchange(endpoint
					+ ClientServiceURIs.GET_ALL + "/" + companyId, HttpMethod.GET, entity,
					String.class);
			String responseBody = response.getBody();
			responseJson = JsonUtils.stringToJSONObject(responseBody);

		} catch (HttpStatusCodeException statusCodeEx) {
			LOGGER.error("{} {}", new Object[] { statusCodeEx.getMessage(),
					statusCodeEx.getResponseBodyAsString() });
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return responseJson;
	}

	private HttpEntity<?> getHttpEntity() {
		final HttpHeaders headers = getHeaders();
		return new HttpEntity<Object>(headers);
	}

	private HttpHeaders getHeaders() {
		final HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", "Basic " + getAuthStringEnc());
		return headers;
	}

	private String getAuthStringEnc() {
		final String authString = apiKey + ":" + apiSecret;
		LOGGER.info(authString);
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		return new String(authEncBytes);
	}
}
