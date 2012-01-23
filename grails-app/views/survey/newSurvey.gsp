<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Create New Survey</title>
      <meta name="layout" content="main">
  </head>
  <body>
        <div>
            <g:hasErrors bean="${surveyObj}">
                <div id="errorPane" style="border-width: 1px">
                    <ol>
                        <g:eachError bean="${surveyObj}">
                            <li><g:message error="${it}"/></li>
                        </g:eachError>
                    </ol>
                </div>
            </g:hasErrors>
            <g:form action="saveSurvey">
                <dl>
                    <dt>
                        <label for="name">Name</label>
                        <g:textField name="name" value="${surveyObj?.name}"/>
                    </dt>
                    <dt>
                        <label for="questions">Choose from the available questions</label>
                        <g:select name="questions" from="${questionList}"
                                  multiple="yes" optionKey="id"
                                  optionValue="text" />
                    </dt>
                    <dt>
                        <g:submitButton name="submit" value="Save Survey"/>
                    </dt>
                </dl>
            </g:form>
        </div>
  </body>
</html>
