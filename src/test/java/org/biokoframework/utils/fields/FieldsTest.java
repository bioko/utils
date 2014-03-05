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

package org.biokoframework.utils.fields;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.biokoframework.utils.domain.AnnotatedPersonExample;
import org.biokoframework.utils.json.FieldsMother;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FieldsTest {

	@Test
	public void fieldsWithObjects() {
		Fields withObjects = new Fields();

		Object theObject = new Object();
		String aFieldName = "OBJECT";
		Object aFieldObject = theObject;

		withObjects.put(aFieldName, aFieldObject);
		Object actualObject = withObjects.get(aFieldName);

		assertEquals(theObject.toString(), actualObject.toString());
	}

	@Test
	public void fieldsWithObjectsUsingType() {
		Fields withObjects = new Fields();

		Object theObject = new Object();
		String aFieldName = "OBJECT";
		Object aFieldObject = theObject;

		withObjects.put(aFieldName, aFieldObject);
		Object actualObject = withObjects.get(aFieldName);

		assertEquals(theObject.toString(), actualObject.toString());
	}

	@Test
	public void fieldsWithArrayList() {
		Fields withObjects = new Fields();

		List<String> theObject = new ArrayList<String>();
		theObject.add("gino.bovino@gmail.com");
		theObject.add("pane.vino@gmail.com");

		String aFieldName = "OBJECT";

		withObjects.put(aFieldName, theObject);
		List<Object> actualObject = withObjects.get(aFieldName);

		assertEquals(theObject.toString(), actualObject.toString());
		assertEquals(theObject.get(0), actualObject.get(0));
		assertEquals(theObject.get(1), actualObject.get(1));
	}

	@Test
	public void fieldsWithArrayListOfCustomObjects() {
		Fields withObjects = new Fields();

		List<AnnotatedPersonExample> theObject = domainEntityInstanceList();

		String aFieldName = "OBJECT";

		withObjects.put(aFieldName, theObject);
		List<AnnotatedPersonExample> actualObject = withObjects.get(aFieldName);

		assertThat(actualObject.get(0).toJSONString(), is(equalTo(theObject.get(0).toJSONString())));
		assertThat(actualObject.get(1).toJSONString(), is(equalTo(theObject.get(1).toJSONString())));
	}

	@Test
	public void fieldsExplicitCastTest() {
		Fields withObjects = new Fields();

		List<AnnotatedPersonExample> theObject = domainEntityInstanceList();

		String aFieldName = "OBJECT";

		withObjects.put(aFieldName, theObject);
		List<AnnotatedPersonExample> actualObject = withObjects.get(aFieldName);

		assertThat(actualObject.get(0).toJSONString(), is(equalTo(theObject.get(0).toJSONString())));
		assertThat(actualObject.get(1).toJSONString(), is(equalTo(theObject.get(1).toJSONString())));
	}

	@Test
	public void simpleFieldsAsJson() throws Exception {
		Fields someFields = FieldsMother.twoFields();
		assertEquals(FieldsMother.FIRST_EXPECTED, someFields.toJSONString());
	}

	@Test
	public void moreFieldsAsJson() throws Exception {
		Fields someFields = FieldsMother.fourFields();

		assertHaveEqualJSONObjects(FieldsMother.FIRST_AND_SECOND_EXPECTED,
				someFields.toJSONString());
	}

	private void assertHaveEqualJSONObjects(String expected, String actual) throws ParseException, Exception {
		JSONParser jsonParser = new JSONParser();
		assertThat(jsonParser.parse(actual), is(equalTo(jsonParser.parse(expected))));
	}

	@Test
	public void fieldsWithArrayListOfDomainEntityAsJson() throws Exception {
		Fields withObjects = new Fields();
		List<AnnotatedPersonExample> theObject = domainEntityInstanceList();
		String aFieldName = "OBJECT";
		withObjects.put(aFieldName, theObject);
		assertEquals(
				"{\"OBJECT\":[{\"name\":\"Michelangelo\",\"surname\":\"Buonarroti\"},"
				+ "{\"name\":\"Michelangelo\",\"surname\":\"Buonarroti\"}]}",
				withObjects.toJSONString());
	}

	private List<AnnotatedPersonExample> domainEntityInstanceList() {
		List<AnnotatedPersonExample> theObject = new ArrayList<AnnotatedPersonExample>();
		
		AnnotatedPersonExample entity = new AnnotatedPersonExample();
		entity.setAll(FieldsMother.twoFields());
		theObject.add(entity);
				
		entity = new AnnotatedPersonExample();
		entity.setAll(FieldsMother.twoFields());
		theObject.add(entity);
		return theObject;
	}

	@Test
	public void containmentTests() {
		Fields fields = new Fields(
				"dino", "bovino",
				"val", 3);
		
		assertThat(fields.containsKey("dino"), is(true));
		assertThat(fields.containsKey("val"), is(true));
		assertThat(fields.containsKey("not existing"), is(false));
		
	}
	
	@Test
	public void extractionTest() {
		Fields fields = new Fields(
				"dino", "bovino",
				"val", 3,
				"anOtherVal", "other");
		
		assertThat(fields.extract("dino", "val"), 
				is(equalTo(new Fields(
						"dino", "bovino",
						"val", 3))));
	}
	
	@Test
	public void javascriptConstructorEmptyTest() {
		Fields fields = new Fields();
		assertThat(fields.isEmpty(), is(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void javascriptConstructorSimpleTest() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4);

		Fields fields = new Fields(
				"dino", "bovino", 
				"number", 22, 
				"list", list);

		assertThat(fields.keys(), contains("dino", "number", "list"));
		assertThat((String) fields.get("dino"), is(equalTo("bovino")));
		assertThat((Integer) fields.get("number"), is(equalTo(22)));
		assertThat((List<Integer>) fields.get("list"), is(equalTo(list)));
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void javascriptConstructorFailForOddItemNumber() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage(is(equalTo("The number of elements is expected to be even")));

		new Fields(
				"dino", "bovino", 
				"gino", 3, 
				"pino");
	}

	@Test
	public void javascriptConstructorFailForKeyNotBeingAString() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage(is(equalTo("Even indexes are expected to contain Strings")));

		new Fields(
				"dino", "bovino", 
				"gino", 3, 
				3, "tino");
	}
}