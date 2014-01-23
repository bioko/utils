package it.bioko.utils.validator.type;

import it.bioko.utils.validator.TypeValidator;

import java.io.InputStream;

public class InputStreamValidator implements TypeValidator {

	@Override
	public boolean isValid(Object value) {
		return value instanceof InputStream;
	}

	@Override
	public void setPattern(String pattern) {
	}

}
