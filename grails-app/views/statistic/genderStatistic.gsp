<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Simple GSP page</title>
      <meta name="layout" content="main"/>
      <script type="text/javascript" src="http://www.google.com/jsapi"></script>
  </head>
  <body>
      <gvisualization:pieCoreChart elementId="chart"
                                   columns="${statisticColNames}"
                                   data="${statisticData}"
                                   width="${400}"
                                   height="${500}"
                                   title="Gender Statistic"/>

      <div id="chart"></div>
  </body>
</html>