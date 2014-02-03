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

import static org.biokoframework.utils.matcher.Matchers.matchesJSONString;
import static org.junit.Assert.assertThat;

import org.biokoframework.utils.fields.Fields;
import org.junit.Test;


public class EntityBuilderTest {
	
	@Test
	public void buildNotExistingEntityTest() {
		MyTestEntityBuilder myBuilder = new MyTestEntityBuilder();
		
		AnnotatedPersonExample withBuilder = myBuilder.loadExample(MyTestEntityBuilder.EXAMPLE1).build(false);
		AnnotatedPersonExample withoutBuilder =  createTestEntity();
						
		assertThat(withBuilder.toJSONString(), matchesJSONString(withoutBuilder.toJSONString()));
	}
	
	@Test
	public void buildExistingEntityTest() {
		MyTestEntityBuilder myBuilder = new MyTestEntityBuilder();
		
		AnnotatedPersonExample withBuilder = myBuilder.loadExample(MyTestEntityBuilder.EXAMPLE1).build(true);
		AnnotatedPersonExample withoutBuilder =  createTestEntity();
		withoutBuilder.set(DomainEntity.ID, "1");
						
		assertThat(withBuilder.toJSONString(), matchesJSONString(withoutBuilder.toJSONString()));
	}
	
	
	@Test
	public void buildCustomIDExistingEntityTest() {
		MyTestEntityBuilder myBuilder = new MyTestEntityBuilder();
		
		AnnotatedPersonExample withBuilder = myBuilder.loadExample(MyTestEntityBuilder.EXAMPLE1).build("12");
		AnnotatedPersonExample withoutBuilder =  createTestEntity();
		withoutBuilder.set(DomainEntity.ID, "12");
						
		assertThat(withBuilder.toJSONString(), matchesJSONString(withoutBuilder.toJSONString()));
	}
	
	@Test
	public void buildModifiedEntityTest() {
		MyTestEntityBuilder myBuilder = new MyTestEntityBuilder();
		
		AnnotatedPersonExample withBuilder = myBuilder.loadExample(MyTestEntityBuilder.EXAMPLE1).set(AnnotatedPersonExample.AGE,"30").build(false);
		
		AnnotatedPersonExample withoutBulder =  createTestEntity();
		withoutBulder.set(AnnotatedPersonExample.AGE, "30");
						
		assertThat(withBuilder.toJSONString(), matchesJSONString(withoutBulder.toJSONString()));
	}
	
	private AnnotatedPersonExample createTestEntity() {
		Fields input = new Fields();
		
		input.put(AnnotatedPersonExample.NAME, "aName");
		input.put(AnnotatedPersonExample.SURNAME, "aSurname");
		input.put(AnnotatedPersonExample.AGE, "27");
		input.put(AnnotatedPersonExample.EMAIL, "email@adress.com");
		
		AnnotatedPersonExample de = new AnnotatedPersonExample(input);
		return de;
	}
		

}
