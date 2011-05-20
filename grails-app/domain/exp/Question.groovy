package exp

class Question {

    String text
    List options

    static hasMany = [options: String]

    static constraints = {
        text blank: false, unique: true
        options()
    }
}
