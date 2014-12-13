package com.acxiom.insightlab.api;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.codec.Charsets;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.acxiom.insightlab.api.utils.JsonUtils;
import com.acxiom.insightlab.api.utils.UrlUtil;

@Service("apiClientJson")
public class ApiClientJsonImpl implements ApiClientJson {

	/**
	 * Spring RestTemplate class for connecting to Rest service.
	 */
	private RestTemplate restTemplate;

	/**
	 * User credentials for login to service.
	 */
	private transient Credentials credentials;

	/**
	 * Logger for debugging url strings.
	 */
	private static final Logger CONNECTION_LOGGER = LoggerFactory
			.getLogger("connection." + ApiClientJsonImpl.class.getName());

	/**
	 * Default connection timeout in milliseconds
	 */
	private static final int TIMEOUT_MILLIS = 10000;
	/**
	 * Connection timeout in milliseconds
	 */
	private int timeoutMillis = TIMEOUT_MILLIS;

	@Value("${app.api.endpoint}")
	private String endPoint;

	public ApiClientJsonImpl() {
		init();
	}

	/**
	 * Procedure that does preparation of fields.
	 */
	private void init() {

		final ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
		final HttpParams httpParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParams, timeoutMillis);

		final DefaultHttpClient httpClient = new DefaultHttpClient(
				connectionManager, httpParams);
		httpClient.setCookieStore(new BasicCookieStore());
		if (credentials != null) {
			((AbstractHttpClient) httpClient).getCredentialsProvider()
					.setCredentials(AuthScope.ANY, credentials);
		}
		final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);

		restTemplate = new RestTemplate(requestFactory);
	}

	@Override
	public JSONObject get(final String path, final JSONObject params)
			throws BaseDataApiException {
		final URL url = UrlUtil.buildUrl(endPoint, path, params);

		final HttpHeaders headers = getBasicHeaders();
		final HttpEntity<String> entity = new HttpEntity<String>(headers);
		final ResponseEntity<String> response = getResponseEntity(url,
				HttpMethod.GET, entity);
		final String responseBody = response.getBody();

		return JsonUtils.stringToJSONObject(responseBody);
	}

	@Override
	public JSONObject post(final String path, final JSONObject params)
			throws BaseDataApiException {
		final URL url = UrlUtil.buildUrl(endPoint, path, params);

		final String paramsStr = UrlUtil.jsonParamsToQueryString(params);
		final HttpHeaders headers = getBasicHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final HttpEntity<String> entity = new HttpEntity<String>(paramsStr,
				headers);
		final ResponseEntity<String> response = getResponseEntity(url,
				HttpMethod.POST, entity);

		return JsonUtils.stringToJSONObject(response.getBody());
	}

	@Override
	public JSONObject post(final String path, final String body)
			throws BaseDataApiException {
		URL url;
		try {
			url = new URL(endPoint + path);
		} catch (MalformedURLException e) {
			throw new BaseDataApiException(e);
		}

		final HttpHeaders headers = getBasicHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		final ResponseEntity<String> response = getResponseEntity(url,
				HttpMethod.POST, entity);

		return JsonUtils.stringToJSONObject(response.getBody());
	}

	@Override
	public JSONObject put(final String path, final JSONObject params)
			throws BaseDataApiException {

		final URL url = UrlUtil.buildUrl(endPoint, path, params);

		final HttpHeaders headers = getBasicHeaders();
		final HttpEntity<String> entity = new HttpEntity<String>(headers);
		final ResponseEntity<String> response = getResponseEntity(url,
				HttpMethod.PUT, entity);

		return JsonUtils.stringToJSONObject(response.getBody());
	}

	@Override
	public JSONObject put(final String path, final String body)
			throws BaseDataApiException {
		URL url;
		try {
			url = new URL(endPoint + path);
		} catch (MalformedURLException e) {
			throw new BaseDataApiException(e);
		}

		final HttpHeaders headers = getBasicHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		final ResponseEntity<String> response = getResponseEntity(url,
				HttpMethod.PUT, entity);

		return JsonUtils.stringToJSONObject(response.getBody());
	}

	@Override
	public JSONObject delete(final String path, final JSONObject params)
			throws BaseDataApiException {
		final URL url = UrlUtil.buildUrl(endPoint, path, params);

		final String paramsStr = UrlUtil.jsonParamsToQueryString(params);
		final HttpHeaders headers = getBasicHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final HttpEntity<String> entity = new HttpEntity<String>(paramsStr,
				headers);
		final ResponseEntity<String> response = getResponseEntity(
				url.toString(), HttpMethod.DELETE, entity);

		return JsonUtils.stringToJSONObject(response.getBody());
	}

	public int getTimeoutMillis() {
		return timeoutMillis;
	}

	public void setTimeoutMillis(final int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

	private HttpHeaders getBasicHeaders() {
		final HttpHeaders basicHeaders = new HttpHeaders();

		basicHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		basicHeaders.setAcceptCharset(Arrays.asList(Charsets.UTF_8));
		return basicHeaders;
	}

	private ResponseEntity<String> getResponseEntity(final URL url,
			final HttpMethod method, final HttpEntity<String> entity)
			throws BaseDataApiException {
		try {
			final ResponseEntity<String> response = restTemplate.exchange(
					url.toURI(), method, entity, String.class);

			CONNECTION_LOGGER.debug("{} {}",
					new Object[] { method, url.toString() });

			return response;

		} catch (HttpStatusCodeException statusCodeEx) {
			CONNECTION_LOGGER.error(
					"{} {} {} {}",
					new Object[] { statusCodeEx.getMessage(),
							statusCodeEx.getResponseBodyAsString(), method,
							url.toString() });
			throw new BaseDataApiException(
					statusCodeEx.getResponseBodyAsString(), statusCodeEx
							.getStatusCode().value(), statusCodeEx);
		} catch (RestClientException e) {
			CONNECTION_LOGGER.error("{} {} {}", new Object[] { e.getMessage(),
					method, url.toString() });
			throw new BaseDataApiException(e);
		} catch (URISyntaxException e) {
			CONNECTION_LOGGER.error("{} {} {}", new Object[] { e.getMessage(),
					method, url.toString() });
			throw new BaseDataApiException(e);
		}
	}

	@Deprecated
	private ResponseEntity<String> getResponseEntity(final String urlString,
			final HttpMethod method, final HttpEntity<String> entity)
			throws BaseDataApiException {
		try {
			final ResponseEntity<String> response = restTemplate.exchange(
					urlString, method, entity, String.class);

			CONNECTION_LOGGER
					.debug("{} {}", new Object[] { method, urlString });

			return response;

		} catch (HttpStatusCodeException statusCodeEx) {
			CONNECTION_LOGGER.error(
					"{} {} {} {}",
					new Object[] { statusCodeEx.getMessage(),
							statusCodeEx.getResponseBodyAsString(), method,
							urlString });
			throw new BaseDataApiException(
					statusCodeEx.getResponseBodyAsString(), statusCodeEx
							.getStatusCode().value(), statusCodeEx);
		}
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
