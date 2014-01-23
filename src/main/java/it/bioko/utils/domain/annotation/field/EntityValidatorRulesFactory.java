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

package it.bioko.utils.domain.annotation.field;

import it.bioko.utils.domain.DomainEntity;
import it.bioko.utils.validator.ValidatorRule;
import it.bioko.utils.validator.ValidatorRuleFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityValidatorRulesFactory {

	public static Map<String, ValidatorRule> create(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		
		Map<String, ValidatorRule> rules = new HashMap<String, ValidatorRule>();
		
		Field[] fields =  annotatedDomainEntity.getFields();
		
		for (Field aField : fields) {
			String fieldName = aField.get(null).toString();
			it.bioko.utils.domain.annotation.field.Field annotation = aField.getAnnotation(it.bioko.utils.domain.annotation.field.Field.class);
			
			if (annotation != null) {
				ValidatorRule rule = ValidatorRuleFactory.fromAnnotation(fieldName, annotation);
				rules.put(fieldName, rule);
			}

		}
		
		return rules;
	}
	
	

}
