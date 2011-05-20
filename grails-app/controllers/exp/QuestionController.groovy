package exp

class QuestionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static scaffold = true

    def index = {
        redirect(action: "list", params: params)
    }
}
