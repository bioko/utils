package it.bioko.utils.validator.type;

import it.bioko.utils.fields.FieldValues;

public class BooleanValidator extends StringBasedTypeValidator {

	@Override
	protected boolean isValidString(String value) {
		return FieldValues.TRUE.equals(value) || FieldValues.FALSE.equals(value);
	}

	@Override
	public void setPattern(String pattern) {
	}
	
}
