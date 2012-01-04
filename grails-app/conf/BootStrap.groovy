import grails.util.Environment

class BootStrap {

    def fixtureLoader

    def init = { servletContext ->
        if(Environment.current == Environment.DEVELOPMENT && System.properties.testData){
            fixtureLoader.load(System.properties.testData)
        }
    }
    def destroy = {
    }
}
