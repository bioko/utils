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

import org.biokoframework.utils.domain.AnnotatedPersonExample;
import org.biokoframework.utils.fields.FieldNames;
import org.biokoframework.utils.fields.Fields;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;


//import static org.hamcrest.Matchers.*;

public class FieldsToJsonTest {

	@Test
	public void emptyFieldsToJson() throws Exception {
		Fields empty = Fields.empty();
		JSONObject object = new JSONObject();
		assertEquals(empty.toJSONString(), object.toJSONString());
	}

	@Test
	public void plainFieldsToJson() throws Exception {
		Fields fields = FieldsMother.twoFields();
		JSONObject object = new JSONObject();
		object.put(FieldNames.NAME, FieldNames.NAME_VALUE);
		object.put(FieldNames.SURNAME, FieldNames.SURNAME_VALUE);
		assertEquals(fields.toJSONString(), object.toJSONString());
	}

	@Test
	public void arrayOfDomainAntityFieldsToJson() throws Exception {
		ArrayList<AnnotatedPersonExample> domainEntityArray = new ArrayList<AnnotatedPersonExample>();
		AnnotatedPersonExample mattoEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		AnnotatedPersonExample paoloEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		domainEntityArray.add(mattoEntity);
		domainEntityArray.add(paoloEntity);

		String aKey = "domainEntityArray";
		Fields fields = Fields.single(aKey, domainEntityArray);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(aKey, domainEntityArray);
		String jsonString = JSONValue.toJSONString(jsonObject);

		assertEquals(fields.toJSONString(), jsonString);
	}

	@Test
	public void fieldsAndarrayOfDomainAntityToJson() throws Exception {
		ArrayList<AnnotatedPersonExample> domainEntityArray = new ArrayList<AnnotatedPersonExample>();
		AnnotatedPersonExample mattoEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		AnnotatedPersonExample paoloEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		domainEntityArray.add(mattoEntity);
		domainEntityArray.add(paoloEntity);

		String aKey = "domainEntityArray";
		Fields arrayOfDomainEntity = Fields.single(aKey, domainEntityArray);
		Fields fields = FieldsMother.twoFields().putAll(arrayOfDomainEntity);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(FieldNames.NAME, FieldNames.NAME_VALUE);
		jsonObject.put(FieldNames.SURNAME, FieldNames.SURNAME_VALUE);
		jsonObject.put(aKey, domainEntityArray);
		String jsonString = JSONValue.toJSONString(jsonObject);

		assertEquals(fields.toJSONString(), jsonString);
	}
}