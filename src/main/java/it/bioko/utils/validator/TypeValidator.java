package it.bioko.utils.validator;

public interface TypeValidator {

	boolean isValid(Object value);
	void setPattern(String pattern);
	
}
