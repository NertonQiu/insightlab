package com.acxiom.insightlab.globalnav.service;

import javax.servlet.http.HttpServletRequest;

import com.acxiom.insightlab.globalnav.model.GlobalNav;

public interface GlobalNavService {

	GlobalNav getNavigation(HttpServletRequest request);
	
}
