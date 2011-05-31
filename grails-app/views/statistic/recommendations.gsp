<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Recommendations by Male and Female</title>
    <meta name="layout" content="customLayout"/>
</head>

<body>
    <g:render template="recommendationTemplate" model="['gender': 'male', 'recommendations': male]"/>
    <g:render template="recommendationTemplate" model="['gender': 'female', 'recommendations': female]"/>
</body>
</html>