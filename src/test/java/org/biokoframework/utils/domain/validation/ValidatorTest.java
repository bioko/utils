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

package org.biokoframework.utils.domain.validation;

import static org.biokoframework.utils.matcher.Matchers.valid;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.biokoframework.utils.domain.DomainEntity;
import org.biokoframework.utils.domain.ErrorEntity;
import org.biokoframework.utils.domain.annotation.field.Field;
import org.biokoframework.utils.domain.annotation.hint.Hint;
import org.biokoframework.utils.fields.Fields;
import org.biokoframework.utils.validation.ValidationErrorBuilder;
import org.biokoframework.utils.validation.ValidationModule;
import org.biokoframework.utils.validation.impl.InjectedValidatorBuilder;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * 
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Mar 4, 2014
 *
 */
public class ValidatorTest {

	private InjectedValidatorBuilder fBuilder;
	private IEntityValidator fValidator;
	
	@Before
	public void createFactory() {
		Injector injector = Guice.createInjector(new ValidationModule());
		fBuilder = new InjectedValidatorBuilder(injector);
		fValidator = fBuilder.createEntityValidator(ValidatedEntity.class);
	}
	
	@Test
	public void testSimpleFieldsValidatorWithSuccess() {
		
		ValidatedEntity entity = new ValidatedEntity(new Fields(
				"name", "gino pino",
				"email", "gino@pino.net",
				"age", 28L));
		
		assertThat(fValidator.isValid(entity), is(true));
		
		entity.enableValidation(fBuilder);
		assertThat(entity, is(valid()));
	}
	
	
	@Test
	public void testSimpleFieldsValidatorWithFailForMandatory() {
		
		ValidatedEntity entity = new ValidatedEntity(new Fields(
				"email", "gino@pino.net",
				"age", 28L));
		
		fValidator = fBuilder.createEntityValidator(ValidatedEntity.class);
				
		assertThat(fValidator.isValid(entity), is(false));
		
		ErrorEntity error = ValidationErrorBuilder.buildMandatoryFieldsMissingError("name");
		
		assertThat(fValidator.getErrors(), contains(error));

	}
	
	@Test
	public void testSimpleFieldsValidatorWithFailForType() {
		
		ValidatedEntity entity = new ValidatedEntity(new Fields(
				"name", "gino pino",
				"email", "gino@pino.net",
				"age", "28"));

		
		assertThat(fValidator.isValid(entity), is(false));
		
		ErrorEntity error = ValidationErrorBuilder.buildWrongTypeError("age");
		assertThat(fValidator.getErrors(), contains(error));
		
	}
	
	@Test
	public void testSimpleFieldsValidatorWithFailForSubtype() {
		
		ValidatedEntity entity = new ValidatedEntity(new Fields(
				"name", "gino pino",
				"email", "www.ginopino.net",
				"age", 28L));
		
		assertThat(fValidator.isValid(entity), is(false));

		ErrorEntity error = ValidationErrorBuilder.buildFieldFormatNotValidError("email");
		assertThat(fValidator.getErrors(), contains(error));	
		
	}
	
	@Test
	public void testSimpleFieldsValidatorWithFailForPattern() {
		
		ValidatedEntity entity = new ValidatedEntity(new Fields(
				"name", "gino",
				"email", "you@yourdomain.com",
				"age", 28L));
		
		assertThat(fValidator.isValid(entity), is(false));
		
		ErrorEntity error = ValidationErrorBuilder.buildWrongRegexpError("name");
		assertThat(fValidator.getErrors(), contains(error));
		
		// correct one
		entity.set(ValidatedEntity.NAME, "some pino");
		assertThat(fValidator.isValid(entity), is(true));
		
		
	}
//	
//	@Test
//	public void testSimpleFieldsValidatorForCalendarWithSuccess() {
//		
//		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
//		
//		ValidatorRule italianDateDesription = new ValidatorRule(LocalDate.class, false, null, null);
//		italianDateDesription.setDateFormat(Validator.ISO_DATE);
//		descriptors.put("italianDate", italianDateDesription);
//		
//		Validator myDummyEntityValidator = new Validator(descriptors);
//		
//		Fields dummyEntityFields = generateInputFields(); 		
//		dummyEntityFields.put("italianDate", "1974-10-19");
//		
//		printErrors(myDummyEntityValidator.getErrors());
//		
//		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(true));
//	}
//	
//	
//	@Test
//	public void testSimpleFieldsValidatorForCalendarWithFail() {
//		
//		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
//		
//		ValidatorRule italianDateDesription = new ValidatorRule(LocalDate.class, false, null, null);
//		italianDateDesription.setDateFormat(Validator.ISO_DATE);
//		descriptors.put("italianDate", italianDateDesription);
//		
//		Validator myDummyEntityValidator = new Validator(descriptors);
//		
//		Fields dummyEntityFields = generateInputFields(); 		
//		dummyEntityFields.put("italianDate", "1974-19-10");
//		
//		printErrors(myDummyEntityValidator.getErrors());
//		
//		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(false));
//		printErrors(myDummyEntityValidator.getErrors());
//		
//		ErrorEntity error = ValidatorErrorBuilder.buildWrongTypeError("italianDate");
//		assertThat(myDummyEntityValidator.getErrors(), contains(error));
//		
//		// correct one
//		dummyEntityFields.put("italianDate", "1974-10-19");
//		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(true));
//	}
//	
//	
//	@Test
//	public void testFieldNotValidableWillFail() {
//		
//		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
//		ValidatorRule ruleWithWrongType = new ValidatorRule(ArrayList.class, false, null, null);
//		descriptors.put("wrongTypedField", ruleWithWrongType);
//		
//		Validator myDummyEntityValidator = new Validator(descriptors);
//		
//		
//		Fields dummyEntityFields = generateInputFields(); 		
//		dummyEntityFields.put("wrongTypedField", "Nothing special to put there");
//		
//		
//		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(false));
//		
//		ErrorEntity error = ValidatorErrorBuilder.buildTypeDontKnowError("wrongTypedField");
//		assertThat(myDummyEntityValidator.getErrors(), contains(error));
//		
//	}
//	
//	
//	
//	private Map<String, ValidatorRule> createFieldDescriptors() {
//		Map<String, ValidatorRule> descriptors = new HashMap<String, ValidatorRule>();
//		
//		ValidatorRule nameDesription = new ValidatorRule(String.class, true, null, null);
//		ValidatorRule emailDesription = new ValidatorRule(String.class, false, null, Validator.FORMAT_EMAIL);
//		ValidatorRule ageDesription = new ValidatorRule(Long.class, false, null, null);
//		
//		descriptors.put("name", nameDesription);
//		descriptors.put("email", emailDesription);
//		descriptors.put("age", ageDesription);
//		
//		return descriptors;
//		
//	}

	public static final class ValidatedEntity extends DomainEntity {

		private static final long serialVersionUID = 6587026300710308613L;
		
		@Field(hints = {
			@Hint(name = "validationRegexp", value = ".* pino")
		})
		public static final String NAME = "name";
		
		@Field(hints = {
			@Hint(name = "validationSubtype", value = "email")
		})
		public static final String EMAIL = "email";
		
		@Field(type = Long.class)
		public static final String AGE = "age";
		
		public ValidatedEntity(Fields input) {
			super(input);
		}
		
	}

	public void printErrors(List<ErrorEntity> errors) {
		for (ErrorEntity e: errors)
			System.out.println(e.toJSONString());
	}

}
