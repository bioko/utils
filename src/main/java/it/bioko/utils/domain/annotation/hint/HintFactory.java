package it.bioko.utils.domain.annotation.hint;

import it.bioko.utils.domain.DomainEntity;
import it.bioko.utils.domain.annotation.field.Field;

import java.util.LinkedHashMap;
import java.util.Map;

public class HintFactory {

	public static Map<String, String> createMap(Class<? extends DomainEntity> annotatedDomainEntity, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Map<String, String> hintsMap = null;
		
		for (java.lang.reflect.Field aField : annotatedDomainEntity.getFields()) {
			if (aField.get(null).equals(fieldName)) {
				hintsMap = extractHints(aField.getAnnotation(Field.class));
			}
		}
		
		return hintsMap;
	}
	
	public static Map<String, Map<String, String>> createMap(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		Map<String, Map<String, String>> entityHints = new LinkedHashMap<String, Map<String, String>>();
		
		for (java.lang.reflect.Field aField : annotatedDomainEntity.getFields()) {
			Field annotation = aField.getAnnotation(Field.class);
			if (annotation != null) {
				String fieldName = (String) aField.get(null);
				entityHints.put(fieldName, extractHints(annotation));
			}
		}
		
		return entityHints;
	}

	private static Map<String, String> extractHints(Field annotation) {
		LinkedHashMap<String, String> hintsMap = new LinkedHashMap<String, String>();

		for (Hint aHint : annotation.hints()) {
			hintsMap.put(aHint.name(), aHint.value());
		}
		
		return hintsMap;
	}
	
}
