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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.biokoframework.utils.domain.annotation.field.EntityValidatorRulesFactory;
import org.biokoframework.utils.fields.Fields;
import org.biokoframework.utils.validator.Validator;
import org.junit.Test;

public class AnnotatedDomainEntityTest {

	private static final String AN_AGE = "27";	
	private static final String A_WRONG_AGE = "27 years";
	private static final String A_SURNAME = "aSurname";
	private static final String A_NAME = "aName";
//	private static final String AN_AGE_28 = "28";
	private static final String CORRECT_EMAIL = "correct@email.net";
	private static final String WRONG_EMAIL = "wrong.email";
	private static final String A_CORRECT_BIRTH_DATE = "29/07/2013";
//	private static final String WRONG_BIRTH_DATE = "2013/07/29";

	@Test
	public void instantiateVoidEntity() {		
		AnnotatedPersonExample de = new AnnotatedPersonExample();
		Fields deFields = de.fields();
		assertTrue(deFields.isEmpty());
	}
	
	
	@Test
	public void instantiateFullEntity() {
		Fields input = composeFiels();
		AnnotatedPersonExample de = new AnnotatedPersonExample(input);
		
		assertThat(de.get(AnnotatedPersonExample.NAME), is(equalTo((Object) A_NAME)));
		assertThat(de.get(AnnotatedPersonExample.SURNAME), is(equalTo((Object) A_SURNAME)));
		assertThat(de.get(AnnotatedPersonExample.AGE), is(equalTo((Object) AN_AGE)));
		
	}


	
	
	@Test
	public void testValidatorWithSuccess() throws IllegalArgumentException, IllegalAccessException {
		Fields input = composeFiels();
		AnnotatedPersonExample de = new AnnotatedPersonExample(input);
		
		Validator myValidator = new Validator(EntityValidatorRulesFactory.create(AnnotatedPersonExample.class));
		assertThat(myValidator.validate(de), equalTo(true));
		assertThat(de.isValid(), equalTo(true));
		
	}
	
	
	
	
	
	@Test
	public void testIntegerValidatorWithFail() throws IllegalArgumentException, IllegalAccessException {
		Fields input = composeFiels();
		input.put(AnnotatedPersonExample.AGE, A_WRONG_AGE);
		AnnotatedPersonExample de = new AnnotatedPersonExample(input);
		
		
		Validator myValidator = new Validator(EntityValidatorRulesFactory.create(AnnotatedPersonExample.class));
		boolean validatorResult = myValidator.validate(de); 
		
		assertThat(validatorResult, equalTo(false));
	}

	
	@Test
	public void testEmailValidatorWithFail() throws IllegalArgumentException, IllegalAccessException {
		Fields input = composeFiels();
		input.put(AnnotatedPersonExample.EMAIL, WRONG_EMAIL);
		AnnotatedPersonExample de = new AnnotatedPersonExample(input);

		Validator myValidator = new Validator(EntityValidatorRulesFactory.create(AnnotatedPersonExample.class));		
		assertThat(myValidator.validate(de), equalTo(false));
	}
	
	
	@Test
	public void testCalendarValidatorWithSuccess() throws IllegalArgumentException, IllegalAccessException {
		Fields input = composeFiels();
		input.put(AnnotatedPersonExample.BIRTH_DATE, A_CORRECT_BIRTH_DATE);
		AnnotatedPersonExample de = new AnnotatedPersonExample(input);
		
		
		Validator myValidator = new Validator(EntityValidatorRulesFactory.create(AnnotatedPersonExample.class));
		boolean validatorResult = myValidator.validate(de); 
		printErrorArray(myValidator.getErrors());
		
		assertThat(validatorResult, equalTo(true));
	}
	
//
//	
//	
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
//	
	
	
	
	private Fields composeFiels() {
		Fields input = new Fields();
		
		input.put(AnnotatedPersonExample.NAME, A_NAME);
		input.put(AnnotatedPersonExample.SURNAME, A_SURNAME);
		input.put(AnnotatedPersonExample.AGE, AN_AGE);
		input.put(AnnotatedPersonExample.EMAIL, CORRECT_EMAIL);
//		input.put(AnnotatedDomainEntityIntance.BIRTH_DATE, CORRECT_BIRTH_DATE);
		return input;
	}
	
	
	private void printErrorArray(List<ErrorEntity> errors) {
		for(int i=0; i<errors.size(); i++) {
			System.out.println(errors.get(i).toJSONString());
		}
	}
	
}
