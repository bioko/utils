package it.bioko.utils.domain;

import it.bioko.utils.domain.annotation.field.ComponingFieldsFactory;
import it.bioko.utils.domain.annotation.field.EntityValidatorRulesFactory;
import it.bioko.utils.fields.Fields;
import it.bioko.utils.validator.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.simple.JSONAware;


@SuppressWarnings("serial")
public abstract class DomainEntity implements Serializable, JSONAware {

	public static final String ID = "id";
	
	protected String _keyName;
	protected Fields _fields = Fields.empty();
	private ArrayList<String> _componingFields;

	private Validator _validator;

	
	public DomainEntity(Fields input) {		
		try {
			_componingFields = ComponingFieldsFactory.create(this.getClass());
		} catch (Exception e) {
			System.out.println("WARNING! this should not happening, The problem is in DomainEntity(input) constructor");			
		} 
		_fields.putAllFilterdBy(input, _componingFields);
		_keyName = ID;
		if (input.containsKey(ID))
			setId(input.stringNamed(ID));
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
	
	@Deprecated
	public void setKeyValue(String keyValue){
		_fields.put(_keyName, keyValue);
	}
	
	public final String report() {
		StringBuilder result = new StringBuilder().
				append(getClass().getSimpleName()).
				append(" [entityValue=").
				append(_fields.report()).
				append("]");
		return result.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		boolean result= false;
		if (fields().equals(((DomainEntity)obj).fields())) {
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
	
	@Deprecated
	public boolean containsField(String foreignKeyName, String foreignKeyValue, boolean ignoreCase) {
		return _fields.containsField(foreignKeyName,foreignKeyValue, ignoreCase);
	}
	
	@Deprecated
	public boolean containsField(String foreignKeyName, String foreignKeyValue) {
		return containsField(foreignKeyName, foreignKeyValue, false);
	}

	public String toJSONString() {
		String result = null;
		try {
			result = _fields.asJson();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void set(String name, String value) {
		_fields.put(name, value);
	}
	
	public String get(String fieldName) {
		return _fields.stringNamed(fieldName);
	}
	
	public void setId(String id) {
		_fields.put(ID, id);
	}
	
	public String getId() {
		if (!_fields.contains(ID)) {
			return null;
		}
		return get(ID);
	}

}
