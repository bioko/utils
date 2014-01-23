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

import org.biokoframework.utils.domain.ErrorEntity;
import org.biokoframework.utils.fields.Fields;

public class ValidatorErrorBuilder {
	
	private static final String ERROR_CODE_MANDATORY = "51";
	private static final String ERROR_CODE_TYPE = "52";
	private static final String ERROR_CODE_TYPE_DONT_KNOW = "53";
	private static final String ERROR_CODE_FORMAT = "54";
	private static final String ERROR_CODE_PATERN = "55";	
	private static final String ERROR_CODE_UNIQUE_VIOLATION = "56";
	private static final String ERROR_CODE_READ_ONLY_VIOLATION = "57";
	
	private static final String ERROR_MSG_MANDATORY = "Missing field ";
	private static final String ERROR_MSG_TYPE = "Wrong type for field ";
	private static final String ERROR_MSG_TYPE_DONT_KNOW = "Don't know how to validate type for field ";
	private static final String ERROR_MSG_FORMAT = "Wrong format for field ";
	private static final String ERROR_MSG_PATTERN = "Wrong pattern for field ";	
	private static final String ERROR_MSG_UNIQUE_VIOLATION = "Field must be unique";	
	private static final String ERROR_MSG_READ_ONLY_VIOLATION = "Field is read only";	
	
	

	public static ErrorEntity buildFieldFormatNotValidError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_FORMAT, ERROR_MSG_FORMAT);
	}
	
	
	public static ErrorEntity createErrorEntity(String fieldName, String errorCode, String errorMsg) {
		Fields input = Fields.empty();
		input.put(ErrorEntity.ERROR_FIELD, fieldName);
		input.put(ErrorEntity.ERROR_CODE, errorCode);
		input.put(ErrorEntity.ERROR_MESSAGE, errorMsg);
		ErrorEntity e = new ErrorEntity(input);
				
		return e;
	}
	
	public static ErrorEntity createErrorEntity(String errorCode, String errorMsg) {
		Fields input = Fields.empty();		
		input.put(ErrorEntity.ERROR_CODE, errorCode);
		input.put(ErrorEntity.ERROR_MESSAGE, errorMsg);
		ErrorEntity e = new ErrorEntity(input);
				
		return e;
	}


	public static ErrorEntity buildWrongPatternError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_PATERN, ERROR_MSG_PATTERN);
	}


	public static ErrorEntity buildTypeDontKnowError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_TYPE_DONT_KNOW, ERROR_MSG_TYPE_DONT_KNOW);
	}


	public static ErrorEntity buildWrongTypeError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_TYPE, ERROR_MSG_TYPE);
	}


	public static ErrorEntity buildMandatoryFieldsMissingError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_MANDATORY, ERROR_MSG_MANDATORY);
	}


	public static ErrorEntity buildUniqueViolationError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_UNIQUE_VIOLATION, ERROR_MSG_UNIQUE_VIOLATION);
	}


	public static ErrorEntity buildReadOnlyViolationError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_READ_ONLY_VIOLATION, ERROR_MSG_READ_ONLY_VIOLATION);
	}

}
