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

package org.biokoframework.utils.domain.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.biokoframework.utils.domain.DomainEntity;
import org.biokoframework.utils.domain.ErrorEntity;
import org.biokoframework.utils.domain.validation.IEntityValidator;
import org.biokoframework.utils.validation.ITypeValidator;

/**
 * 
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Mar 4, 2014
 *
 */
public class EntityValidatorImpl implements IEntityValidator {

	private final Map<String, ITypeValidator<?>> fValidationMap;
	private List<ErrorEntity> fErrors;

	public EntityValidatorImpl(Map<String, ITypeValidator<?>> validationMap) {
		fValidationMap = validationMap;
	}
	
	@Override
	public boolean isValid(DomainEntity entity) {
		boolean valid = true;
		fErrors = new ArrayList<>();
		for (Entry<String, ITypeValidator<?>> entry : fValidationMap.entrySet()) {
			String fieldName = entry.getKey();
			ITypeValidator<?> typeValidator = entry.getValue();
			if (!typeValidator.isValid(fieldName, entity.get(fieldName))) {
				valid = false;
				fErrors.addAll(typeValidator.getErrors());
			}
		}
		return valid;
	}

	@Override
	public List<ErrorEntity> getErrors() {
		return fErrors;
	}
	
}