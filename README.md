Run the app in development mode
===============================
To load the sample data(fixtures), please use the following command line:
	grails -DtestData=<fixtureName> run-app
e.g.
	grails -DtestData=simpleSurvey run-app
	
	
How to open pages as admin
==========================
Please open url at http://localhost:8080/surveyApp/survey/login?pwd=<thePassword>
You can get the inital password in Bootstrap.groovy, and update it at runtime, by login first, then
go http://localhost:8080/surveyApp/settings page.

To logout, go http://localhost:8080/surveyApp/survey/logout

This is different than the initial accessToken based auth design.
The flag is put in HTTP session, so you don't have to pass the token everywhere.


Load initital data
==================
http://localhost:8080/surveyApp/survey/login?pwd=<thePassword>
then
http://localhost:8080/surveyApp/survey/load/<fileName>
