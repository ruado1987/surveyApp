package exp

import grails.plugin.spock.UnitSpec

class QuestionSpec extends UnitSpec {

    def "test constraints on scalar values"() {
        setup:
            mockForConstraintsTests Question, [new Question(text: 'duplicate')]
        when:
            def question = new Question(text: value)
            def result = question.validate()
        then:
            result == valid
            validator == question.errors['text']
        where:
            value << ['', null, 'q1', 'duplicate']
            valid << [false, false, true, false]
            validator << ['blank', 'nullable', null, 'unique']
    }

    /*def "test constraints on collection values" () {
        setup:
            mockDomain Question
        when:
            def question = new Question(text: 'a question')
            options.each {
                question.addToOptions(it)
            }
            def result = question.validate()
        then:
            assert result == valid, "Validate result of ${options} is not as expected"
            validator == question.errors['options']
        where:
            options << [['1','2'], ['1','2','3','4'], ['1','2','3','4','5']]
            valid << [false, true, false]
            validator << ['size', null, 'size']
    }*/

    /*def "test constraints on empty collection values" () {
          setup:
            mockForConstraintsTests Question
          when:
            def question = new Question('text': 'a question', options: [])
            def result = question.validate()
          then:
            result
            'size' == question.errors['options']
    }*/
}
