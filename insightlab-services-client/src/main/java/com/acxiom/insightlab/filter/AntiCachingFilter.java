package com.acxiom.insightlab.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.acxiom.web.sso.dataAccess.model.GlobalProfileDto;
import com.acxiom.web.sso.security.model.AcxiomUser;
import com.acxiom.web.sso.security.model.AcxiomWebAuthenticationToken;

/**
 * Prevents the inadvertent release or retention of sensitive information. E.g.
 * if user logs out and than click history back button in browser he will be
 * redirected to login page rather than to page from history.
 */
public class AntiCachingFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AntiCachingFilter.class);

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// do nothing?
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		LOGGER.debug("enters AntiCachingFilter. Path: "
				+ ((HttpServletRequest) request).getRequestURI());
		httpResponse.setHeader("Pragma", "no-cache");
		httpResponse
				.setHeader("Cache-Control",
						"no-cache, no-store, must-revalidate, proxy-revalidate, max-age=0");
		httpResponse.setDateHeader("Expires", 0);

		//UserDetailsImpl userDetails = new UserDetailsImpl("jack.zhou",null);
		GlobalProfileDto globalProfileDto = new GlobalProfileDto();
		//globalProfileDto.setUserDateFormat("MM/dd/yyyy");//3
		//globalProfileDto.setUserTimeFormat("hh:mm a");//7
		globalProfileDto.setCultureType("Chinese (Simplified, PRC)");//34
		globalProfileDto.setUserName("jack.zhou");
		globalProfileDto.setUserId("jack.zhou");
		globalProfileDto.setWorkingCompanyName("My test campany");
		globalProfileDto.setWorkingCompanyId("d6a26382-9999-469e-a88a-d3a7b2df64cd");
		
		AcxiomUser userPrincipal = new AcxiomUser("jack.zhou@acxiom.com","Udhe9&*dg",new ArrayList<GrantedAuthority>(), globalProfileDto);
		
		AcxiomWebAuthenticationToken token= new AcxiomWebAuthenticationToken(userPrincipal,null,userPrincipal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
		
		SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(final FilterConfig fConfig) throws ServletException {
		// do nothing?
	}

}
