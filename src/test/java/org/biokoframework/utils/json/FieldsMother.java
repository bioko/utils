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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.biokoframework.utils.fields.FieldNames;
import org.biokoframework.utils.fields.Fields;

public class FieldsMother {

	public static final String FIRST_EXPECTED = "{\"" + FieldNames.NAME + "\":\"" + FieldNames.NAME_VALUE + "\",\"" + FieldNames.SURNAME + "\":\"" + FieldNames.SURNAME_VALUE + "\"}";
	public static final String FIRST_AND_SECOND_EXPECTED = "{\"" + FieldNames.NAME + "\":\"" + FieldNames.NAME_VALUE + "\",\"" + FieldNames.SURNAME + "\":\"" + FieldNames.SURNAME_VALUE + "\"," +
															"\"" + FieldNames.EMAIL + "\":\"" + FieldNames.EMAIL_VALUE + "\",\"" + FieldNames.AREA + "\":\"" + FieldNames.AREA_VALUE + "\"}";
	
	public static Fields twoFields() {
		Fields field = Fields.fromRow(
				FieldNames.NAME + Fields.KEY_VALUE_SEPARATOR + FieldNames.NAME_VALUE + Fields.FIELDS_SEPARATOR +
				FieldNames.SURNAME + Fields.KEY_VALUE_SEPARATOR + FieldNames.SURNAME_VALUE + Fields.FIELDS_SEPARATOR
				);
		return field;
	}
	
	public static Fields fourFields() {
		Fields field = Fields.fromRow(
				FieldNames.NAME + Fields.KEY_VALUE_SEPARATOR + FieldNames.NAME_VALUE + Fields.FIELDS_SEPARATOR +
				FieldNames.SURNAME + Fields.KEY_VALUE_SEPARATOR + FieldNames.SURNAME_VALUE + Fields.FIELDS_SEPARATOR +
				FieldNames.EMAIL + Fields.KEY_VALUE_SEPARATOR + FieldNames.EMAIL_VALUE + Fields.FIELDS_SEPARATOR +
				FieldNames.AREA + Fields.KEY_VALUE_SEPARATOR + FieldNames.AREA_VALUE + Fields.FIELDS_SEPARATOR
				);
		return field;
	}

	public static Fields fieldsWithList() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("First");
		list.add("Second");
		return Fields.single("list", list);
	}

	public static Fields fieldsWithMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		return Fields.single("object", map );
	}
}
