import exp.Question

def questionOp = [
        'What is your gender?': ['Male', 'Female'],
        'Which range includes your age?': ['18-22', '22-30', '30-40', 'above 40'],
        'What is your major?': ['Information Technology', 'Business', 'Accounting', 'Other'],
        'If your major is Information Technology, please let us know your specialty': ['Network administrator', 'Software developer', 'Web Designer', 'Other'],
        'Are you a member of some popular social networks like Facebook, Twitter, etc': ['Yes', 'No'],
        'Have you ever discussed your work, technique problems, or your new findings on existing popuplar social networks?': ['Yes', 'No'],
        'If your answer for the above question is No, please let us know your primary reasons': [],
        'If there exists a social network which promotes the act of sharing and contributing creative ideas, are you willing to join?': ['Yes', 'No'],
        'How likely are you to recommend this kind of social network to your friends/colleagues?': ['Likely', 'Neutral', 'Unlikely'],
        'Among basic features like sharing photos, posting comments, what are the other features you would like to recommend?': []
]

fixture {
    questionOp.eachWithIndex {k, v, num ->
        "question${num + 1}"(Question) {
            text = k
            options = v
        }
    }

    noOpQuestion(Question) {
        text = 'a Question'
    }
}
