package org.biokoframework.utils.validation.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.biokoframework.utils.validation.ValidationErrorBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DateTimeValidator extends AbstractValidator<DateTime> {

	public static final String VALIDATION_DATETIME_PATTERN = "validationDatetimeFormat";
	private DateTimeFormatter fFormatter = ISODateTimeFormat.dateTimeNoMillis();
	
	@Override
	protected boolean checkValid(String name, Object value) {
		if (!(value instanceof String)) {
			addError(ValidationErrorBuilder.buildWrongTypeError(name));
			return false;
		}
		String string = (String) value;
		
		try {
			fFormatter.parseDateTime(string);
		} catch (Exception exception) {
			addError(ValidationErrorBuilder.buildWrongTypeError(name));
			return false;
		}

		return true;
	}

	@Override
	protected void addHints(Map<String, String> hints) {
		String pattern = hints.get(VALIDATION_DATETIME_PATTERN); 
		if (!StringUtils.isEmpty(pattern)) {
			fFormatter = DateTimeFormat.forPattern(pattern);
		}
	}

}
