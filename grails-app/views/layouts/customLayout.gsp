<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><g:layoutTitle default="Grails"/></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <blueprint:resources/>
    <g:layoutHead/>
    <g:javascript library="application"/>
</head>

<body>
<div class="container">
    <div id="spinner" class="spinner" style="display:none;">
        <img src="${resource(dir: 'images', file: 'spinner.gif')}"
             alt="${message(code: 'spinner.alt', default: 'Loading...')}"/>
    </div>

    <div id="grailsLogo"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}"
                                                          alt="Grails" border="0"/></a></div>

    <div class="span-16 colborder">
        <g:layoutBody/>
    </div>

    <div class="span-7 last">
        <div class="box">
            <span class="text, large, loud">Currently, the following statistics are supported, click the links below to get each statistic displayed</span>
        </div>
        <dd>
            <dt class="text"><g:link controller="statistic" action="genderStatistic">Show number of males/females</g:link> </dt>
            <dt class="text"><g:link controller="statistic" action="getAllRecommendations">Show recommendations of males/females</g:link> </dt>
            <dt class="text"><g:link controller="statistic" action="showQuestionsSelection">Show number of males/females based on question</g:link> </dt>
        </dd>
    </div>
</div>
</body>
</html>