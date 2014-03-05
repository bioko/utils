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

import java.util.HashMap;

import org.biokoframework.utils.fields.Fields;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.google.inject.Injector;

public abstract class EntityBuilder<T extends DomainEntity> {

	protected Fields fCurrentFields = new Fields();
	private HashMap<String, Fields> fFieldsForExamples = new HashMap<String, Fields>();
	
	private Class<T> fEntityClass;	
	private JSONObject fCurrentJsonObject;
	private Injector fInjector;

	public EntityBuilder(Class<T> entityClass) {
		this(entityClass, null);
	}
	
	public EntityBuilder(Class<T> entityClass, Injector injector) {
		fEntityClass = entityClass;
		fInjector = injector;
	}

	public EntityBuilder<T> loadExample(String exampleName) {
		fCurrentFields = fFieldsForExamples.get(exampleName);
		if (fCurrentFields == null) {
			System.out.println("[EASY MAN] the example '" + exampleName + "' does not exists");
			fCurrentFields = new Fields();
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

		Fields buildFields = fCurrentFields.copy();
		
		if (!exists) {
			buildFields.remove(DomainEntity.ID);
		}
		
		T de = null;
		if (fInjector != null) {
			de = fInjector.getInstance(fEntityClass);
			de.setAll(buildFields);
		} else {
			try {
				de = fEntityClass.getConstructor(Fields.class).newInstance(buildFields);
			} catch (Exception e) {
				try {
					de = fEntityClass.newInstance();
					de.setAll(buildFields);
				} catch (Exception exception) {
					System.out.println("[EASY MAN] the constructor with only fields is missing for class " + fEntityClass.getSimpleName());
				}
			}
		}
			
		return de;
	}
	
	public T build(String customID) {
		fCurrentFields.put(DomainEntity.ID, customID);
		return build(true);
	}


	public void putExample(String exampleName, String jsonData)  {
		jsonData = jsonData.replace("\\'", "$$$$####");
		jsonData = jsonData.replace("'", "\"");
		jsonData = jsonData.replace("$$$$####", "'");
		
		Fields exampleFields = Fields.fromJson(jsonData);
		
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
		fFieldsForExamples.put(exampleName, exampleFields);
		
	}

	public EntityBuilder<T> set(String fieldName, Object fieldValue) {		
		fCurrentFields.put(fieldName, fieldValue);
		return this;
	}
	
	public String get(String fieldName) {		
		return fCurrentFields.get(fieldName);		
	}
	
	@SuppressWarnings("unchecked")
	public String getJsonForFields(String... fields) {
		JSONObject json = new JSONObject();
		
		for (String aField: fields) {			
			json.put(aField, fCurrentFields.get(aField));			
		}		
		
		return json.toJSONString();
	}
	
	public EntityBuilder<T> removeField(String fieldName) {
		fCurrentFields.remove(fieldName);
		return this;
	}
	
	public EntityBuilder<T> newJson() {
		fCurrentJsonObject = new JSONObject();
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public EntityBuilder<T> addFieldToJson(String fieldName) {
		fCurrentJsonObject.put(fieldName, fCurrentFields.get(fieldName));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public EntityBuilder<T> addAllFieldsToJson() {		
		for (String fk: fCurrentFields.keys()) {			
			fCurrentJsonObject.put(fk, fCurrentFields.get(fk));			
		}	
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public EntityBuilder<T> addStringToJson(String fieldName, String fieldValue) {
		fCurrentJsonObject.put(fieldName, fieldValue);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public EntityBuilder<T> addObjectToJson(String fieldName, JSONAware obj) {
		fCurrentJsonObject.put(fieldName, obj);
		return this;
	}
	
	
	public String getJson() {
		return fCurrentJsonObject.toJSONString();
	}
	
	

	public EntityBuilder<T> setId(String id) {
		fCurrentFields.put(DomainEntity.ID, id);
		return this;
	}

	
	

}
