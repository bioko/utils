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

package org.biokoframework.utils.domain.annotation.hint;

import java.util.LinkedHashMap;
import java.util.Map;

import org.biokoframework.utils.domain.DomainEntity;
import org.biokoframework.utils.domain.annotation.field.Field;

public class HintFactory {

	public static Map<String, String> createMap(Class<? extends DomainEntity> annotatedDomainEntity, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Map<String, String> hintsMap = null;
		
		for (java.lang.reflect.Field aField : annotatedDomainEntity.getFields()) {
			if (aField.get(null).equals(fieldName)) {
				hintsMap = extractHints(aField.getAnnotation(Field.class));
			}
		}
		
		return hintsMap;
	}
	
	public static Map<String, Map<String, String>> createMap(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		Map<String, Map<String, String>> entityHints = new LinkedHashMap<String, Map<String, String>>();
		
		for (java.lang.reflect.Field aField : annotatedDomainEntity.getFields()) {
			Field annotation = aField.getAnnotation(Field.class);
			if (annotation != null) {
				String fieldName = (String) aField.get(null);
				entityHints.put(fieldName, extractHints(annotation));
			}
		}
		
		return entityHints;
	}

	private static Map<String, String> extractHints(Field annotation) {
		LinkedHashMap<String, String> hintsMap = new LinkedHashMap<String, String>();

		for (Hint aHint : annotation.hints()) {
			hintsMap.put(aHint.name(), aHint.value());
		}
		
		return hintsMap;
	}
	
}
