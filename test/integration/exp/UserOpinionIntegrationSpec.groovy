package exp

import grails.plugin.spock.IntegrationSpec

class UserOpinionIntegrationSpec extends IntegrationSpec {

    def fixtureLoader

    def 'test save new user opinion'() {
        setup:
            def fixture = fixtureLoader.load {
                (1..7).each {num->
                    "answer${num}"(Answer){
                        question = fixtureLoader['surveyFixture']."question${num}"
                        text = 'Some answer'
                    }
                }
                orphanAnswer(Answer){
                    question = fixtureLoader['surveyFixture'].noOpQuestion
                    text = 'Another answer'
                }
                uo(UserOpinion){
                    submitDate = new Date()
                    survey = Survey.get(1)
                    answers = [answer1, answer2, answer3, answer4, answer5, answer6, answer7, orphanAnswer]
                }
            }
        when:
            def count = UserOpinion.count()
            def uo = UserOpinion.get(1)
        then:
            1 == count
            uo
            8 == uo.answers.size()
            uo.answers == fixture.uo.answers
    }
}
