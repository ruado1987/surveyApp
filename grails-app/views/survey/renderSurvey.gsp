<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Survey questions list</title>
      <meta name="layout" content="main"/>
      <style media="screen" type="text/css">
          * {
              padding-left: 2px;
          }
          span {

              display: block;
              clear: both;
          }
          .headerMsg {
              font-family: serif;
              font-size: large;
              font-style: oblique;
              font-weight: bold;
          }
          .headerMsg1 {
              font-family: serif;
              font-size: medium;
              font-weight: bold;
              padding-bottom: 20px;
          }
          .questionText {
              font-family: sans-serif;
              font-weight: bold;
              font-size: 14px;
          }
          .questionAnswer {
              font-family: sans-serif;
              font-size: 12px;
          }
          .answerList {
              padding-left: 10px;
          }
          .customTextArea {
              width: 250px;
              height: 80px;
              margin-left: 10px;
          }
          .customButton {
              cursor: pointer;
              background: transparent url(<g:resource dir="images/skin" file="database_save.png"/>) 2px 50% no-repeat;
              font-size: 10px;
              font-weight: bold;
              overflow: visible;
              margin-left: 3px;
              margin-top: 10px;
              padding-left: 28px;
              height: 25px;
          }
          .customButton:hover {
              border-color: #b2d1ff
          }
          dl dt{
              margin-bottom: 10px;
          }
          .errorMsg {
              color: red;
              font: 14px sans-serif;
          }
      </style>
  </head>
  <body>
        <span class="headerMsg">Your are taking a survey: ${survey.name}</span>
        <span class="headerMsg1">Please take your valuable time to answer the following questions</span>
        <g:if test="${flash.message}">
            <p class="errorMsg">${flash.message}</p>
        </g:if>
        <g:form action="saveUserOpinion">
            <dl>
                <g:hiddenField name="surveyId" value="${survey.id}"/>
                <g:each in="${survey.questions}" var="question" status="num">
                    <dt>
                        <span class="questionText">${num + 1}. ${question.text}</span>
                        <g:if test="${question.options}">
                            <g:set var="optionList" value="${question.options as List}"/>
                            <div class="answerList">
                                <g:radioGroup name="answer_${question.id}"
                                              values="${optionList}"
                                              labels="${optionList}"
                                              value="${optionList[0]}">
                                    <span class="questionAnswer">${it.radio} ${it.label}</span>
                                </g:radioGroup>
                            </div>
                        </g:if>
                        <g:else>
                            <g:textArea name="answer_${question.id}" cols="2" rows="2" class="customTextArea"/>
                        </g:else>
                    </dt>
                </g:each>
            </dl>
            <div>
                <g:actionSubmit value="Save User Opinion" class="customButton"/>
            </div>
        </g:form>
  </body>
</html>