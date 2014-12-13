#!/usr/bin/env bash
rm -rf ../resources/scripts/main_compiled.js
touch ../resources/scripts/main_compiled.js
chmod 0666 ../resources/scripts/main_compiled.js
java -jar ./closure-compiler.jar \
--compilation_level WHITESPACE_ONLY \
--warning_level VERBOSE \
--charset UTF-8 \
--externs externs.js \
--externs jquery-extends.js \
--third_party \
--js_output_file ../resources/scripts/main_compiled.js \
--js ../js/compatibility.js \
--js ../js/common.js \
--js ../src/scripts/net/HttpRequest.js \
--js ../src/scripts/net/ServletRequest.js \
--js ../src/scripts/net/HttpServletRequest.js \
--js ../resources/scripts/utils/DomUtils.js \
--js ../resources/scripts/utils/StringUtils.js \
--js ../resources/scripts/utils/Utils.js \
--js ../resources/scripts/utils/Template.js \
--js ../resources/scripts/mappers/DataFormatter.js \
--js ../resources/scripts/utils/DateUtils.js \
--js ../resources/scripts/utils/HashUtil.js \
--js ../src/scripts/dom/EventTargetListener.js \
--js ../src/scripts/dom/DataStorage.js \
--js ../src/scripts/widgets/BaseWidget.js \
--js ../src/scripts/widgets/DataLoader.js \
--js ../src/scripts/util/__ns__.js \
--js ../src/scripts/util/StringUtils.js \
--js ../src/scripts/util/PropertyReader.js \
--js ../src/scripts/util/Object.js \
--js ../src/scripts/util/MetaData.js \
--js ../src/scripts/util/Locale.js \
--js ../src/scripts/util/FileUtils.js \
--js ../src/scripts/locale/__ns__.js \
--js ../src/scripts/locale/Calendar.js \
--js ../src/scripts/locale/Validation.js \
--js ../src/scripts/formatters/__ns__.js \
--js ../src/scripts/formatters/DateFormatter.js \
--js ../src/scripts/formatters/NumberFormatter.js \
--js ../resources/scripts/components/Breadcrumb.js \
--js ../resources/scripts/components/DataTableComponent.js \
--js ../resources/scripts/components/DataTableComponentExt.js \
--js ../resources/scripts/components/SliderComponent.js \
--js ../resources/scripts/components/ProgressBarComponent.js \
--js ../resources/scripts/components/KendoChart.js \
--js ../resources/scripts/components/GeoChart.js \
--js ../resources/scripts/components/CheckboxComponent.js \
--js ../resources/scripts/components/ButtonComponent.js \
--js ../resources/scripts/components/InputTagComponent.js \
--js ../resources/scripts/components/LegendComponent.js \
--js ../resources/scripts/components/CheckboxListComponent.js \
--js ../src/scripts/components/BaseComponent.js \
--js ../resources/scripts/components/DropDownComponent.js \
--js ../resources/scripts/widgets/BaseChartsContainer.js \
--js ../resources/scripts/widgets/BaseWidgetsContainer.js \
--js ../resources/scripts/widgets/TargetGroupsList.js \
--js ../resources/scripts/widgets/ViewPortrait.js \
--js ../resources/scripts/widgets/PanelWidget.js \
--js ../resources/scripts/widgets/PanelFilters.js \
--js ../resources/scripts/widgets/FilterWidget.js \
--js ../resources/scripts/widgets/RecentRequestsWidget.js \
--js ../resources/scripts/datatable-plugins/dataTable.plug-ins.js \
--js ../js/jquery.dblclick.js \
--js ../resources/scripts/mappers/DataMapper.js \
--js ../resources/scripts/mappers/KendoMapper.js \
--js ../resources/scripts/pages/InsightsPage.js \
--js ../resources/scripts/pages/HomePage.js \
--js ../resources/scripts/pages/ModelPage.js \
--js ../resources/scripts/pages/GeoselectionPopup.js \
--js ../resources/scripts/pages/Scorecard.js \
--js ../resources/scripts/pages/ScorecardDetails.js \
--js ../resources/scripts/pages/PersonicxPage.js \
--js ../resources/scripts/pages/ScattergramReport.js \
--js ../resources/scripts/pages/PortraitReport.js \
--js ../resources/scripts/pages/TargetGroupReport.js \
--js ../resources/scripts/pages/ProfileAppPage.js \
--js ../resources/scripts/pages/COPage.js \
--js ../resources/scripts/pages/BuildSegmentPage.js 
# lessc ../resources/styles/insightlab-sg.less  > ../resources/styles/insightlab-sg.css;


#java -jar yuicompressor-2.4.8pre.jar \
#  --type css --charset utf-8 --verbose  \
#  -o ../resources/styles/insightlab-sg.min.css  \
#  ../resources/styles/insightlab-sg.css  \

#find "../src/scripts" -name "*.js" -print |
# sed 's/.*/--js &/' |
# xargs java -jar ./closure-compiler.jar \
# --compilation_level ADVANCED_OPTIMIZATIONS \
# --warning_level VERBOSE \
# --charset UTF-8 \
# --process_jquery_primitives \
# --externs externs.js \
# --externs externs-gviz-api.js \
# --js_output_file ../bin/scripts/codebase.js