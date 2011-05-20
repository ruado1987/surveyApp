<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
        <title>Welcome page</title>
        <meta name="layout" content="main"/>
        <style media="screen" type="text/css">
            .errorPane {
                padding-top: 10px;
                padding-bottom: 10px;
                color: #cc0000;
            }
            body p,span {
                margin-left: 5px;
                padding-bottom: 10px;
                font-family: sans-serif;
                font-size: 14px;
            }
            a:hover {
                color: #b2d1ff
            }
        </style>
  </head>
  <body>
        <div>
            <p>You are going to take part in a short survey on social networks. Hope you kindly
            spend your valuable time to share your opinions.</p>
            <g:if test="${flash.message}">
                <div class="errorPane">
                    <span>${flash.message}</span>
                </div>
            </g:if>
            <g:link controller="survey" action="renderSurvey" id="1"><span>Click here to enter the survey</span></g:link>
        </div>
  </body>
</html>