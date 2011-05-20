<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Thank you page</title>
      <meta name="layout" content="main"/>
      <meta http-equiv="REFRESH" content="10;URL=http://google.com"/>
      <g:javascript library="jquery" />
  </head>
  <body>
      <div>
          <p style="font: bold 14px sans-serif;margin-left: 5px">
              Thank you for your time and valuable opinions.
              We are about to move you from this site to the google search page within <span id="second">10</span> second(s)
          </p>
      </div>
      <g:javascript>
        var intervalId = setInterval("countdown()",1000)
        var count=10
        function countdown(){
            if(count-- >0) {
                $("#second").html(count)
            } else{
                clearInterval(intervalId)
            }
        }
      </g:javascript>
  </body>
</html>