package it.bioko.utils.domain.annotation.field;

import it.bioko.utils.domain.DomainEntity;
import it.bioko.utils.validator.ValidatorRule;
import it.bioko.utils.validator.ValidatorRuleFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityValidatorRulesFactory {

	public static Map<String, ValidatorRule> create(Class<? extends DomainEntity> annotatedDomainEntity) throws IllegalArgumentException, IllegalAccessException {
		
		Map<String, ValidatorRule> rules = new HashMap<String, ValidatorRule>();
		
		Field[] fields =  annotatedDomainEntity.getFields();
		
		for (Field aField : fields) {
			String fieldName = aField.get(null).toString();
			it.bioko.utils.domain.annotation.field.Field annotation = aField.getAnnotation(it.bioko.utils.domain.annotation.field.Field.class);
			
			if (annotation != null) {
				ValidatorRule rule = ValidatorRuleFactory.fromAnnotation(fieldName, annotation);
				rules.put(fieldName, rule);
			}

		}
		
		return rules;
	}
	
	

}
