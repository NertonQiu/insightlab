#!/usr/bin/env bash

if [ $# -eq 0 ]
  then
    echo "ERROR: Script usage: sh ./jsupdate <cataline-home>";
    echo "INFO: <cataline-home> - path for your tomcat directory.";
    exit 1;
  else
    CATALINA_HOME=$1;

    if [ -z "$CATALINA_HOME" ]
      then
    
        echo "ERROR: CATALINA_HOME environment variable is not set!";
        exit 1;
      else
		sh main_compiled.sh
        rm -rf $CATALINA_HOME/webapps/insightlab/resources/scripts/main_compiled.js;
        rm -rf $CATALINA_HOME/webapps/insightlab/js/yuimain.js;
        rm -rf $CATALINA_HOME/webapps/insightlab/resources/styles/insightlab-sg.css;
        cp ../resources/scripts/main_compiled.js $CATALINA_HOME/webapps/insightlab/resources/scripts/main_compiled.js;
        cp ../js/yuimain.js $CATALINA_HOME/webapps/insightlab/js/yuimain.js;
        cp ../resources/styles/insightlab-sg.css $CATALINA_HOME/webapps/insightlab/resources/styles/insightlab-sg.css;

        cp ../index.html $CATALINA_HOME/webapps/insightlab/index.html;
        cp ../insights/index.html $CATALINA_HOME/webapps/insightlab/insights/index.html;
        cp ../insights/dpa/index.html $CATALINA_HOME/webapps/insightlab/insights/dpa/index.html;
        cp ../insights/personicx/index.html $CATALINA_HOME/webapps/insightlab/insights/personicx/index.html;
        cp ../models/index.html $CATALINA_HOME/webapps/insightlab/models/index.html;
        cp ../models/scorecards/index.html $CATALINA_HOME/webapps/insightlab/models/scorecards/index.html;
        cp ../models/scorecard-details/index.html $CATALINA_HOME/webapps/insightlab/models/scorecard-details/index.html;

        cp -r ../resources/img/ $CATALINA_HOME/webapps/insightlab/resources/img/
        cp -r ../resources/images/ $CATALINA_HOME/webapps/insightlab/resources/images/
        echo "INFO: CATALINA ui sources(js, css, html, img, images) updated";
    fi
fi