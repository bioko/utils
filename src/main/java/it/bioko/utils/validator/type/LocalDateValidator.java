package it.bioko.utils.validator.type;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

public class LocalDateValidator extends StringBasedTypeValidator {

	private String _pattern;
	
	@Override
	protected boolean isValidString(String value) {
		if (_pattern == null) {
			try {
				ISODateTimeFormat.date().parseDateTime(value);		
			} catch (Exception exception) {
				return false;
			}
		} else {
			try {
				DateTimeFormat.forPattern(_pattern).parseDateTime(value);
			} catch (Exception exception) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void setPattern(String pattern) {
		_pattern = pattern;
	}
}
