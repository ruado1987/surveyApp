import exp.Answer
import exp.Survey
import exp.UserOpinion

include "sampleAnswers"

fixture {
    simpleSurvey(Survey) {
        name = 'A survey'
        questions = [
                question1,
                question2,
                question3,
                question4,
                question5
        ]
    }
    (2..5).each {num ->
        "answer${num}"(Answer) {
            question = ref("question${num}")
            text = 'An answer'
        }
    }

    uo1(UserOpinion) {
        submitDate = new Date()
        survey = simpleSurvey
        answers = [
                femaleAnswer,
                ageAnswer, answer3, answer4, answer5
        ]
    }

    uo2(UserOpinion) {
        submitDate = new Date()
        survey = simpleSurvey
        answers = [
                femaleAnswer,
                ageAnswer, answer3, answer4, answer5
        ]
    }
    uo3(UserOpinion) {
        submitDate = new Date()
        survey = simpleSurvey
        answers = [
                maleAnswer,
                ageAnswer, answer3, answer4, answer5
        ]
    }
}
