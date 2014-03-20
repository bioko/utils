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

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.apache.log4j.Logger;
import org.biokoframework.utils.domain.DomainEntity;
import org.biokoframework.utils.domain.annotation.field.Field;
import org.biokoframework.utils.domain.annotation.hint.Hint;
import org.biokoframework.utils.domain.reflection.DummyParameterizedType;
import org.biokoframework.utils.domain.validation.IEntityValidator;
import org.biokoframework.utils.domain.validation.IEntityValidatorBuilder;
import org.biokoframework.utils.domain.validation.impl.EntityValidatorImpl;
import org.biokoframework.utils.validation.ITypeValidator;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Mar 4, 2014
 *
 */
public class InjectedValidatorBuilder implements IEntityValidatorBuilder {

	/**
	 * If this hint is present in the field the {@link InjectedValidatorBuilder}
	 * will look for a {@link org.biokoframework.utils.validation.ITypeValidator}
	 * annotated with <code>{@link javax.inject.Named}</code> having the same value.
	 * E.g.<br>
	 * <code>
	 * {@literal @}Field(hints={
	 * 		{@literal @}Hint(name="validationSubtype", value = "email")
	 * })</code><br>
	 * will be checked using with a validator <code>@Named("email")</code>
	 * 
	 */
	public static final String VALIDATION_SUBTYPE = "validationSubtype";

	private static final Logger LOGGER = Logger.getLogger(InjectedValidatorBuilder.class); 
	
	private final Injector fInjector;

	@Inject
	public InjectedValidatorBuilder(Injector injector) {
		fInjector = injector;
	}
	
	@Override
	public IEntityValidator createEntityValidator(Class<? extends DomainEntity> entityClass) {
		
		Map<String, Field> fields = null;
		try {
			fields = extractFields(entityClass);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOGGER.fatal("[easy-men]: something is wrong in your entity", e);
			return null;
		}
		
		Map<String, ITypeValidator<?>> validators = new LinkedHashMap<>();
		for (Entry<String, Field> entry : fields.entrySet()) {
			validators.put(entry.getKey(), createTypeValidator(entry.getValue()));
		}
		
		return new EntityValidatorImpl(validators);
	}

	private Map<String, Field> extractFields(Class<? extends DomainEntity> entityClass) throws IllegalArgumentException, IllegalAccessException {
		Map<String, Field> fields = new LinkedHashMap<>();
		for (java.lang.reflect.Field field : entityClass.getFields()) {
			Field annotation = field.getAnnotation(Field.class);
			if (annotation != null) {
				fields.put((String) field.get(null), annotation);
			}
		}
		return fields;
	}
	
	private ITypeValidator<?> createTypeValidator(Field value) {
		String subtype = "";
		for (Hint anHint : value.hints()) {
			if (anHint.name().equals(VALIDATION_SUBTYPE)) {
				subtype = anHint.value();
			}
		}
		ITypeValidator<?> validator;
		if (DomainEntity.class.isAssignableFrom(value.type())) {
			validator = (ITypeValidator<?>) fInjector.getInstance(Key.get(new DummyParameterizedType(ITypeValidator.class, String.class)));
		} else if (subtype.isEmpty()) {
			validator = (ITypeValidator<?>) fInjector.getInstance(Key.get(new DummyParameterizedType(ITypeValidator.class, value.type())));			
		} else { 
			validator = (ITypeValidator<?>) fInjector.getInstance(Key.get(new DummyParameterizedType(ITypeValidator.class, value.type()), Names.named(subtype)));
		}
		
		validator.setMandatory(value.mandatory());
		validator.addHints(value.hints());
		return validator;
	}

}
