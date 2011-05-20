package exp

import grails.plugin.spock.IntegrationSpec

class SurveyIntegrationSpec extends IntegrationSpec {

    def "test save new survey"() {
        def survey = new Survey(name: 'A survey')
        questionList.each {q->
            survey.addToQuestions(q)
        }
        when:
            def result = survey.save(flush: true)
        then:
            result
            Survey.count() > 0
            Survey.findByName('A survey')
        where:
            questionList << [[
                             new Question(text: 'How old are u?'),
                             new Question(text: 'What is your name?'),
                             new Question(text: 'What is your interest?'),
                             new Question(text: 'Where are you?'),
                             new Question(text: 'What is your habit?')
                             ]]
    }
}
