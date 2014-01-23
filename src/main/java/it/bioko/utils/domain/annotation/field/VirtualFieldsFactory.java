package it.bioko.utils.domain.annotation.field;

import it.bioko.utils.domain.DomainEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class VirtualFieldsFactory {

	public static ArrayList<String> create(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		return new ArrayList<String>(createWithAnnotation(annotatedDomainEntity).keySet());
	}
	
	public static LinkedHashMap<String, VirtualField> createWithAnnotation(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		
		LinkedHashMap<String, VirtualField> componingFields = new LinkedHashMap<String, VirtualField>();
		Field[] fields =  annotatedDomainEntity.getFields();
		
		for (Field aField : fields) {
			String fieldName = aField.get(null).toString();
			VirtualField annotation = aField.getAnnotation(VirtualField.class);
			
			if (annotation != null) {				
				componingFields.put(fieldName, annotation);				
			}
		}

		return componingFields;
	}
	
}
