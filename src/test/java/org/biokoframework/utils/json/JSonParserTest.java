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

package org.biokoframework.utils.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.biokoframework.utils.domain.AnnotatedPersonExample;
import org.biokoframework.utils.fields.FieldNames;
import org.biokoframework.utils.fields.Fields;
import org.junit.Ignore;
import org.junit.Test;


public class JSonParserTest {

	@Test
	public void testSingleFields() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		String aKey = "A";
		String aValue = "a";
		
		String actualJSon = jSonBuilder.buildFrom(Fields.single(aKey, aValue));
		
		JSonParser jSonParser = new JSonParser();
		LinkedHashMap<String, Object> map = jSonParser.parseToMap(actualJSon);
		
		assertEquals(aValue, map.get(aKey));
	}
	
	@Test
	public void testFields() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		String actualJSon = jSonBuilder.buildFrom(FieldsMother.twoFields());
		
		JSonParser jSonParser = new JSonParser();
		LinkedHashMap<String, Object> map = jSonParser.parseToMap(actualJSon);
		
		assertEquals(FieldNames.NAME_VALUE, map.get(FieldNames.NAME));
		assertEquals(FieldNames.SURNAME_VALUE, map.get(FieldNames.SURNAME));
	}
	
	@Test
	public void testSimpleFieldsFromFieldsToJsonAndBack() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		Fields someFields = FieldsMother.twoFields();
		String actualJSon = jSonBuilder.buildFrom(someFields);

		Fields fieldsFromJson = Fields.fromJson(actualJSon);
		
		assertEquals(someFields.toString(), fieldsFromJson.toString());
	}
	
	@Test
	public void testFieldsWithList() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		Fields someFields = FieldsMother.fieldsWithList();
		String actualJSon = jSonBuilder.buildFrom(someFields);
		
		Fields fieldsFromJson = Fields.fromJson(actualJSon);
		
		assertEquals(someFields.toString(), fieldsFromJson.toString());
	}
	
	@Test
	public void testFieldsWithObjects() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		Fields someFields = FieldsMother.fieldsWithMap();
		String actualJSon = jSonBuilder.buildFrom(someFields);
		
		Fields fieldsFromJson = Fields.fromJson(actualJSon);
		
		assertEquals(someFields.toString(), fieldsFromJson.toString());
	}
	
	// TODO Andrà implementata la creazione dell'ArrayList di DomainEntity da json se serve
	@Ignore
	@Test
	public void testComplexFieldsFromFieldsToJsonAndBack() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		ArrayList<AnnotatedPersonExample> domainEntityArray = new ArrayList<AnnotatedPersonExample>();
		AnnotatedPersonExample mattoEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		AnnotatedPersonExample paoloEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		domainEntityArray.add(mattoEntity);
		domainEntityArray.add(paoloEntity);
		
		String aKey = "domainEntityArray";
		Fields arrayOfDomainEntity = Fields.single(aKey, domainEntityArray);
		
		Fields arrayOfDomainEntityAndFields = FieldsMother.twoFields().putAll(arrayOfDomainEntity);
		String actualJSon = jSonBuilder.buildFrom(arrayOfDomainEntityAndFields);
		
		Fields fieldsFromJson = Fields.fromJson(actualJSon);
		
		assertEquals(arrayOfDomainEntityAndFields.toString(), fieldsFromJson.toString());
	}
}
