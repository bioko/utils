/*
 * Copyright (c) 2014																 
 *	Mikol Faro			<mikol.faro@gmail.com>
 *	Simone Mangano		<simone.mangano@ieee.org>
 *	Mattia Tortorelli	<mattia.tortorelli@gmail.com>
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

package org.biokoframework.utils.validation.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.biokoframework.utils.validation.ValidationErrorBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Mar 4, 2014
 *
 */
public class LocalDateValidator extends AbstractValidator<LocalDate> {

	public static final String VALIDATION_LOCALDATE_PATTERN = "validationLocaldateFormat";
	private String fPattern;
	private DateTimeFormatter fFormatter = ISODateTimeFormat.date();

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
			addError(ValidationErrorBuilder.buildWrongRegexpError(name));
			return false;
		}

		return true;
	}

	@Override
	protected void addHints(Map<String, String> hints) {
		String pattern = hints.get(VALIDATION_LOCALDATE_PATTERN); 
		if (!StringUtils.isEmpty(pattern)) {
			fFormatter = DateTimeFormat.forPattern(pattern);
		}

	}

}
