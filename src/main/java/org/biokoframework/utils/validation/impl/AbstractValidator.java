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

import org.biokoframework.utils.domain.ErrorEntity;
import org.biokoframework.utils.domain.annotation.hint.Hint;
import org.biokoframework.utils.validation.ITypeValidator;
import org.biokoframework.utils.validation.ValidationErrorBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Mar 4, 2014
 *
 * @param <T> The type to be validated 
 */
public abstract class AbstractValidator<T> implements ITypeValidator<T> {
	
	private List<ErrorEntity> fErrors = new ArrayList<>();
	private boolean fMandatory;
	
	@Override
	public boolean isValid(String name, Object value) {
		if (value == null) {
			if (fMandatory) {
				fErrors.add(ValidationErrorBuilder.buildMandatoryFieldsMissingError(name));
				return false;
			} else {
				return true;
			}
		} else {
			return checkValid(name, value);
		}
	}
	
	protected abstract boolean checkValid(String name, Object value);

	@Override
	public final void addHints(Hint[] hints) {
		Map<String, String> hintsMap = new LinkedHashMap<>();
		for (Hint anHint : hints) {
			hintsMap.put(anHint.name(), anHint.value());
		}
		addHints(hintsMap);
	}
	
	protected abstract void addHints(Map<String, String> hints);

	@Override
	public final void setMandatory(boolean mandatory) {
		fMandatory = mandatory;
	}

	@Override
	public List<ErrorEntity> getErrors() {
		return fErrors;
	}
	
	protected void addError(ErrorEntity error) {
		fErrors.add(error);
	}
}
