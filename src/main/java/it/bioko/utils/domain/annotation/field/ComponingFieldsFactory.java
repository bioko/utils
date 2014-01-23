package it.bioko.utils.domain.annotation.field;

import it.bioko.utils.domain.DomainEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ComponingFieldsFactory {

	public static ArrayList<String> create(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		return new ArrayList<String>(createWithAnnotation(annotatedDomainEntity).keySet());
	}
	
	public static LinkedHashMap<String,it.bioko.utils.domain.annotation.field.Field> createWithAnnotation(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		
		LinkedHashMap<String, it.bioko.utils.domain.annotation.field.Field> componingFields = new LinkedHashMap<String, it.bioko.utils.domain.annotation.field.Field>();
		Field[] fields =  annotatedDomainEntity.getFields();
		
		for (Field aField : fields) {
			String fieldName = aField.get(null).toString();
			it.bioko.utils.domain.annotation.field.Field annotation = aField.getAnnotation(it.bioko.utils.domain.annotation.field.Field.class);
			
			if (annotation != null) {				
				componingFields.put(fieldName, annotation);				
			}
		}

		return componingFields;
	}
	
}
