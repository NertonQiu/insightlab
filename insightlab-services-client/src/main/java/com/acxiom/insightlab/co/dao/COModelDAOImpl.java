package com.acxiom.insightlab.co.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.utils.JsonUtils;
import com.acxiom.insightlab.co.constant.ModelServiceURIs;
import com.acxiom.insightlab.co.model.COModel;
import com.acxiom.insightlab.service.SecurityUtils;

@Service
public class COModelDAOImpl implements COModelDAO {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(COModelDAOImpl.class);

	private static final String CO_MODEL_SOURCE = "CO";
	private static final Logger CONNECTION_LOGGER = LoggerFactory
			.getLogger("connection." + COModelDAOImpl.class.getName());

	private final RestTemplate restTemplate;

	@Value("${co.service.endpoint}")
	private String coEndpoint;

	/**
	 * Public constructor that initializes requestTemplate.
	 */
	public COModelDAOImpl() {
		final ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		restTemplate = new RestTemplate(requestFactory);
	}

	private HttpClient getHttpClient() {
		// TODO: describe why poolingConnectionManger is needed
		final ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
		return new DefaultHttpClient(connectionManager);
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		final HttpClient httpClient = getHttpClient();
		final ClientHttpRequestFactory requestFactory = new COClientHttpRequestFactory(
				httpClient);
		return requestFactory;
	}

	/**
	 * Custom request factory that prepares connection with SSO cookie in
	 * request header.
	 * 
	 * @author dmytro.plekhotkin
	 * 
	 */
	private class COClientHttpRequestFactory extends
			HttpComponentsClientHttpRequestFactory {
		public COClientHttpRequestFactory(final HttpClient httpClient) {
			super(httpClient);
		}

		@Override
		protected void postProcessHttpRequest(
				final HttpUriRequest httpUriRequest) {
			final String cookieRequestProperty = getCookieRequestProperty();
			httpUriRequest.setHeader("Cookie", cookieRequestProperty);
		}
	}

	/**
	 * Concatenates SSO cookie name and value in such format: name=value.
	 * 
	 * @return string that is a concatenated cookie name and value (format:
	 *         name=value);
	 */
	private String getCookieRequestProperty() {
		final Cookie authCookie = SecurityUtils.getAuthCookieDetails();
		final String cookieName = authCookie.getName();
		final String cookieValue = authCookie.getValue();
		return cookieName + "=" + cookieValue;
	}

	@Override
	public List<COModel> getModelList() throws BaseDataApiException {
		final String companyId = SecurityUtils.getCompanyId().toString();

		StringBuilder urlStringBulder = new StringBuilder(coEndpoint);
		urlStringBulder.append(ModelServiceURIs.GET_CO_MODELS);
		urlStringBulder.append("?clientid=");
		urlStringBulder.append(companyId);

		List<COModel> modelList = new ArrayList<COModel>();
		final HttpHeaders headers = getHeaders();
		final HttpEntity<String> entity = new HttpEntity<String>(headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(
					urlStringBulder.toString(), HttpMethod.GET, entity,
					String.class);
			String responseBody = response.getBody();
			CONNECTION_LOGGER.debug("{} {}", new Object[] { HttpMethod.GET,
					urlStringBulder.toString() });
			LOGGER.warn("ResponseBody as string: " + responseBody);

			Map<String, Class<?>> map = new HashMap<String, Class<?>>();
			map.put("model", COModel.class);
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
					.fromObject(responseBody);
			for (Iterator<?> iter = jsonArray.iterator(); iter.hasNext();) {
				net.sf.json.JSONObject jsonObject = (net.sf.json.JSONObject) iter
						.next();
				modelList.add((COModel) net.sf.json.JSONObject.toBean(
						jsonObject, COModel.class));
			}
		} catch (HttpStatusCodeException statusCodeEx) {
			LOGGER.error("{} {}", new Object[] { statusCodeEx.getMessage(),
					statusCodeEx.getResponseBodyAsString() });
			throw new BaseDataApiException(
					statusCodeEx.getResponseBodyAsString(), statusCodeEx
							.getStatusCode().value(), statusCodeEx);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BaseDataApiException(e);
		}

		return modelList;
	}

	@Override
	public JSONObject getModel(final String modelId)
			throws BaseDataApiException {

		JSONObject model = null;
		final String userId = SecurityUtils.getUserName();
		final String companyId = SecurityUtils.getCompanyId().toString();

		StringBuilder urlStringBulder = new StringBuilder(coEndpoint);
		urlStringBulder.append(ModelServiceURIs.GET_CO_MODEL);
		urlStringBulder.append("?clientid=");
		urlStringBulder.append(companyId);

		urlStringBulder.append("&userid=");
		urlStringBulder.append(userId);

		urlStringBulder.append("&modelid=");
		urlStringBulder.append(modelId);

		final HttpHeaders headers = getHeaders();
		final HttpEntity<String> entity = new HttpEntity<String>(headers);

		try {
			final HttpMethod method = HttpMethod.GET;

			ResponseEntity<String> response = restTemplate.exchange(
					urlStringBulder.toString(), method, entity, String.class);
			String responseBody = response.getBody();
			CONNECTION_LOGGER.debug("{} {}", new Object[] { method,
					urlStringBulder.toString() });

			model = JsonUtils.stringToJSONObject(responseBody);
			updateModelToModelLibraryFormat(model);
		} catch (HttpStatusCodeException statusCodeEx) {
			LOGGER.error("{} {}", new Object[] { statusCodeEx.getMessage(),
					statusCodeEx.getResponseBodyAsString() });
			throw new BaseDataApiException(
					statusCodeEx.getResponseBodyAsString(), statusCodeEx
							.getStatusCode().value(), statusCodeEx);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BaseDataApiException(e);
		}

		return model;

	}

	private HttpHeaders getHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		final Cookie authCookie = SecurityUtils.getAuthCookieDetails();
		headers.set("Cookie",
				authCookie.getName() + "=" + authCookie.getValue());
		return headers;
	}

	/**
	 * 
	 * @param model
	 * @return boolean value that indicates if update was successful
	 */
	private boolean updateModelToModelLibraryFormat(final JSONObject model) {
		boolean isSuccess = true;
		try {
			final JSONObject createdDateJson = model
					.optJSONObject("createdDate");
			if (createdDateJson == null) {
				LOGGER.warn("\"createdDate\" is null or has wrong format. COModel "
						+ model + " will not be shown.");
				isSuccess = false;
			} else {
				net.sf.json.JSONObject jsonObject = (net.sf.json.JSONObject) net.sf.json.JSONObject
						.fromObject(createdDateJson.toString());
				Date createdDate = (Date) net.sf.json.JSONObject.toBean(
						jsonObject, Date.class);
				final String createdDateStr = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.US).format(createdDate);
				model.put("dateAddedToLibrary", createdDateStr);
				model.put("source", CO_MODEL_SOURCE);

				model.remove("createdDate");
				model.remove("clientID");
			}

		} catch (JSONException e) {
			LOGGER.error(e.getMessage());
			isSuccess = false;
		}

		return isSuccess;

	}

}
