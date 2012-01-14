package exp

abstract class AbstractConstraintSpec extends spock.lang.Specification {
	 
    void validateConstraint(String validator, ouv, String fieldName) {
        if(validator == 'valid')
            assert ouv.errors["${fieldName}"] == null
        else    
            assert ouv.errors["${fieldName}"] == validator
    }

}
