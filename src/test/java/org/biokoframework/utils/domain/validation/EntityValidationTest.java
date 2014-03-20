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

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.biokoframework.utils.domain.AnnotatedPersonExample;
import org.biokoframework.utils.fields.Fields;
import org.biokoframework.utils.validation.ValidationModule;
import org.junit.Before;
import org.junit.Test;

import static org.biokoframework.utils.matcher.Matchers.empty;
import static org.biokoframework.utils.matcher.Matchers.valid;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EntityValidationTest {

	private static final Long AN_AGE = 27L;	
	private static final String A_WRONG_AGE = "27 years";
	private static final String A_SURNAME = "aSurname";
	private static final String A_NAME = "aName";
//	private static final String AN_AGE_28 = "28";
	private static final String CORRECT_EMAIL = "correct@email.net";
	private static final String WRONG_EMAIL = "wrong.email";
	private static final String A_CORRECT_BIRTH_DATE = "29/07/2013";
//	private static final String WRONG_BIRTH_DATE = "2013/07/29";
	
	private IEntityValidatorBuilder fBuilder;
	private IEntityValidator fValidator;
	
	private AnnotatedPersonExample fEntity;

	@Before
	public void prepareValidator() {
		Injector injector = Guice.createInjector(new ValidationModule());
		fBuilder = injector.getInstance(IEntityValidatorBuilder.class);
		fValidator = fBuilder.createEntityValidator(AnnotatedPersonExample.class);
		
		fEntity = new AnnotatedPersonExample();
		fEntity.setAll(composeFiels());
	}
	
	@Test
	public void instantiateVoidEntity() {		
		AnnotatedPersonExample de = new AnnotatedPersonExample();
		Fields deFields = de.fields();
		assertThat(deFields, is(empty()));
	}
	
	@Test
	public void instantiateFullEntity() {
		
		assertThat(fEntity.get(AnnotatedPersonExample.NAME), is(equalTo((Object) A_NAME)));
		assertThat(fEntity.get(AnnotatedPersonExample.SURNAME), is(equalTo((Object) A_SURNAME)));
		assertThat(fEntity.get(AnnotatedPersonExample.AGE), is(equalTo((Object) AN_AGE)));
		
	}
	
	@Test
	public void testValidatorWithSuccess() throws IllegalArgumentException, IllegalAccessException {
		
		assertThat(fValidator.isValid(fEntity), is(true));
		fEntity.enableValidation(fBuilder);
		assertThat(fEntity, is(valid()));
	}
	
	@Test
	public void testIntegerValidatorWithFail() throws IllegalArgumentException, IllegalAccessException {
		fEntity.set(AnnotatedPersonExample.AGE, A_WRONG_AGE);
		
		assertThat(fValidator.isValid(fEntity), is(false));
	}
	
	@Test
	public void testEmailValidatorWithFail() throws IllegalArgumentException, IllegalAccessException {
		fEntity.set(AnnotatedPersonExample.EMAIL, WRONG_EMAIL);
		
		assertThat(fValidator.isValid(fEntity), equalTo(false));
	}
	
	@Test
	public void testCalendarValidatorWithSuccess() throws IllegalArgumentException, IllegalAccessException {
		fEntity.set(AnnotatedPersonExample.BIRTH_DATE, A_CORRECT_BIRTH_DATE);

		assertThat(fValidator.isValid(fEntity), is(true));
	}

//	@Test
//	public void testWithCustomValidator() {
//		Fields input = composeFiels();		
//		AnnotatedDomainEntityIntance de27ers = new AnnotatedDomainEntityIntance(input);
//		input.put(AnnotatedDomainEntityIntance.AGE, AN_AGE_28);
//		AnnotatedDomainEntityIntance de28ers = new AnnotatedDomainEntityIntance(input);
//		
//		// with standard validator
//		assertThat(ValidatorUtil.validate(de27ers), equalTo(true));
//		assertThat(ValidatorUtil.validate(de28ers), equalTo(true));
//		
//		ValidatorUtil validatorUtil = new ValidatorUtil();
//		validatorUtil.addCustomValidator(AnnotatedDomainEntityIntance.class.getSimpleName(), new Custom28ersValidator());
//		
//		// with the custom validator
//		assertThat(validatorUtil._validate(de27ers), equalTo(false));
//		assertThat(validatorUtil._validate(de28ers), equalTo(true));
//	}
	
	private Fields composeFiels() {
		Fields input = new Fields(
				AnnotatedPersonExample.NAME, A_NAME,
				AnnotatedPersonExample.SURNAME, A_SURNAME,
				AnnotatedPersonExample.AGE, AN_AGE,
				AnnotatedPersonExample.EMAIL, CORRECT_EMAIL);
//		input.put(AnnotatedDomainEntityIntance.BIRTH_DATE, CORRECT_BIRTH_DATE);
		return input;
	}
	
}
