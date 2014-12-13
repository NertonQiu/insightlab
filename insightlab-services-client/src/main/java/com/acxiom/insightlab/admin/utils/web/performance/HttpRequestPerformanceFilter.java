package com.acxiom.insightlab.admin.utils.web.performance;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;

/**
 * Servlet Filter implementation class HttpRequestPerformanceFilter
 */
public class HttpRequestPerformanceFilter implements Filter {

	protected EtmMonitor etmMonitor;
	protected FilterConfig filterConfig;

	@Override
	public void init(final FilterConfig aFilterConfig) throws ServletException {
		filterConfig = aFilterConfig;
		etmMonitor = getEtmMonitor();
	}

	@Override
	public void doFilter(final ServletRequest servletRequest,
			final ServletResponse servletResponse, final FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = ((HttpServletRequest) servletRequest);

		final String request = httpRequest.getRequestURI();
		final String method = httpRequest.getMethod();

		final EtmPoint point = etmMonitor.createPoint("HTTP " + method + " "
				+ request.replaceAll("[+]?\\d+", "*"));
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			point.collect();
		}
	}

	@Override
	public void destroy() {
		// TODO: do nothing
	}

	protected EtmMonitor getEtmMonitor() throws ServletException {
		return EtmManager.getEtmMonitor();
	}

}
