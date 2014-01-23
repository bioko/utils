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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.biokoframework.utils.domain.AnnotatedPersonExample;
import org.biokoframework.utils.fields.Fields;
import org.biokoframework.utils.json.FieldsMother;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;


public class FieldsTest {

	@Test
	public void onlyOneFieldFromRow() {
		Fields field = Fields.single("KEY", "VALUE");
		String fieldString = field.asRow();
		System.out.println("fieldString: " + fieldString);
		assertEquals(field, Fields.fromRow(fieldString));
	}
	
	@Test
	public void twoFieldsFromRow() {
		Fields field = Fields.single("KEY1", "VALUE1");
		field.put("KEY2", "VALUE2");
		String fieldString = field.asRow();
		System.out.println("fieldString: " + fieldString);
		assertEquals(field, Fields.fromRow(fieldString));
	}
	
	@Test
	public void fieldsWithObjects() {
		Fields withObjects = Fields.empty();
		
		Object theObject = new Object();
		String aFieldName = "OBJECT";
		Object aFieldObject = theObject;
		
		withObjects.put(aFieldName, aFieldObject);
		Object actualObject = withObjects.valueFor(aFieldName, Object.class);
		
		assertEquals(theObject.toString(), actualObject.toString());
	}
	
	@Test
	public void fieldsWithObjectsUsingType() {
		Fields withObjects = Fields.empty();
		
		Object theObject = new Object();
		String aFieldName = "OBJECT";
		Object aFieldObject = theObject;
		
		withObjects.put(aFieldName, aFieldObject);
		Object actualObject = withObjects.valueFor(aFieldName, Object.class);
		
		assertEquals(theObject.toString(), actualObject.toString());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void fieldsWithArrayList() {
		Fields withObjects = Fields.empty();
		
		List<String> theObject = new ArrayList<String>();
		theObject.add("gino.bovino@gmail.com");
		theObject.add("pane.vino@gmail.com");
		
		String aFieldName = "OBJECT";
		
		withObjects.put(aFieldName, theObject);
		List<Object> actualObject = (List<Object>)withObjects.valueFor(aFieldName, ArrayList.class);
		
		assertEquals(theObject.toString(), actualObject.toString());
		assertEquals(theObject.get(0), actualObject.get(0));
		assertEquals(theObject.get(1), actualObject.get(1));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void fieldsWithArrayListOfCustomObjects() {
		Fields withObjects = Fields.empty();
		
		List<AnnotatedPersonExample> theObject = domainEntityInstanceList();
		
		String aFieldName = "OBJECT";
		
		withObjects.put(aFieldName, theObject);
		List<AnnotatedPersonExample> actualObject = withObjects.valueFor(aFieldName, ArrayList.class);
		
		assertEquals(theObject.get(0).report(), actualObject.get(0).report());
		assertEquals(theObject.get(1).report(), actualObject.get(1).report());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void fieldsExplicitCastTest() {
		Fields withObjects = Fields.empty();
		
		List<AnnotatedPersonExample> theObject = domainEntityInstanceList();
		
		String aFieldName = "OBJECT";
		
		withObjects.put(aFieldName, theObject);
		List<AnnotatedPersonExample> actualObject = (ArrayList<AnnotatedPersonExample>)withObjects.valueFor(aFieldName);
		
		assertEquals(theObject.get(0).report(), actualObject.get(0).report());
		assertEquals(theObject.get(1).report(), actualObject.get(1).report());
	}

	@Test
	public void simpleFieldsAsJson() throws Exception {
		Fields someFields = FieldsMother.twoFields();
		assertEquals(FieldsMother.FIRST_EXPECTED, someFields.asJson());
	}

	@Test
	public void moreFieldsAsJson() throws Exception {
		Fields someFields = FieldsMother.fourFields();
		
		assertHaveEqualJSONObjects(FieldsMother.FIRST_AND_SECOND_EXPECTED, someFields.asJson());
	}

	private void assertHaveEqualJSONObjects(String expected, String actual)
			throws ParseException, Exception {
		
		JSONParser jsonParser = new JSONParser();		
		assertEquals(jsonParser.parse(expected), jsonParser.parse(actual));
	}
	
	@Test
	public void fieldsWithArrayListOfDomainEntityAsJson() throws Exception {
		Fields withObjects = Fields.empty();
		List<AnnotatedPersonExample> theObject = domainEntityInstanceList();
		String aFieldName = "OBJECT";
		withObjects.put(aFieldName, theObject);
		assertEquals("{\"OBJECT\":[{\"name\":\"Michelangelo\",\"surname\":\"Buonarroti\"},{\"name\":\"Michelangelo\",\"surname\":\"Buonarroti\"}]}", withObjects.asJson());
	}

	private List<AnnotatedPersonExample> domainEntityInstanceList() {
		List<AnnotatedPersonExample> theObject = new ArrayList<AnnotatedPersonExample>();
		theObject.add(new AnnotatedPersonExample(FieldsMother.twoFields()));
		theObject.add(new AnnotatedPersonExample(FieldsMother.twoFields()));
		return theObject;
	}
}