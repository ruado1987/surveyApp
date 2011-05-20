package exp

class Survey {

    String name
    List questions

    static hasMany = [questions: Question]

    static constraints = {
        name blank: false, unique: true
        questions minSize: 5
    }
}
