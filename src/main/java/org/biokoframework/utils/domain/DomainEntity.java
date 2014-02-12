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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.biokoframework.utils.domain.annotation.field.ComponingFieldsFactory;
import org.biokoframework.utils.domain.annotation.field.EntityValidatorRulesFactory;
import org.biokoframework.utils.fields.Fields;
import org.biokoframework.utils.validator.Validator;
import org.json.simple.JSONAware;


public abstract class DomainEntity implements Serializable, JSONAware {

	private static final long serialVersionUID = -3722504540065126472L;

	// @Field(mandatory = false)
	public static final String ID = "id";
	
	protected Fields _fields = new Fields();
	private ArrayList<String> _componingFields;

	private Validator _validator;
	
	public DomainEntity(Fields input) {		
		try {
			_componingFields = ComponingFieldsFactory.create(this.getClass());
		} catch (Exception e) {
			System.out.println("WARNING! this should not happening, The problem is in DomainEntity(input) constructor");			
		} 
		_fields.putAllFilterdBy(input, _componingFields);
		
		if (input.containsKey(ID)) {
			setId(input.get(ID).toString());
		}
	}
	
	public boolean isValid() {
		if (_validator == null) {
			try {
				_validator = new Validator(EntityValidatorRulesFactory.create(this.getClass()));
			} catch (Exception exception) {
				exception.printStackTrace();
				return false;
			}
		}
		
		boolean valid = _validator.validate(this);
		
		return valid;
	}
	
	public List<ErrorEntity> getValidationErrors() {
		return _validator.getErrors();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof DomainEntity)) {
			return false;
		}

		boolean result = false;
		DomainEntity entity = (DomainEntity)obj;
		if (_fields.equals(entity._fields)) {
			result = true;
		}
		return result;
	}
	
	public Fields fields() {
		return _fields;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	public String toJSONString() {
		String result = null;
		try {
			result = _fields.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + toJSONString();
	}

	public void set(String name, Object value) {
		_fields.put(name, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String fieldName) {
		return (T) _fields.get(fieldName);
	}
	
	public void setId(String id) {
		_fields.put(ID, id);
	}
	
	public String getId() {
		Object id = get(ID);
		
		return id != null ? id.toString() : null;
	}
	
}
