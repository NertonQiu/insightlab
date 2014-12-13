package com.acxiom.insightlab.filter;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acxiom.insightlab.api.constant.MasterDataServiceURIs;

/**
 * Servlet Filter implementation class ServicesMemcachingFilter
 */
public class ServicesMemcachingFilter extends AbstractMemcachingFilter {

	/**
	 * Default constructor.
	 */
	public ServicesMemcachingFilter() {
		super();
	}
	
	@Override
	protected void setResponseHeaders(final HttpServletResponse httpResponse) {
		httpResponse.setContentType("application/json");
		httpResponse.setCharacterEncoding("utf-8");
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
	}

	@Override
	protected boolean isCachingAllowedForRequest(final HttpServletRequest request) {
		return pathsForCaching.contains(request.getPathInfo());
		
	}
	
	private final List<String> pathsForCaching = Arrays.asList(new String[]{
			MasterDataServiceURIs.GET_ALL_SYN_DATA_CATEGORIES,
			MasterDataServiceURIs.GET_SYN_DATA_SUBCATEGORIES_BY_CATEGORY,
			MasterDataServiceURIs.GET_SYN_DATA_QUESTIONS_BY_SUBCATEGORY,
			MasterDataServiceURIs.GET_RESPONSE_FOR_MRI_QUESTION 
	});

}
