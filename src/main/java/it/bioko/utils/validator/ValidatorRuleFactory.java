package it.bioko.utils.validator;

import it.bioko.utils.domain.annotation.field.Field;

import org.apache.commons.lang3.StringUtils;

public class ValidatorRuleFactory {

	public static ValidatorRule fromAnnotation(String fieldName, Field fieldAnnotation) {		
		ValidatorRule rule = new ValidatorRule(fieldAnnotation.type(), 
				fieldAnnotation.mandatory(), fieldAnnotation.pattern(), fieldAnnotation.format());
		if (!StringUtils.isEmpty(fieldAnnotation.dateFormat()))
			rule.setDateFormat(fieldAnnotation.dateFormat());
		
		return rule;
		
	}

}
