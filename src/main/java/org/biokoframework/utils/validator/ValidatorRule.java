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

package org.biokoframework.utils.validator;

public class ValidatorRule {

	private Class<?> 	_type;
	private boolean _mandatory;
	private String 	_pattern;
	private String 	_format;
	private String _dateFormat;
	

	public ValidatorRule(Class<?> type, boolean mandatory, String pattern, String format) {
		_type = type;
		_mandatory = mandatory;
		_pattern = pattern;
		_format = format;
	}


	public Class<?> getType() {
		return _type;
	}


	public void setType(Class<?> type) {
		_type = type;
	}


	public boolean isMandatory() {
		return _mandatory;
	}


	public void setMandatory(boolean mandatory) {
		_mandatory = mandatory;
	}


	public String getPattern() {
		return _pattern;
	}


	public void setPattern(String pattern) {
		_pattern = pattern;
	}


	public String getFormat() {
		return _format;
	}


	public void setFormat(String format) {
		_format = format;
	}


	public String getDateFormat() {
		return _dateFormat;
	}


	public void setDateFormat(String dateFormat) {
		_dateFormat = dateFormat;
	}
	
	
	
	
	
	
}
