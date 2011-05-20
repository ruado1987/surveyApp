<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Simple GSP page</title>
      <meta name="layout" content="main"/>
      <style media="screen" type="text/css">
          p {
              font: bold 14px sans-serif;
              margin-bottom: 10px;
          }
          a:hover {
              color: #b2d1ff;
          }
      </style>
  </head>
  <body>
    <p>
        The resource you are looking for is not found. Please make sure you specify a correct url.
    </p>
    <g:link uri="/"><span>Click here to return to the welcome page</span></g:link>
  </body>
</html>