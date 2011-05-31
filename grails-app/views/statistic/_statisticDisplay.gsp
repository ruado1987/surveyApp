<%
    def columnNames = [['string', 'Gender'],['number', 'Count']]
    def summary = "${chartTitle}".toString()
%>

<gvisualization:pieCoreChart elementId="statistic_chart"
                             columns="${columnNames}"
                             data="${statistic}"
                             width="${400}"
                             height="${500}"
                             title="${summary}"
                             is3D="${true}"
                             dynamicLoading="${true}"/>
<div id='statistic_chart'></div>