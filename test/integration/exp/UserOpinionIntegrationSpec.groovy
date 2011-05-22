package exp

import grails.plugin.spock.IntegrationSpec

class UserOpinionIntegrationSpec extends IntegrationSpec {

    def fixtureLoader

    def 'test save new user opinion'() {
        setup:
            def fixture = fixtureLoader.load('simpleSurvey').load {
                (1..9).each {num->
                    "answer${num}"(Answer){
                        question = ref("question${num}")
                        text = 'Some answer'
                    }
                }
                orphanAnswer(Answer){
                    question = noOpQuestion
                    text = 'Another answer'
                }
                uo(UserOpinion){
                    submitDate = new Date() + 9
                    survey = survey
                    answers = [answer1, answer2, answer3, answer4,
                                answer5, answer6, answer7,
                                answer8, answer9, orphanAnswer]
                }
            }
        when:
            def count = UserOpinion.count()
            def uo = UserOpinion.findBySubmitDate(new Date() + 9)
        then:
            0 < count
            uo
            uo.answers == fixture.uo.answers
    }

    def 'test query for the number of user opinions' (){
        setup:
            fixtureLoader.load('simpleSurvey').load {
                simpleSurvey(Survey){
                    name = 'A survey'
                    questions = [
                             question1,
                             question2,
                             question3,
                             question4,
                             question5
                    ]
                }
                (2..5).each {num->
                    "answer${num}"(Answer){
                        question = ref("question${num}")
                        text = 'An answer'
                    }
                }
                uo1(UserOpinion){
                    submitDate = new Date()
                    survey = simpleSurvey
                    answers = [
                        new Answer(question: question1, text: 'Female'),
                        answer2, answer3, answer4, answer5
                    ]
                }

                uo2(UserOpinion){
                    submitDate= new Date()
                    survey = simpleSurvey
                    answers = [
                         new Answer(question: question1, text: 'Female'),
                         answer2, answer3, answer4, answer5
                    ]
                }
                uo3(UserOpinion){
                    submitDate= new Date()
                    survey = simpleSurvey
                    answers = [
                         new Answer(question: question1, text: 'Male'),
                         answer2, answer3, answer4, answer5
                    ]
                }
            }
        when:
            def femaleAmt = countOpinionBaseOnGender('Female')
            def maleAmt = countOpinionBaseOnGender('Male')
        then:
            2 == femaleAmt
            1 == maleAmt
    }

    private countOpinionBaseOnGender(gender = 'Male'){
        def cb = UserOpinion.createCriteria()
        def amt = cb.count {
                answers {
                    and {
                        question {
                            eq('text', 'What is your gender?')
                        }
                        eq('text', gender)
                    }
                }
        }
        amt
    }
}
