package exp

import grails.test.*
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.FileSystemResourceLoader

class URLMappingIntegrationTests extends GrailsUrlMappingsTestCase {

    void setUp(){
        super.setUp()
        patternResolver = new PathMatchingResourcePatternResolver(new FileSystemResourceLoader())
    }
    void testMapping() {
        assertForwardUrlMapping(404, view: 'notfound')
        assertForwardUrlMapping(500, view: 'error')
        assertUrlMapping('/', view: 'index')
        assertUrlMapping('/survey/renderSurvey/1', controller: 'survey', action: 'renderSurvey') {
            id = 1
        }
    }
}
