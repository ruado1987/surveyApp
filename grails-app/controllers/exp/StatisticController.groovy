package exp

import groovy.xml.MarkupBuilder

class StatisticController {

    def statisticService

    def genderStatistic = {
        def stat = statisticService.countOpinionBasedOnGender()
        def statColNames = [
                ['string', 'Gender'],
                ['number', 'Count']
        ]
        def statData = buildStatisticModel(stat)
        return [statisticColNames: statColNames, statisticData: statData]
    }

    def getAllRecommendations() {
        def stat = statisticService.allRecommendations()
        render(view: 'recommendations', model: stat)
    }

    def showQuestionsSelection() {
        def questions = Question.findAll('\
                    from Question q \
                    where :yes = any elements(q.options) \
                    and :no = any elements(q.options) \
                    and size(q.options) = 2', [yes: 'Yes', no: 'No'])
        render(view: 'questionsSelection', model: [questions: questions])
    }

    def yesNoStatistic() {
        def q = Question.get(params.questionId)
        if (q) {
            def statistic = statisticService.countOpinionsByQuestion(q.text, 'Yes')
            def statData = buildStatisticModel(statistic)
            render(template: 'statisticDisplay',
                    model: [statistic: statData, chartTitle: "Statistics for question ${q.text}"])
        }
        else {
            def strWriter = new StringWriter()
            def markupBuilder = new MarkupBuilder(strWriter)
            markupBuilder.div {
                span 'No question found'
            }
            render(text: strWriter.toString())
        }
    }

    private def buildStatisticModel(Map statisticMap) {
        def statData = []
            statisticMap.each {k, v ->
                if (k =~ /^[Ff]emale.*/) {
                    statData << ['Female', v]
                }
                else if (k =~ /^[Mm]ale.*/) {
                    statData << ['Male', v]
                }
            }
        statData
    }
}
