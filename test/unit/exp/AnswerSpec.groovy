package exp

import grails.plugin.spock.UnitSpec

class AnswerSpec extends UnitSpec {

    def "test constraints on scalar value"() {
        setup:
            mockForConstraintsTests Answer
        when:
            def answer = new Answer(text: value, question: aQuestion)
            def result = answer.validate()
        then:
            valid == result
            validator == answer.errors[field]
        where:
            value << ['', 'a reply','an answer']
            aQuestion << [new Question(text: 'a simple question'),
                          null, new Question(text: 'another question')]
            valid << [false,false,true]
            validator << ['blank', 'nullable', null]
            field << ['text', 'question', 'text']
    }
}
