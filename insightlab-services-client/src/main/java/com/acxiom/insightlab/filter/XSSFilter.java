package com.acxiom.insightlab.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class XSSFilter
 */
public class XSSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		httpResponse.setHeader("X-Content-Type-Options", "nosniff");
		httpResponse.setHeader("X-Frame-Options","sameorigin");
		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request),
				httpResponse);
	}

}