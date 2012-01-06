package exp



import org.junit.*
import grails.test.mixin.*

@TestFor(UserOpinionController)
@Mock(UserOpinion)
class UserOpinionControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/userOpinion/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.userOpinionInstanceList.size() == 0
        assert model.userOpinionInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.userOpinionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.userOpinionInstance != null
        assert view == '/userOpinion/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/userOpinion/show/1'
        assert controller.flash.message != null
        assert UserOpinion.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/userOpinion/list'


        populateValidParams(params)
        def userOpinion = new UserOpinion(params)

        assert userOpinion.save() != null

        params.id = userOpinion.id

        def model = controller.show()

        assert model.userOpinionInstance == userOpinion
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/userOpinion/list'


        populateValidParams(params)
        def userOpinion = new UserOpinion(params)

        assert userOpinion.save() != null

        params.id = userOpinion.id

        def model = controller.edit()

        assert model.userOpinionInstance == userOpinion
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/userOpinion/list'

        response.reset()


        populateValidParams(params)
        def userOpinion = new UserOpinion(params)

        assert userOpinion.save() != null

        // test invalid parameters in update
        params.id = userOpinion.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/userOpinion/edit"
        assert model.userOpinionInstance != null

        userOpinion.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/userOpinion/show/$userOpinion.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        userOpinion.clearErrors()

        populateValidParams(params)
        params.id = userOpinion.id
        params.version = -1
        controller.update()

        assert view == "/userOpinion/edit"
        assert model.userOpinionInstance != null
        assert model.userOpinionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/userOpinion/list'

        response.reset()

        populateValidParams(params)
        def userOpinion = new UserOpinion(params)

        assert userOpinion.save() != null
        assert UserOpinion.count() == 1

        params.id = userOpinion.id

        controller.delete()

        assert UserOpinion.count() == 0
        assert UserOpinion.get(userOpinion.id) == null
        assert response.redirectedUrl == '/userOpinion/list'
    }
}
