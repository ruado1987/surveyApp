
<%@ page import="exp.UserOpinion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'userOpinion.label', default: 'UserOpinion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-userOpinion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-userOpinion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="submitDate" title="${message(code: 'userOpinion.submitDate.label', default: 'Submit Date')}" />
					
						<th><g:message code="userOpinion.survey.label" default="Survey" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${userOpinionInstanceList}" status="i" var="userOpinionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${userOpinionInstance.id}">${fieldValue(bean: userOpinionInstance, field: "submitDate")}</g:link></td>
					
						<td>${userOpinionInstance.survey.name}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${userOpinionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
