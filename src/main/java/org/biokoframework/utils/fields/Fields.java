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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.biokoframework.utils.json.JSonBuilder;
import org.biokoframework.utils.json.JSonParser;
import org.json.simple.JSONAware;


public class Fields implements Serializable, JSONAware {

	private static final long serialVersionUID = -7605925590938632486L;
	private static final String PRETTY_KEY_VALUE_SEPARATOR = "=";
	private static final String PRETTY_FIELDS_SEPARATOR = ";";
	public static final String KEY_VALUE_SEPARATOR = "#";
	public static final String FIELDS_SEPARATOR = "##";
	public static final Object MULTIPLE_VALUES_SEPARATOR = "|";
	private LinkedHashMap<String, Object> _fields = new LinkedHashMap<String, Object>();

	public static Fields fromMap(Map<String, Object> map) {
		Fields fields = empty();
		fields._fields.putAll(map);
		return fields;
	}
	
	public static Fields empty() {
		return new Fields();
	}
	
	public String stringNamed(String aFieldName) {
		String fieldValue = (String)_fields.get(aFieldName);
		// alertEmptyField(fieldValue);
		// alertNullFieldValue(fieldValue);
		return fieldValue;
	}

//	private void alertEmptyField(String fieldValue) {
//		if (fieldValue == null || fieldValue.equals(""))
//			_fields.put("WARNING", fieldValue + " IS EMPTY ON NULL");
//	}

	public Object objectNamed(String aFieldName) {
		Object fieldValue = _fields.get(aFieldName);
//		alertNullFieldValue(fieldValue);
		return fieldValue;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T  valueFor(String aFieldName, Class<T> aFieldType) {
		return (T) _fields.get(aFieldName);
	}

	public Object valueFor(String aFieldName) {
		return _fields.get(aFieldName);
	}
	
//	private void alertNullFieldValue(Object fieldValue) {
//		if (fieldValue == null)
//			_fields.put("WARNING", fieldValue + " IS NULL");
//	}

	public static Fields fromRow(String aFieldsRow) {
		Fields result = Fields.empty();
		String[] splittedFields = aFieldsRow.split(FIELDS_SEPARATOR);
		for (String each : splittedFields) {
			String[] keyValueSplitted = each.split(KEY_VALUE_SEPARATOR);
			result.put(keyValueSplitted[0], keyValueSplitted[1]);
		}
		return result;
	}

	public String asRow() {
		StringBuffer result = new StringBuffer();
		for (Entry<String, Object> each : _fields.entrySet()) {
			result.append(each.getKey());
			result.append(KEY_VALUE_SEPARATOR);
			result.append(each.getValue());
			result.append(FIELDS_SEPARATOR);
		}
		return result.toString();
	}
	
	public String asString() {
		StringBuffer result = new StringBuffer();
		for (Entry<String, Object> each : _fields.entrySet()) {
			result.append(each.getKey());
			result.append(PRETTY_KEY_VALUE_SEPARATOR);
			result.append(each.getValue());
			result.append(FIELDS_SEPARATOR);
		}
		String outcome = null;
		try {
			outcome = new String(result.toString().getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outcome;
	}

	public String report() {
		StringBuffer result = new StringBuffer("Fields:");
		for (Entry<String, Object> each : _fields.entrySet()) {
			result.append(each.getKey());
			result.append(PRETTY_KEY_VALUE_SEPARATOR);
			result.append(each.getValue());
			result.append(PRETTY_FIELDS_SEPARATOR);
		}
		return result.toString();
	}
	
	public boolean containsKey(String aKeyPossiblyContained) {
		return _fields.containsKey(aKeyPossiblyContained);
	}

	public boolean containsValue(String aValuePossiblyContained) {
		return _fields.containsValue(aValuePossiblyContained);
	}

	public void replace(String aFieldKey, String aFieldValue) {
		if (_fields.containsKey(aFieldKey))
			_fields.remove(aFieldKey);
		_fields.put(aFieldKey, aFieldValue);
	}

	public static Fields fromUrlEncodedRow(String input) throws UnsupportedEncodingException {
		return fromRow(URLDecoder.decode(input, "UTF-8"));
	}

	@Deprecated
    public static Fields successful() {
        Fields result = Fields.empty();
        result.put(FieldNames.REQUEST_OUTCOME, FieldValues.OK);
        result.put(FieldNames.COMMAND_OUTCOME, FieldValues.SUCCESSFUL);
        result.remove(FieldNames.REASON);
        return result;
	}
    
    @Deprecated
    public static Fields failed() {
        Fields result = Fields.empty();
        result.put(FieldNames.REQUEST_OUTCOME, FieldValues.OK);
        result.put(FieldNames.COMMAND_OUTCOME, FieldValues.FAILED);
        return result;    	
    }

    @Deprecated
	public static Fields failed(String aFailingReason) {
	        Fields result = Fields.empty();
	        result.put(FieldNames.REQUEST_OUTCOME, FieldValues.OK);
	        result.put(FieldNames.COMMAND_OUTCOME, FieldValues.FAILED);
	        result.put(FieldNames.REASON, "FAILURE: " + aFailingReason);
	        return result;
	}
	
	@Deprecated
	public static Fields error(String anErrorReason) {
	        Fields result = Fields.empty();
	        result.put(FieldNames.REQUEST_OUTCOME, FieldValues.KO);
	        result.put(FieldNames.COMMAND_OUTCOME, FieldValues.ERROR);
	        result.put(FieldNames.REASON, "ERROR: " + anErrorReason);
	        return result;
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
		if (_fields.size() == 0)
			return true;
		return false;
	}

	public Fields putAll(Fields input) {
		_fields.putAll(input.collection());
		return this;
	}

	private LinkedHashMap<String, Object> collection() {
		return _fields;
	}
	
	public void remove(String aFieldKey) {
		_fields.remove(aFieldKey);
	}
	
	public boolean containsField(String foreignKeyName, String foreignKeyValue, boolean ignoreCase) {
		if (ignoreCase)
			return containsKey(foreignKeyName) && stringNamed(foreignKeyName).equalsIgnoreCase(foreignKeyValue);
		else
			return containsKey(foreignKeyName) && stringNamed(foreignKeyName).equals(foreignKeyValue);
	}
	
	public boolean containsField(String foreignKeyName, String foreignKeyValue) {
		return containsField(foreignKeyName, foreignKeyValue, false);
	}

	public void removeAll() {
		_fields = new LinkedHashMap<String, Object>();
	}

	public void putFieldFrom(String fieldKey, Fields sourceFields) {
		_fields.put(fieldKey, sourceFields.stringNamed(fieldKey));
	}

	public String reason() {
		return (String)_fields.get(FieldNames.REASON);
	}

	public Fields copy() {
		Fields result = Fields.empty();
		return result.putAll(this);
	}
	
	public boolean contains(String aKey, String aValue) {
		if (!_fields.containsKey(aKey)) return false;
		return true;
	}

	public List<String> keys() {
		return new ArrayList<String>(_fields.keySet());
	}
	
	public static Fields single(String aKey, Object aValue) {
		Fields result = Fields.empty();
		result.put(aKey, aValue);
		return result;
	}

	public Fields put(String aKey, Object aFieldObject) {
		_fields.put(aKey, aFieldObject);
		return this;
	}

	public String asJson() {
		JSonBuilder builder = new JSonBuilder();
		return builder.buildFrom(this);
	}
	
	

	public Fields putAllFilterdBy(Fields input, ArrayList<String> _mandatoryFields) {
		if (_mandatoryFields != null) {
		for (String each : _mandatoryFields) {
			Object value = input.valueFor(each);
			if (value != null) {
				_fields.put(each, value);
			}
		}
		}
		return this;
	}
	
	public Fields extract(String... fieldNames) {
		Fields extracted = Fields.empty();
		for (String fieldName: fieldNames) {
			 Object fieldContent = _fields.get(fieldName);
			if (fieldContent!=null)
				extracted.put(fieldName, fieldContent);
		}
		
		return extracted;
	}

	public Fields fromJson(String actualJSon) {
		JSonParser jSonParser = new JSonParser();
		_fields =  jSonParser.parseToMap(actualJSon);
		return this;
	}

	public Fields fromCommandName(String aCommandName) {
		return put(FieldNames.COMMAND_NAME, aCommandName);
	}

	public boolean contains(String aKey) {
		return _fields.containsKey(aKey);
	}

	public boolean requestOutcomeOK() {
		return contains(FieldNames.REQUEST_OUTCOME) && stringNamed(FieldNames.REQUEST_OUTCOME).equals(FieldValues.OK);
	}

	public boolean commandOutcomeSuccessful() {
		return contains(FieldNames.COMMAND_OUTCOME) && stringNamed(FieldNames.COMMAND_OUTCOME).equals(FieldValues.SUCCESSFUL);
	}

	@Override
	public String toJSONString() {
		return this.asJson();
	}
}