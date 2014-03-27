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

package org.biokoframework.utils.fields;

import org.biokoframework.utils.domain.ErrorEntity;

public interface FieldNames {
	
	// TODO pulire pulire pulire!!!
    public static final String COMMAND_NAME = "commandName";
    public static final String PRINTED_COMMAND_NAME = "printedCommandName";
    public static final String COMMAND_OUTCOME = "commandOutcome";
    public static final String REQUEST_OUTCOME = "requestOutcome";
    public static final String REASON = "errorReason";
	public static final String FAILED_COMMAND = "failedCommand";
	public static final String ERROR_COMMAND = "errorCommand";    
	public static final String COMMAND_INVOCATION_INPUT_INFO = "commandInvocationInputInfo";
	public static final String COMMAND_INVOCATION_OUTPUT_INFO = "commandInvocationOutputInfo";
	public static final String COMMAND_INFORMATION_ON = "commandInformationOn";
	
	public static final String FIELD_NAME = "fieldName";
	
	public static final String NAME = "name";
	public static final String NAME_VALUE = "Michelangelo";
	public static final String SURNAME = "surname";
	public static final String SURNAME_VALUE = "Buonarroti";
	public static final String EMAIL = "email";
	public static final String EMAIL_VALUE = "michelangelo.buonarroti@firenze.it";
	public static final String AREA = "area";
	public static final String AREA_VALUE = "firenze";
	public static final String ID = "id";
	public static final String ID_VALUE = "999";
	
	@Deprecated
	public static final String ERROR_MESSAGE = ErrorEntity.ERROR_MESSAGE;
	@Deprecated
	public static final String ERROR_CODE = ErrorEntity.ERROR_CODE;
	
	public static final long NO_ERROR_CODE = 0;

	public static final long BAD_COMMAND_INVOCATION_CODE = 100;
	public static final long  COMMAND_NOT_FOUND_CODE = 101;
	
	public static final long ENTITY_WITH_ID_NOT_FOUND_CODE = 102;
	public static final long ENTITY_WITH_FIELD_NOT_FOUND_CODE = 103;
	public static final long EXPECTED_FIELD_NOT_FOUND = 104;
	public static final long ENTITY_NOT_COMPLETE_CODE = 105;
	public static final long ENTITY_ALREADY_EXISTING_CODE = 106;
	public static final long REPOSITORY_IS_EMPTY_CODE = 107;
	public static final long REPOSITORY_MISSING_CODE = 108;
    public static final long ENTITY_FIELD_NOT_UNIQUE = 109;
	
	public static final long AUTHENTICATION_REQUIRED_CODE = 110;
	public static final long INVALID_LOGIN_CODE = 111;
	public static final long TOKEN_EXPIRED_CODE = 112;
	public static final long INSUFFICIENT_PRIVILEGES_CODE = 113;
	public static final long FACEBOOK_AUTH_FAILURE_CODE = 114;
	
	public static final long DISSOLUTION_INCOMPLETE_CODE = 120;

	public static final long CONTAINER_EXCEPTION_CODE = 190;
	
	public static final long UNSUPPORTED_FORMAT_CODE = 130;
	
	
}
