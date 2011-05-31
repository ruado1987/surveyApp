<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Female/male statistic</title>
      <meta name="layout" content="customLayout"/>
      <script type="text/javascript" src="http://www.google.com/jsapi"></script>
  </head>
  <body>
      <gvisualization:pieCoreChart elementId="chart"
                                   columns="${statisticColNames}"
                                   data="${statisticData}"
                                   width="${400}"
                                   height="${500}"
                                   title="Gender Statistic"
                                   is3D="${true}"/>

      <div id="chart"></div>
  </body>
</html>