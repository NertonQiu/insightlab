package com.acxiom.insightlab.sso.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.service.SecurityUtils;
import com.acxiom.web.sso.dataAccess.UserServiceFacade;
import com.acxiom.web.sso.dataAccess.model.RoleInCompanyDto;
import com.acxiom.web.sso.dataAccess.model.RolesInApplicationDto;

@Service
public class UserServiceClient {

	@Autowired
	private UserServiceFacade userService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserServiceClient.class);
	private static final String ROLE_USER = "user";

	/**
	 * @param appName
	 * @return
	 */
	public boolean isAppUser(final String appName) {
		boolean isAppUser = false;

		RolesInApplicationDto roles = userService.getApplicationRoles(
				SecurityUtils.getAuthCookieDetails(), appName,
				SecurityUtils.getCompanyId());

		if (roles != null) {
			for (RoleInCompanyDto applicationRole : roles.getRoles()) {
				if (applicationRole.getRoleName().equals(ROLE_USER)) {
					isAppUser = true;
				}
			}
		}

		return isAppUser;
	}
}
