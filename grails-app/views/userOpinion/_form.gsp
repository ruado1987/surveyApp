<%@ page import="exp.UserOpinion" %>



<div class="fieldcontain ${hasErrors(bean: userOpinionInstance, field: 'submitDate', 'error')} required">
	<label for="submitDate">
		<g:message code="userOpinion.submitDate.label" default="Submit Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="submitDate" precision="day"  value="${userOpinionInstance?.submitDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: userOpinionInstance, field: 'survey', 'error')} required">
	<label for="survey">
		<g:message code="userOpinion.survey.label" default="Survey" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="survey" name="survey.id" from="${exp.Survey.list()}" optionKey="id" required="" value="${userOpinionInstance?.survey?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userOpinionInstance, field: 'answers', 'error')} ">
	<label for="answers">
		<g:message code="userOpinion.answers.label" default="Answers" />
		
	</label>
	<g:select name="answers" from="${exp.Answer.list()}" multiple="multiple" optionKey="id" size="5" value="${userOpinionInstance?.answers*.id}" class="many-to-many"/>
</div>

