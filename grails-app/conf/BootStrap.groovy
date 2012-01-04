import grails.util.Environment
import exp.Settings

class BootStrap {

    def fixtureLoader

    def init = { servletContext ->
        if(Environment.current == Environment.DEVELOPMENT && System.properties.testData){
            fixtureLoader.load(System.properties.testData)
            new Settings(propKey:'adminKey',propValue:'210787').save(failOnError: true)
        }
    }
    def destroy = {
    }
}
