package com.acxiom.insightlab.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.acxiom.web.sso.dataAccess.model.ApplicationRoleDto;
import com.acxiom.web.sso.dataAccess.model.GlobalProfileDto;
import com.acxiom.web.sso.dataAccess.model.TenantDto;
import com.acxiom.web.sso.security.model.AcxiomUser;
import com.acxiom.web.sso.security.model.AcxiomWebAuthenticationToken;
import com.acxiom.web.sso.security.model.GlobalProfile;


/**
 * Grants different security information that can be gotten from SSO library:
 * application roles, user roles, global profile, company info, etc.
 * 
 * @author dmytro.plekhotkin
 * 
 */
public final class SecurityUtils {

	/**
	 * Private constructor. This is a static class and it can't be instantiated.
	 */
	private SecurityUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns AcxiomWebAuthenticationToken object that is designed for
	 * presentation of different security information.
	 * 
	 * @return AcxiomWebAuthenticationToken object
	 */
	private static AcxiomWebAuthenticationToken getAcxiomWebAuthToken() {
		final Authentication authentication = SecurityContextHolder
				.getContext().getAuthentication();

		return (AcxiomWebAuthenticationToken) authentication;
	}

	/**
	 * Returns user details.
	 * 
	 * @return UserDetailsImpl object
	 */
	public static AcxiomUser getUserDetails() {
		final AcxiomWebAuthenticationToken authToken = getAcxiomWebAuthToken();
		return (AcxiomUser) authToken.getPrincipal();
	}

	/**
	 * Returns global profile.
	 * 
	 * @return GlobalProfile object
	 */
	public static GlobalProfileDto getGlobalProfile() {
		final AcxiomUser userDetails = getUserDetails();
		return userDetails.getGlobalProfile();
	}

	/**
	 * Returns company info.
	 * 
	 * @return CompanyInfo object
	 */
	public static TenantDto getTenantInfo() {
		final AcxiomWebAuthenticationToken authToken = getAcxiomWebAuthToken();
		return authToken.getTenantInfo();
	}

	/**
	 * Returns user roles.
	 * 
	 * @return Collection of user roles
	 */
	public static Collection<? extends GrantedAuthority> getUserRoles() {

		final AcxiomUser userDetails = getUserDetails();
		return userDetails.getAuthorities();
	}

	/**
	 * Returns application roles.
	 * 
	 * @return list of application roles
	 */
	public static List<ApplicationRoleDto> getApplicationRoles() {
		final AcxiomWebAuthenticationToken authToken = getAcxiomWebAuthToken();
		return authToken.getApplicationRoles();
	}

	/**
	 * Returns authentication cookie details.
	 * 
	 * @return Cookie object
	 */
	public static Cookie getAuthCookieDetails() {
		final AcxiomWebAuthenticationToken authToken = getAcxiomWebAuthToken();
		return (Cookie) authToken.getDetails();
	}

	/**
	 * Returns user name.
	 * 
	 * @return string
	 */
	public static String getUserName() {
		final AcxiomUser userDetails = getUserDetails();
		return userDetails.getUsername();

	}

	/**
	 * Returns true if user is authenticated.
	 * 
	 * @return boolean value that shows if user is authenticated
	 */
	public static boolean isAuthenticated() {
		final AcxiomWebAuthenticationToken authToken = getAcxiomWebAuthToken();
		return authToken.isAuthenticated();
	}

	/**
	 * Returns true if user is authenticated.
	 * 
	 * @return boolean value that shows if user is authenticated
	 */
	public static String getCompanyId() {
		final GlobalProfileDto globalProfile = getGlobalProfile();
		return globalProfile.getWorkingCompanyId();
	}
}
