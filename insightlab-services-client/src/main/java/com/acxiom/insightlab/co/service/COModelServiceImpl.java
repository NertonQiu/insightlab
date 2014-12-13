package com.acxiom.insightlab.co.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.model.ViewModel;
import com.acxiom.insightlab.api.model.ViewModelAdapter;
import com.acxiom.insightlab.api.utils.JsonUtils;
import com.acxiom.insightlab.co.dao.COModelDAO;
import com.acxiom.insightlab.co.model.COModel;
import com.acxiom.insightlab.co.model.ModelAdapter;
import com.acxiom.insightlab.sso.service.UserServiceClient;

@Service
public class COModelServiceImpl implements COModelService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(COModelServiceImpl.class);
	@Autowired
	private COModelDAO coModelDao;

	@Value("${co.applicationName}")
	private String coApplicationName;

	@Autowired
	private UserServiceClient userService;

	public JSONObject getModelList() throws BaseDataApiException, JSONException {
		List<ViewModel> adaptedModelList = new ArrayList<ViewModel>();
		boolean isCOUser = userService.isAppUser(coApplicationName);
		if (isCOUser) {
			List<COModel> coModelList = coModelDao.getModelList();
			List<ViewModel> adaptedCoModelList = new ArrayList<ViewModel>();

			for (COModel model : coModelList) {
				adaptedCoModelList.add(new ViewModelAdapter(new ModelAdapter(
						model)));
			}
			adaptedModelList.addAll(adaptedCoModelList);
		}

		net.sf.json.JSONArray adaptedModelListJson = new net.sf.json.JSONArray();

		adaptedModelListJson.addAll(net.sf.json.JSONArray
				.fromObject(adaptedModelList));

		JSONObject result = JsonUtils.stringToJSONObject(adaptedModelListJson
				.toString());
		result.put("isCOUser", isCOUser);
		return result;
	}

	@Override
	public JSONObject getModel(final String modelId) throws JSONException,
			BaseDataApiException {

		JSONObject model = null;
		boolean isCOUser = userService.isAppUser(coApplicationName);

		if (isCOUser) {
			model = coModelDao.getModel(modelId);

		}
		return model;
	}

}
