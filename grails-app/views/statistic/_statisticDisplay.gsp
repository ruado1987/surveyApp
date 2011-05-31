<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<gvisualization:pieCoreChart elementId="statistic_chart"
                             columns="${[['string', 'Gender'],['number', 'Count']]}"
                             data="${statisticData}"
                             width="${400}"
                             height="${500}"
                             title="Gender Statistic"
                             is3D="${true}"/>
<div id='statistic_chart'></div>