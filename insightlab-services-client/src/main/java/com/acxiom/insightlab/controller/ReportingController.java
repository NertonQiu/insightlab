package com.acxiom.insightlab.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.formatter.DropdownFormatter;
import com.acxiom.insightlab.api.formatter.SettingsFormatter;
import com.acxiom.insightlab.api.formatter.TableFormatter;
import com.acxiom.insightlab.api.service.impl.ReportingServiceImpl;
import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;
import com.acxiom.insightlab.service.ServletUtils;

/**
 * 
 * Works with Reporting service and returns reporting service data to client.
 * 
 * @author dmytro.plekhotkin
 * @since 2013-03-19
 * 
 */
@Controller
@RequestMapping("/reportingService")
public class ReportingController extends GlobalExceptionHandler {

  private static final String REFERENCE_ID = "referenceid";
  private static final String INSIGHT_ID = "insightid";

  @Autowired
  private ReportingServiceImpl reportingService;

  @RequestMapping("/savePersonicxTargetGroup")
  public void savePersonicxTargetGroup(
      @RequestParam(value = "targettype", required = true) final String targetType,
      @RequestParam(value = "targetid", required = true) final String targetId,
      @RequestParam(value = "referencetype", required = true) final String referenceType,
      @RequestParam(value = REFERENCE_ID, required = true) final String referenceId,
      @RequestParam(value = "name", required = true) final String name,
      @RequestParam(value = "segment", required = true) final String[] segment,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);
    WebHelper.addUserName(requestParams);
    WebHelper.addTenantId(requestParams);

    JSONObject dataJson = reportingService.savePersonicxTargetGroup(requestParams);

    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/getPersonicxPortraitReport")
  public void getPersonicxPortraitReport(
      @RequestParam(value = "targettype", required = true) final String targetType,
      @RequestParam(value = "targetid", required = true) final String targetId,
      @RequestParam(value = "referencetype", required = true) final String referenceType,
      @RequestParam(value = REFERENCE_ID, required = true) final String referenceId,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getPersonicxPortraitReport(requestParams);
    TableFormatter.format(dataJson, "portraitData");
    WebHelper.sendJsonResponse(dataJson, response);

  }

  @RequestMapping("/getScattergramReport")
  public void getScattergramReport(
      @RequestParam(value = INSIGHT_ID, required = true) final String insightId,
      @RequestParam(value = REFERENCE_ID, required = true) final String referenceId,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {

    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getScattergramReport(requestParams);

    TableFormatter.format(dataJson, "scattergramData");

    WebHelper.sendJsonResponse(dataJson, response);

  }

  @RequestMapping("/getDPACategories")
  public void getDPACategories(
      @RequestParam(value = INSIGHT_ID, required = true) final String insightId,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {

    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getDPACategories(requestParams);

    DropdownFormatter.format(dataJson, "results");

    WebHelper.sendJsonResponse(dataJson, response);

  }

  // TODO: add additional mandatory parameter.
  @RequestMapping("/getDPAQuestions")
  public void getDPAQuestions(
      @RequestParam(value = "category", required = true) final String category,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {

    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getDPAQuestions(requestParams);

    DropdownFormatter.format(dataJson, "results");

    WebHelper.sendJsonResponse(dataJson, response);

  }

  @RequestMapping("/getDPAResponses")
  public void getDPAResponses(
      @RequestParam(value = "category", required = true) final String category,
      @RequestParam(value = "question", required = true) final String question,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {

    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getDPAResponses(requestParams);
    TableFormatter.format(dataJson, "results");

    WebHelper.sendJsonResponse(dataJson, response);

  }

  @RequestMapping("/getDPAReport")
  public void getDPAReport(
      @RequestParam(value = INSIGHT_ID, required = true) final String insightId,
      @RequestParam(value = "category", required = true) final String category,
      @RequestParam(value = "question", required = false) final String question,
      @RequestParam(value = "over", required = false) final String over,
      @RequestParam(value = "under", required = false) final String under,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {

    // validate

    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getDPAReport(requestParams);

    TableFormatter.format(dataJson, "reportData");

    WebHelper.sendJsonResponse(dataJson, response);

  }

  @RequestMapping("/getPersonicxTargetGroups")
  public void getPersonicxTargetGroups(
      @RequestParam(value = "targetid", required = true) final String targetId,
      @RequestParam(value = "targettype", required = true) final String targetType,
      @RequestParam(value = REFERENCE_ID, required = true) final String referenceId,
      @RequestParam(value = "referencetype", required = true) final String referenceType,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);
    WebHelper.addUserName(requestParams);
    WebHelper.addTenantId(requestParams);

    JSONObject dataJson = reportingService.getPersonicxTargetGroups(requestParams);

    TableFormatter.format(dataJson, "results");

    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/getPropensityDetailReport")
  public void getPropensityDetailReport(
      @RequestParam(value = INSIGHT_ID, required = true) final String insightId,
      @RequestParam(value = "modelid", required = true) final String modelId,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getPropensityDetailReport(requestParams);

    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/getAudiencePropensitiesDPAReport")
  public void getAudiencePropensitiesDPAReport(
      @RequestParam(value = INSIGHT_ID, required = true) final String insightId,
      @RequestParam(value = "propensitycategoryid", required = false) String propensityCategoryId,
      @RequestParam(value = "overcount", required = true) final String overcount,
      @RequestParam(value = "undercount", required = true) final String undercount,

      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getAudiencePropensitiesDPAReport(requestParams);

    TableFormatter.format(dataJson, "reportData");

    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/savePersonicxPortrait")
  public void savePersonicxPortrait(
      @RequestParam(value = "targettype", required = true) final String targetType,
      @RequestParam(value = "targetid", required = true) final String targetId,
      @RequestParam(value = "referencetype", required = true) final String referenceType,
      @RequestParam(value = REFERENCE_ID, required = true) final String referenceId,
      @RequestParam(value = "name", required = true) final String name,
      @RequestParam(value = "description", required = false) final String description,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);
    WebHelper.addUserName(requestParams);
    WebHelper.addTenantId(requestParams);
    JSONObject dataJson = reportingService.savePersonicxPortrait(requestParams);

    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/getPersonicxPortraits")
  public void getPersonicxPortraits(
      @RequestParam(value = "targettype", required = false) final String targetType,
      @RequestParam(value = "targetid", required = false) final String targetId,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);

    WebHelper.addUserName(requestParams);
    WebHelper.addTenantId(requestParams);

    JSONObject dataJson = reportingService.getPersonicxPortraits(requestParams);
    SettingsFormatter.addDateTimeSettings(dataJson);
    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/updatePersonicxPortrait")
  public void updatePersonicxPortrait(
      @RequestParam(value = "portraitid", required = true) final String portraitId,
      @RequestParam(value = "description", required = true) final String description,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.updatePersonicxPortrait(requestParams);
    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/deletePersonicxPortrait")
  public void deletePersonicxPortrait(
      @RequestParam(value = "portraitid", required = true) final String portraitId,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.deletePersonicxPortrait(requestParams);
    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/getPersonicxPortraitStub")
  public void getPersonicxPortraitStub(
      @RequestParam(value = "portraitid", required = true) final String portraitId,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getPersonicxPortraitStub(requestParams);
    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/getPersonicxTargetGroupReport")
  public void getPersonicxTargetGroupReport(
      @RequestParam(value = "targetgroupid", required = true) final String[] targetGroupId,
      final HttpServletRequest request, final HttpServletResponse response) throws JSONException,
      BaseDataApiException, IOException {
    JSONObject requestParams = ServletUtils.getParams(request);

    JSONObject dataJson = reportingService.getPersonicxTargetGroupReport(requestParams);
    WebHelper.sendJsonResponse(dataJson, response);
  }

  @RequestMapping("/getTemporarySegmentPersonicxPortraitReport")
  public void getTemporarySegmentPersonicxPortraitReport(final HttpServletRequest request,
      final HttpServletResponse response) throws JSONException, BaseDataApiException, IOException {

    StringBuilder jb = new StringBuilder();
    String line = null;

    BufferedReader reader = request.getReader();
    while ((line = reader.readLine()) != null) {
      jb.append(line);
    }


    JSONObject requestParams = new JSONObject(jb.toString());

    JSONObject dataJson =
        reportingService.getTemporarySegmentPersonicxPortraitReport(requestParams);

    TableFormatter.format(dataJson, "portraitData");

    WebHelper.sendJsonResponse(dataJson, response);
  }

}
