package it.bioko.utils.validator.type;


public class LongValidator extends StringBasedTypeValidator {

	private org.apache.commons.validator.routines.LongValidator _validator;

	public LongValidator() {
		_validator = new org.apache.commons.validator.routines.LongValidator();
	}

	@Override
	public boolean isValidString(String value) {		
		return _validator.isValid(value);
	}

	@Override
	public void setPattern(String pattern) {
	}

}
