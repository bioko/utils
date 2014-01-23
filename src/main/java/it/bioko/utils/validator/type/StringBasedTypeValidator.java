package it.bioko.utils.validator.type;

import it.bioko.utils.validator.TypeValidator;

public abstract class StringBasedTypeValidator implements TypeValidator {

	@Override
	public boolean isValid(Object value) {
		if (value instanceof String) {
			return isValidString((String)value);
		}
		return false;
	}

	protected abstract boolean isValidString(String value);

}
