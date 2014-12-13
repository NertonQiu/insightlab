package com.acxiom.insightlab.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.acxiom.insightlab.exception.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/yui")
public class YuiComboController extends GlobalExceptionHandler {

	@Value("${app.yui.combo.service.endpoint}")
	private String yuiComboEndpoint;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(YuiComboController.class);

	@RequestMapping("/combo")
	public void combo(HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException, URISyntaxException {
                LOGGER.info(request.getRequestURI());
		String urlString = String.format("%s?%s", yuiComboEndpoint,
				request.getQueryString());
                LOGGER.info(urlString);
		URI url = new URI(urlString);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,
				String.class);

		HttpHeaders headers = responseEntity.getHeaders();
		List<String> headerValues;
		for (String headerName : headers.keySet()) {
			headerValues = headers.get(headerName);
			for (String value : headerValues) {
				response.addHeader(headerName, value);
			}
		}
                LOGGER.info(responseEntity.getBody());
		response.getWriter().print(responseEntity.getBody());
	}
}
