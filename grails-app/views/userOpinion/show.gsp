
<%@ page import="exp.UserOpinion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'userOpinion.label', default: 'UserOpinion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-userOpinion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-userOpinion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list userOpinion">
			
				<g:if test="${userOpinionInstance?.submitDate}">
				<li class="fieldcontain">
					<span id="submitDate-label" class="property-label"><g:message code="userOpinion.submitDate.label" default="Submit Date" /></span>
					
						<span class="property-value" aria-labelledby="submitDate-label"><g:formatDate date="${userOpinionInstance?.submitDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${userOpinionInstance?.survey}">
				<li class="fieldcontain">
					<span id="survey-label" class="property-label"><g:message code="userOpinion.survey.label" default="Survey" /></span>
					
						<span class="property-value" aria-labelledby="survey-label"><g:link controller="survey" action="show" id="${userOpinionInstance?.survey?.id}">${userOpinionInstance?.survey?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${userOpinionInstance?.answers}">
				<li class="fieldcontain">
					<span id="answers-label" class="property-label"><g:message code="userOpinion.answers.label" default="Answers" /></span>
					
						<g:each in="${userOpinionInstance.answers}" var="a">
						<div class="property-value" aria-labelledby="answers-label">${a?.question.text}<br/>${a?.text}<br/><br/></div>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${userOpinionInstance?.id}" />
					<g:link class="edit" action="edit" id="${userOpinionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
