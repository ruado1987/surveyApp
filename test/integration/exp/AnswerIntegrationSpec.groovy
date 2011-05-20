package exp

import grails.plugin.spock.IntegrationSpec

class AnswerIntegrationSpec extends IntegrationSpec {

    def fixtureLoader

    def 'test save new answer'() {
        when:
            def fixture = fixtureLoader.load {
                simpleQuestion(Question){
                    text = 'how old are you?'
                }
                simpleAnswer(Answer){
                    question = simpleQuestion
                    text = '25'
                }
            }
        then:
            fixture.simpleQuestion == fixture.simpleAnswer.question
    }
}
