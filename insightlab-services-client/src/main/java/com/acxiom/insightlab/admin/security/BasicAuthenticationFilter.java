package com.acxiom.insightlab.admin.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet Filter implementation class BasicAuthenticationFilter
 */
public class BasicAuthenticationFilter implements Filter {

	public static final String PARAM_USER = "user";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_REALM = "realm";

	@Value("${admin.resources.username}")
	private String user;
	@Value("${admin.resources.password}")
	private String password;
	@Value("${admin.resources.realm}")
	private String realm;

	@Override
	public void destroy() {
		// Nothing to do.
	}

	@Override
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		final String auth = httpRequest.getHeader("Authorization");
		if (auth != null) {

			final int index = auth.indexOf(' ');
			if (index > 0) {
				final String credentialsStr = new String(
						Base64.decodeBase64(auth.substring(index)),
						Charsets.UTF_8);

				final String[] credentials = credentialsStr.split("\\:");

				if (credentials.length == 2 && user.equals(credentials[0])
						&& password.equals(credentials[1])) {
					chain.doFilter(httpRequest, httpResponse);
					return;
				}
			}
		}

		httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"" + realm
				+ "\"");
		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	@Override
	public void init(final FilterConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
		
		if (!isFieldExist(user)) {
			throw new ServletException(
					"No user provided in filter configuration");
		}

		if (!isFieldExist(password)) {
			throw new ServletException(
					"No password provided in filter configuration");
		}

		if (!isFieldExist(realm)) {
			throw new ServletException(
					"No realm provided in filter configuration");
		}
	}
	
	private boolean isFieldExist(final String field) {
		return (field != null) && !field.isEmpty();
	}
}
