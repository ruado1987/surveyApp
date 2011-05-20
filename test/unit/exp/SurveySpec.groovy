package exp

import grails.plugin.spock.UnitSpec

class SurveySpec extends UnitSpec {

    def "test constraints on scalar value"() {
        setup:
            def questionList = []
            (1..5).each {
                questionList << new Question(text: "This is a question ${it}")
            }
            mockForConstraintsTests Survey, [new Survey(name: 'a unique survey', questions: questionList)]
        when:
            def survey = new Survey(name: value)
            def result = survey.validate()
        then:
            assert valid == result, "Validation result of _${value}_ is not as expected"
            validator == survey.errors['name']
        where:
            value << ['', 'A survey', null, 'a unique survey']
            valid << [false, true, false, false]
            validator << ['blank', null, 'nullable', 'unique']
    }

    def "test constraints on collection values" () {
        setup:
            mockForConstraintsTests Survey
        when:
            def survey = new Survey(name: 'a Survey', questions: questions)
            def result = survey.validate()
        then:
            !result
            'minSize' == survey.errors['questions']
        where:
            questions << [[], [new Question(text: 'A Question')]]
    }
}
