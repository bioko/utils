package it.bioko.utils.validator.type;


public class CalendarValidator extends StringBasedTypeValidator {
	
	private org.apache.commons.validator.routines.CalendarValidator _validator;
	String _pattern = null;
	
	public CalendarValidator() {
		_validator = new org.apache.commons.validator.routines.CalendarValidator();
	}

	@Override
	public boolean isValidString(String value) {
		boolean valid;
		if (_pattern == null)
			valid = _validator.isValid(value);
		else
			valid = _validator.isValid(value, _pattern);
			
		return valid;
	}

	@Override
	public void setPattern(String pattern) {
		_pattern = pattern;		
	}

}
