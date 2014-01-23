package it.bioko.utils.validator;

import org.apache.commons.validator.routines.RegexValidator;

public class PatternValidator {
	
	RegexValidator _validator;
	
	public PatternValidator(String pattern) {
		_validator = new RegexValidator(pattern);
	}

	public boolean isValid(String value) {
		return _validator.isValid(value);
	}
	
	

	
	
	

}
