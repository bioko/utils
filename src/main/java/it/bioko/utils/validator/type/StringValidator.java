package it.bioko.utils.validator.type;


public class StringValidator extends StringBasedTypeValidator {

	@Override
	public boolean isValidString(String value) {
		return true;	// always for string
	}

	@Override
	public void setPattern(String pattern) {
	}

}
