import exp.Answer

include 'sampleQuestions'

fixture {
    maleAnswer(Answer, question: question1, text: 'Male')
    femaleAnswer(Answer, question: question1, text: 'Female')
    ageAnswer(Answer, question: question2, text: '18-22')

    (1..10).each {num ->
        "answer${num}"(Answer) {
            question = ref("question${num}")
            text = 'An answer'
        }
    }
}