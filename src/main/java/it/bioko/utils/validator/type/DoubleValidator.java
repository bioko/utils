package it.bioko.utils.validator.type;

public class DoubleValidator extends StringBasedTypeValidator {

	private org.apache.commons.validator.routines.DoubleValidator _validator = 
			new org.apache.commons.validator.routines.DoubleValidator();
	
	@Override
	public void setPattern(String pattern) {
	}

	@Override
	protected boolean isValidString(String value) {
		return _validator.isValid(value);
	}

}
