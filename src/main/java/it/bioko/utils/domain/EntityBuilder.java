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

package it.bioko.utils.domain;

import it.bioko.utils.fields.Fields;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public abstract class EntityBuilder<T extends DomainEntity> {

	protected Fields _currentFields = Fields.empty();
	private HashMap<String, Fields> _fieldsForExamples = new HashMap<String, Fields>();
	
	private Class<T> _entityClass;	
	private JSONObject _currentJsonObject;

	

	public EntityBuilder(Class<T> entityClass) {
		_entityClass = entityClass;
	}
	
	public EntityBuilder<T> loadExample(String exampleName) {	
		_currentFields = _fieldsForExamples .get(exampleName);
		if (_currentFields==null) {
			System.out.println("[EASY MAN] the example '"+exampleName+"' does not exists");
			_currentFields = Fields.empty();
		}
		
		return this;
	}
	
	public abstract EntityBuilder<T> loadDefaultExample();


	@SuppressWarnings("unchecked")
	public JSONArray buildAsArray(boolean exists) {
		JSONArray retArray = new JSONArray();
		retArray.add(build(exists));
		
		return retArray;
	}
	
	
	public T build(boolean exists) {

		Fields buildFields = _currentFields.copy();
		
		if (!exists)
			buildFields.remove(DomainEntity.ID);
		
		T de = null;
		try {
			de = _entityClass.getConstructor(Fields.class).newInstance(buildFields);
		} catch (Exception e) {
			System.out.println("[EASY MAN] the constructor with only fields is missing for class "+_entityClass.getSimpleName());
		}
			
		return de;
	}
	
	public T build(String customID) {
		_currentFields.put(DomainEntity.ID, customID);
		return build(true);
	}


	public void putExample(String exampleName, String jsonData)  {
		jsonData = jsonData.replace("\\'", "$$$$####");
		jsonData = jsonData.replace("'", "\"");
		jsonData = jsonData.replace("$$$$####", "'");
		
		Fields exampleFields = Fields.empty();
		exampleFields.fromJson(jsonData);
		
		
		if (exampleFields.isEmpty()) {
			System.out.println("[EASY MAN] Probably the json loaded in EntityBuilder have a wrong Syntax");
			System.out.println(jsonData);
			System.out.println("--");
		}
			
		// TODO trovare il modo di tracciare un errore di sintassi del json perchè altrimenti ci vogliono ore a fare il debug
//		System.out.println("&&&&&&&&&&&");
//		System.out.println("SOURCE: "+jsonData);
//		try {
//			System.out.println("LOAD: "+exampleFields.asJson());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("&&&&&&&&&&&");
		_fieldsForExamples.put(exampleName, exampleFields);
		
	}

	public EntityBuilder<T> set(String fieldName, String fieldValue) {		
		_currentFields.put(fieldName, fieldValue);
		return this;
	}
	
	public String get(String fieldName) {		
		return _currentFields.stringNamed(fieldName);		
	}
	
	@SuppressWarnings("unchecked")
	public String getJsonForFields(String... fields) {
		JSONObject json = new JSONObject();
		
		for (String aField: fields) {			
			json.put(aField, _currentFields.stringNamed(aField));			
		}		
		
		return json.toJSONString();
	}
	
	public EntityBuilder<T> removeField(String fieldName) {
		_currentFields.remove(fieldName);
		return this;
	}
	
	public EntityBuilder<T> newJson() {
		_currentJsonObject = new JSONObject();
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public EntityBuilder<T> addFieldToJson(String fieldName) {
		_currentJsonObject.put(fieldName, _currentFields.stringNamed(fieldName));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public EntityBuilder<T> addAllFieldsToJson() {		
		for (String fk: _currentFields.keys()) {			
			_currentJsonObject.put(fk, _currentFields.stringNamed(fk));			
		}	
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public EntityBuilder<T> addStringToJson(String fieldName, String fieldValue) {
		_currentJsonObject.put(fieldName, fieldValue);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public EntityBuilder<T> addObjectToJson(String fieldName, JSONAware obj) {
		_currentJsonObject.put(fieldName, obj);
		return this;
	}
	
	
	public String getJson() {
		return _currentJsonObject.toJSONString();
	}
	
	

	public EntityBuilder<T> setId(String id) {
		_currentFields.put(DomainEntity.ID, id);
		return this;
	}

	
	

}
