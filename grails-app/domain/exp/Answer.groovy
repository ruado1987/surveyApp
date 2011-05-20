package exp

class Answer {

    Question question
    String text

    static constraints = {
        text blank: false
        question()
    }
}
