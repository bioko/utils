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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.biokoframework.utils.json.JSonBuilder;
import org.biokoframework.utils.json.JSonParser;
import org.json.simple.JSONAware;

public class Fields implements Serializable, JSONAware {

	private static final long serialVersionUID = -7605925590938632486L;

	private LinkedHashMap<String, Object> fFields = new LinkedHashMap<String, Object>();

	public Fields(Object... keysAndValues) {
		if (keysAndValues.length % 2 != 0) {
			throw new RuntimeException("The number of elements is expected to be even");
		}

		for (int i = 0; i < keysAndValues.length; i = i + 2) {
			if (!(keysAndValues[i] instanceof String)) {
				throw new RuntimeException("Even indexes are expected to contain Strings");
			} else {
				fFields.put((String) keysAndValues[i], keysAndValues[i + 1]);
			}
		}
	}

	public static Fields fromMap(Map<String, Object> map) {
		Fields fields = new Fields();
		fields.fFields.putAll(map);
		return fields;
	}

	public static Fields fromJson(String actualJSon) {
		JSonParser jSonParser = new JSonParser();
		return Fields.fromMap(jSonParser.parseToMap(actualJSon));
	}

	public boolean containsKey(String aKeyPossiblyContained) {
		return fFields.containsKey(aKeyPossiblyContained);
	}

	public void replace(String aFieldKey, Object aFieldValue) {
		fFields.put(aFieldKey, aFieldValue);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean isEmpty() {
		return fFields.isEmpty();
	}

	public Fields putAll(Fields input) {
		fFields.putAll(input.collection());
		return this;
	}

	private LinkedHashMap<String, Object> collection() {
		return fFields;
	}

	public void remove(String aFieldKey) {
		fFields.remove(aFieldKey);
	}

	public void removeAll() {
		fFields = new LinkedHashMap<String, Object>();
	}

	public Fields copy() {
		Fields result = new Fields();
		return result.putAll(this);
	}

	public List<String> keys() {
		return new ArrayList<String>(fFields.keySet());
	}

	public Fields put(String aKey, Object aFieldObject) {
		fFields.put(aKey, aFieldObject);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) fFields.get(key);
	}

	public Fields putAllFilterdBy(Fields input, ArrayList<String> _mandatoryFields) {
		if (_mandatoryFields != null) {
			for (String each : _mandatoryFields) {
				Object value = input.get(each);
				if (value != null) {
					fFields.put(each, value);
				}
			}
		}
		return this;
	}

	public Fields extract(String... fieldNames) {
		Fields extracted = new Fields();
		for (String fieldName : fieldNames) {
			Object fieldContent = fFields.get(fieldName);
			if (fieldContent != null) {
				extracted.put(fieldName, fieldContent);
			}
		}

		return extracted;
	}

	@Override
	public String toString() {
		return toJSONString();
	}

	@Override
	public String toJSONString() {
		return new JSonBuilder().buildFrom(this);
	}

	/* Status reporters */
	
	@Deprecated
	public static Fields successful() {
		Fields result = new Fields();
		result.put(FieldNames.REQUEST_OUTCOME, FieldValues.OK);
		result.put(FieldNames.COMMAND_OUTCOME, FieldValues.SUCCESSFUL);
		result.remove(FieldNames.REASON);
		return result;
	}

	@Deprecated
	public static Fields failed() {
		Fields result = new Fields();
		result.put(FieldNames.REQUEST_OUTCOME, FieldValues.OK);
		result.put(FieldNames.COMMAND_OUTCOME, FieldValues.FAILED);
		return result;
	}

	@Deprecated
	public static Fields failed(String aFailingReason) {
		Fields result = new Fields();
		result.put(FieldNames.REQUEST_OUTCOME, FieldValues.OK);
		result.put(FieldNames.COMMAND_OUTCOME, FieldValues.FAILED);
		result.put(FieldNames.REASON, "FAILURE: " + aFailingReason);
		return result;
	}

	@Deprecated
	public static Fields error(String anErrorReason) {
		Fields result = new Fields();
		result.put(FieldNames.REQUEST_OUTCOME, FieldValues.KO);
		result.put(FieldNames.COMMAND_OUTCOME, FieldValues.ERROR);
		result.put(FieldNames.REASON, "ERROR: " + anErrorReason);
		return result;
	}
}
