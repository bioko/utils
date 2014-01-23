package it.bioko.utils.validator.type;


public class IntegerValidator extends StringBasedTypeValidator {
	
	private org.apache.commons.validator.routines.IntegerValidator _validaor;

	public IntegerValidator() {
		_validaor = new org.apache.commons.validator.routines.IntegerValidator();
	}

	@Override
	public boolean isValidString(String value) {		
		return _validaor.isValid(value);
	}

	@Override
	public void setPattern(String pattern) {
	}

}
