package exp

class Answer {

    Question question
    String text

    static constraints = {
        text blank: true
        question()
    }

    static mapping = {
        cache: 'read-only'
    }
}
