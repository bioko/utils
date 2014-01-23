package it.bioko.utils.validator.format;

import it.bioko.utils.validator.FormatValidator;

public class EmailValidator implements FormatValidator {

	@Override
	public boolean isValid(String value) {
		return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(value);
	}

}
