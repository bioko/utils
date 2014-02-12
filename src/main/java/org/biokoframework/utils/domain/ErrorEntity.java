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

package org.biokoframework.utils.domain;

import java.util.ArrayList;

import org.biokoframework.utils.domain.annotation.field.Field;
import org.biokoframework.utils.fields.Fields;

public class ErrorEntity extends DomainEntity {

	private static final long serialVersionUID = -105134104794440349L;
	
	@Field
	public static final String ERROR_MESSAGE = "errorMessage";
	@Field(type=Integer.class)
	public static final String ERROR_CODE = "errorCode";
	@Field(mandatory=false)
	public static final String ERROR_FIELD = "errorField";
	@Field(mandatory=false)
	public static final String ERROR_DESCRIPTION = "errorDescription";
	
	public ErrorEntity(Fields input) {
		//super(input, componingKeys());
		super(input);
	}
	
	public ErrorEntity(Fields input, ArrayList<String> componingFieldKeys) {
		super(input);
	}
	
}
