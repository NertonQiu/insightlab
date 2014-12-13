package com.acxiom.insightlab.service;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.acxiom.web.sso.dataAccess.model.ApplicationRoleDto;
import com.acxiom.web.sso.dataAccess.model.GlobalProfileDto;
import com.acxiom.web.sso.dataAccess.model.TenantDto;
import com.acxiom.web.sso.security.model.GlobalProfile;

/**
 * Adapter that returns data from SecurityUtils service in json format.
 * 
 * @author dmytro.plekhotkin
 * 
 */
public final class SecurityUtilsJsonAdapter {

	/**
	 * Private constructor. This is a static class and it can't be instantiated.
	 */
	private SecurityUtilsJsonAdapter() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns user details.
	 * 
	 * @return json object
	 * @throws JSONException
	 */
	public static JSONObject getUserDetailsJson() throws JSONException {
		final JSONObject userDetailsJson = new JSONObject();
		final UserDetails userDetails = SecurityUtils.getUserDetails();

		userDetailsJson.put("username", userDetails.getUsername());
		userDetailsJson.put("is_enabled", userDetails.isEnabled());
		userDetailsJson.put("is_account_non_expired",
				userDetails.isAccountNonExpired());
		userDetailsJson.put("is_account_non_locked",
				userDetails.isAccountNonLocked());
		userDetailsJson.put("is_credentials_non_expired",
				userDetails.isCredentialsNonExpired());
		userDetailsJson.put("user_roles", getUserRolesJson());
		userDetailsJson.put("global_profile", getGlobalProfileJson());

		return userDetailsJson;
	}

	/**
	 * Returns global profile.
	 * 
	 * @return json object
	 * @throws JSONException
	 */
	public static JSONObject getGlobalProfileJson() throws JSONException {
		final JSONObject globalProfile = new JSONObject();
		final GlobalProfileDto profile = SecurityUtils.getGlobalProfile();

		globalProfile.put("first_name", profile.getFirstName());
		globalProfile.put("last_name", profile.getLastName());
		globalProfile.put("email", profile.getEmail());
		globalProfile.put("working_company_id", profile.getWorkingCompanyId());

		globalProfile.put("date_format", profile.getUserDateFormat());
		globalProfile.put("time_format", profile.getUserTimeFormat());
		globalProfile.put("timezone_id", profile.getTimeZoneInfoID());
		globalProfile.put("user_domain", profile.getUserDomain());
		globalProfile.put("utc_offset", profile.getUtcOffset());

		return globalProfile;
	}

	/**
	 * Returns company information for current user.
	 * 
	 * @return json object
	 * @throws JSONException
	 */
	public static JSONObject getCompanyInfoJson() throws JSONException {
		final JSONObject companyInfoJson = new JSONObject();
		final TenantDto tenantInfo = SecurityUtils.getTenantInfo();

		companyInfoJson.put("acr_id", tenantInfo.getAcrId());
		companyInfoJson.put("bussiness_unit", tenantInfo.getBusinessUnit());
		companyInfoJson.put("client_number", tenantInfo.getClientNumber());
		companyInfoJson.put("description", tenantInfo.getDescription());

		companyInfoJson.put("display_name", tenantInfo.getDisplayName());
		companyInfoJson.put("file_number", tenantInfo.getFileNumber());
		companyInfoJson.put("gl_number", tenantInfo.getGlNumber());
		companyInfoJson.put("name", tenantInfo.getName());
		companyInfoJson.put("project", tenantInfo.getProject());
		companyInfoJson.put("company_id", tenantInfo.getCompanyId());
		companyInfoJson.put("is_active", tenantInfo.isActive());

		return companyInfoJson;
	}

	/**
	 * Returns user roles for current user.
	 * 
	 * @return json array
	 */
	public static JSONArray getUserRolesJson() {
		final JSONArray userRolesJson = new JSONArray();
		final Collection<? extends GrantedAuthority> authorities = SecurityUtils
				.getUserRoles();
		for (GrantedAuthority grantedAuthority : authorities) {
			userRolesJson.put(grantedAuthority.getAuthority());
		}

		return userRolesJson;
	}

	/**
	 * Returns roles for current application.
	 * 
	 * @return json array
	 * @throws JSONException
	 */
	public static JSONArray getApplicationRolesJson() throws JSONException {

		final JSONArray rolesArray = new JSONArray();

		final List<ApplicationRoleDto> appRoles = SecurityUtils.getApplicationRoles();

		for (ApplicationRoleDto appRole : appRoles) {
			final JSONObject appRoleJson = new JSONObject();
			appRoleJson.put("description", appRole.getDescription());
			appRoleJson.put("name", appRole.getName());
			appRoleJson.put("is_application_company_user_role",
					appRole.isApplicationCompanyUserRole());
			appRoleJson.put("is_application_user_role",
					appRole.isApplicationUserRole());
			appRoleJson.put("is_system_role", appRole.isSystemRole());

			rolesArray.put(appRoleJson);

		}

		return rolesArray;
	}

	/**
	 * Returns authentication cookie details as json object.
	 * 
	 * @return json object
	 * @throws JSONException
	 */
	public static JSONObject getAuthCookieDetailsJson() throws JSONException {

		final JSONObject cookieDetailsJson = new JSONObject();

		final Cookie cookie = SecurityUtils.getAuthCookieDetails();

		cookieDetailsJson.put("comment", cookie.getComment());
		cookieDetailsJson.put("domain", cookie.getDomain());
		cookieDetailsJson.put("max_age", cookie.getMaxAge());
		cookieDetailsJson.put("name", cookie.getName());
		cookieDetailsJson.put("path", cookie.getPath());
		cookieDetailsJson.put("value", cookie.getValue());
		cookieDetailsJson.put("version", cookie.getVersion());

		return cookieDetailsJson;
	}

	/**
	 * Returns user name.
	 * 
	 * @return json object
	 * @throws JSONException
	 */
	public static JSONObject getUserNameJson() throws JSONException {

		final JSONObject userNameJson = new JSONObject();

		final String userName = SecurityUtils.getUserName();

		userNameJson.put("username", userName);

		return userNameJson;
	}

	public static JSONObject getCompanyIdJson() throws JSONException {

		final JSONObject companyIdJson = new JSONObject();

		final String companyId = SecurityUtils.getCompanyId();

		companyIdJson.put("company_id", companyId);

		return companyIdJson;
	}

	public static JSONObject isAuthenticated() throws JSONException {
		final JSONObject isAuthJson = new JSONObject();
		//jack
		//final boolean isAuth = SecurityUtils.isAuthenticated();
		final boolean isAuth = true;
		
		isAuthJson.put("authenticated", isAuth);

		return isAuthJson;
	}
}
