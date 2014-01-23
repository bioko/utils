package it.bioko.utils.domain.annotation.field;

import it.bioko.utils.domain.DomainEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MandatoryFieldsFactory {

	public static ArrayList<String> create(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		
		ArrayList<String> mandatoryFields = new ArrayList<String>();
		
		Field[] fields =  annotatedDomainEntity.getFields();
		
		for (Field aField : fields) {
			String fieldName = aField.get(null).toString();
			it.bioko.utils.domain.annotation.field.Field annotation = aField.getAnnotation(it.bioko.utils.domain.annotation.field.Field.class);
			
			if (annotation != null) {
				if (annotation.mandatory())
					mandatoryFields.add(fieldName);				
			}
		}

		return mandatoryFields;
	}
	
	
	
	
}
