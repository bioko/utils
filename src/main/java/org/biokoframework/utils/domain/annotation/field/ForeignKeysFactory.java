package org.biokoframework.utils.domain.annotation.field;

import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.biokoframework.utils.domain.DomainEntity;

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

public class ForeignKeysFactory {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Entry<String, Class<? extends DomainEntity>>> create(
			Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		
		ArrayList<Entry<String, Class<? extends DomainEntity>>> foreignKeys = new ArrayList<Entry<String,Class<? extends DomainEntity>>>();
		
		Field[] fields =  annotatedDomainEntity.getFields();
		
		for (Field aField : fields) {
			String fieldName = aField.get(null).toString();
			org.biokoframework.utils.domain.annotation.field.Field annotation = aField.getAnnotation(org.biokoframework.utils.domain.annotation.field.Field.class);
			
			if (annotation != null && !aField.get(null).equals(DomainEntity.ID)) {
				if (DomainEntity.class.isAssignableFrom(annotation.type())) {
					foreignKeys.add(new SimpleEntry<String, Class<? extends DomainEntity>>(
							fieldName, (Class<DomainEntity>) annotation.type()));
				}
			}
		}

		
		return foreignKeys;
	}

	public static boolean hasForeignKeys(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields =  annotatedDomainEntity.getFields();
		
		for (Field aField : fields) {
			org.biokoframework.utils.domain.annotation.field.Field annotation = aField.getAnnotation(org.biokoframework.utils.domain.annotation.field.Field.class);
			
			if (annotation != null && !aField.get(null).equals(DomainEntity.ID)) {
				if (DomainEntity.class.isAssignableFrom(annotation.type())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
