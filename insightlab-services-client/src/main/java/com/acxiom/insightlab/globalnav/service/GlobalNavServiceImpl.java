package com.acxiom.insightlab.globalnav.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.acxiom.insightlab.globalnav.model.GlobalNav;
import com.acxiom.insightlab.service.SecurityUtils;

@Service("globalNav")
public class GlobalNavServiceImpl implements GlobalNavService {

	/**
	 * Base global navigation service URL.
	 */
	@Value("${globalNavService.endpoint}")
	private String endpoint;

	@Value("${globalNavService.help}")
	private boolean help;

	/**
	 * Global navigation service rest API returns two levels of navigation:
	 * "Level zero" and "Level one". These two levels of navigation can have
	 * different structure according to their's id.
	 * 
	 * Field "applicationName" stores id for "Level zero" navigation.
	 */
	@Value("${acxiomweb.applicationName}")
	private String applicationName;

	@Value("${globalNavService.linkName.home}")
	private String homeLinkName;

	@Value("${globalNavService.linkName.models}")
	private String modelsLinkName;

	@Value("${acxiomweb.api.key}")
	private String apiKey;

	@Value("${acxiomweb.api.secret}")
	private String apiSecret;
	
	private static final String MODELS_CONTEXT_PATH = "/models";
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GlobalNavServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public GlobalNav getNavigation(final HttpServletRequest request) {
		HttpHeaders requestHeaders = new HttpHeaders();
		final Cookie authCookie = SecurityUtils.getAuthCookieDetails();
		if (authCookie == null) {
			LOGGER.error("cookie for global navigation is null.");
		} else {
			LOGGER.info("sending cookie for global navigation ... ");
			LOGGER.info("domain: " + authCookie.getDomain());
			LOGGER.info("name: " + authCookie.getName());
			LOGGER.info("path: " + authCookie.getPath());
			LOGGER.info("value: " + authCookie.getValue());
			LOGGER.info("max age: " + authCookie.getMaxAge());
			LOGGER.info("secure: " + authCookie.getSecure());
			LOGGER.info("version: " + authCookie.getVersion());
			requestHeaders.add("Auth-Token",
					 authCookie.getValue());
			LOGGER.info("apiKey: " + apiKey);
			LOGGER.info("apiSecret: " + apiSecret);
			if (apiKey != null && apiSecret != null) {
				requestHeaders.add("Authorization", "Basic "
						+ getBasicAuthEncoded());
			}

		}

		HttpEntity<Object> requestEntity = new HttpEntity<Object>(null,
				requestHeaders);

		final String linkName = getLinkName(request);
		ResponseEntity<GlobalNav> globalNavResponse = restTemplate.exchange(
				"{endpoint}{appName}/{linkName}?help={help}", HttpMethod.GET,
				requestEntity, GlobalNav.class, endpoint, applicationName,
				linkName, help);
		GlobalNav globalNav = globalNavResponse.getBody();
		return globalNav;
	}

	private String getLinkName(final HttpServletRequest request) {
		final String defaultLinkName = homeLinkName;
		String linkName = defaultLinkName;
		final String contextPath = request.getContextPath();
		final String requestUri = request.getRequestURI();
		boolean isModelsPage = requestUri.startsWith(contextPath
				+ MODELS_CONTEXT_PATH);
		if (isModelsPage) {
			linkName = modelsLinkName;
		}
		return linkName;
	}

	private String getBasicAuthEncoded() {
		return DatatypeConverter.printBase64Binary((apiKey + ":" + apiSecret)
				.getBytes(Charsets.UTF_8));
	}
}
