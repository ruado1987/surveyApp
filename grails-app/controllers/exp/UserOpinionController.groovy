package exp

import org.springframework.dao.DataIntegrityViolationException

class UserOpinionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userOpinionInstanceList: UserOpinion.list(params), userOpinionInstanceTotal: UserOpinion.count()]
    }

    def create() {
        [userOpinionInstance: new UserOpinion(params)]
    }

    def save() {
        def userOpinionInstance = new UserOpinion(params)
        if (!userOpinionInstance.save(flush: true)) {
            render(view: "create", model: [userOpinionInstance: userOpinionInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'userOpinion.label', default: 'UserOpinion'), userOpinionInstance.id])
        redirect(action: "show", id: userOpinionInstance.id)
    }

    def show() {
        def userOpinionInstance = UserOpinion.get(params.id)
        if (!userOpinionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'userOpinion.label', default: 'UserOpinion'), params.id])
            redirect(action: "list")
            return
        }

        [userOpinionInstance: userOpinionInstance]
    }

    def edit() {
        def userOpinionInstance = UserOpinion.get(params.id)
        if (!userOpinionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userOpinion.label', default: 'UserOpinion'), params.id])
            redirect(action: "list")
            return
        }

        [userOpinionInstance: userOpinionInstance]
    }

    def update() {
        def userOpinionInstance = UserOpinion.get(params.id)
        if (!userOpinionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userOpinion.label', default: 'UserOpinion'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (userOpinionInstance.version > version) {
                userOpinionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'userOpinion.label', default: 'UserOpinion')] as Object[],
                          "Another user has updated this UserOpinion while you were editing")
                render(view: "edit", model: [userOpinionInstance: userOpinionInstance])
                return
            }
        }

        userOpinionInstance.properties = params

        if (!userOpinionInstance.save(flush: true)) {
            render(view: "edit", model: [userOpinionInstance: userOpinionInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'userOpinion.label', default: 'UserOpinion'), userOpinionInstance.id])
        redirect(action: "show", id: userOpinionInstance.id)
    }

    def delete() {
        def userOpinionInstance = UserOpinion.get(params.id)
        if (!userOpinionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'userOpinion.label', default: 'UserOpinion'), params.id])
            redirect(action: "list")
            return
        }

        try {
            userOpinionInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'userOpinion.label', default: 'UserOpinion'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'userOpinion.label', default: 'UserOpinion'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
