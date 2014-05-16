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

package org.biokoframework.utils.exception;

import org.biokoframework.utils.domain.ErrorEntity;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Jan 26, 2014
 *
 */
public class BiokoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private Exception fException;
	
	private List<ErrorEntity> fErrors = new ArrayList<ErrorEntity>();
	
	public BiokoException(ErrorEntity error) {
		super(error.toJSONString());
		fErrors.add(error);
	}
	
	public BiokoException(List<ErrorEntity> errors) {
		fErrors = new ArrayList<ErrorEntity>(errors);
	}
	
	public BiokoException(Exception exception) {
		super();
		fException = exception;
	}

	public BiokoException(ErrorEntity error, Exception exception) {
		super(exception);
		fErrors.add(error);
		fException = exception;
	}
	
	public BiokoException(List<ErrorEntity> errors, Exception exception) {
		super(exception);
		fErrors = new ArrayList<ErrorEntity>(errors);		
		fException = exception;
	}
	
	public List<ErrorEntity> getErrors() {
		return Collections.unmodifiableList(fErrors);
	}
	
	public Exception exception() {
		return fException;
	}
	
	public Throwable getCause() {
		return exception();
	}

    @Override
    public String getMessage() {
        if (fErrors != null) {
            return JSONValue.toJSONString(fErrors);
        } else {
            return super.getMessage();
        }
    }
}
