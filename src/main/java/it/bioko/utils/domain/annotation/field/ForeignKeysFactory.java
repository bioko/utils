package it.bioko.utils.domain.annotation.field;

import it.bioko.utils.domain.DomainEntity;

import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;

public class ForeignKeysFactory {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Entry<String, Class<? extends DomainEntity>>> create(
			Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		
		ArrayList<Entry<String, Class<? extends DomainEntity>>> foreignKeys = new ArrayList<Entry<String,Class<? extends DomainEntity>>>();
		
		Field[] fields =  annotatedDomainEntity.getFields();
		
		for (Field aField : fields) {
			String fieldName = aField.get(null).toString();
			it.bioko.utils.domain.annotation.field.Field annotation = aField.getAnnotation(it.bioko.utils.domain.annotation.field.Field.class);
			
			if (annotation != null && !aField.get(null).equals(DomainEntity.ID)) {
				if (DomainEntity.class.isAssignableFrom(annotation.type())) {
					foreignKeys.add(new SimpleEntry<String, Class<? extends DomainEntity>>(
							fieldName, (Class<DomainEntity>) annotation.type()));
				}
			}
		}

		
		return foreignKeys;
	}

	public static boolean hasForeignKeys(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields =  annotatedDomainEntity.getFields();
		
		for (Field aField : fields) {
			it.bioko.utils.domain.annotation.field.Field annotation = aField.getAnnotation(it.bioko.utils.domain.annotation.field.Field.class);
			
			if (annotation != null && !aField.get(null).equals(DomainEntity.ID)) {
				if (DomainEntity.class.isAssignableFrom(annotation.type())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
