<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Statistic Display</title>
    <meta name="layout" content="customLayout"/>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <g:javascript library="jquery"/>
    <g:javascript>
    //the map keeps the original values of option objects
    var originValues = {}
    $(document).ready(function() {
       $('#questionId option').each(function(){
           if($(this).html().length > 40) {
                var currentVal = $(this).html()
                originValues[$(this).val()] = currentVal
               $(this).html(currentVal.substring(0,40) + '...')
           }
       })
        $('#questionId').change(function(){
            $('#fullQuestion span').text(originValues[$(this).children('option:selected').val()])
        })
        $('#questionId').css({
            fontWeight: 'bold'
        })
        $('#fullQuestion').css({
            marginTop: '10px'
        })
        $('#fullQuestion span').css({
            marginLeft: '0px',
            fontWeight: 'bold'
        })
    })
    function updateSpinner(status) {
        $("#spinner").css({
            display: status
        })
    }
    </g:javascript>
</head>

<body>
<fieldset>
    <legend>Choose a question from the list below</legend>
    <div>
        <g:form>
            <g:select name="questionId" from="${questions}" optionKey="id" optionValue="text"/>
            <g:submitToRemote
                    update="[success: 'statistic_display', failure: 'error']"
                    value="View Statistic"
                    url="[controller: 'statistic', action: 'yesNoStatistic']"
                    onLoading="updateSpinner('inline')" onComplete="updateSpinner('none')"/>
        </g:form>
    </div>
    <div id="fullQuestion">
        <span></span>
    </div>
</fieldset>
<fieldset>
    <legend>Statistic display</legend>
    <div id='statistic_display'>

    </div>
    <div id="error">

    </div>
</fieldset>
</body>
</html>