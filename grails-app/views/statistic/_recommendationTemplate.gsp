<fieldset>
    <legend>${gender.capitalize()} recommendations</legend>
    <g:if test="${!recommendations}">
        <span>There is no recommendations from ${gender}</span>
    </g:if>
    <g:else>
            <dl>
        <g:each in="${recommendations}" var="rec">
            <dt><span>${rec}</span></dt>
        </g:each>
    </dl>
    </g:else>
</fieldset>