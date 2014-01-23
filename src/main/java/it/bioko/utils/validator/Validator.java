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

package it.bioko.utils.validator;

import it.bioko.utils.domain.DomainEntity;
import it.bioko.utils.domain.ErrorEntity;
import it.bioko.utils.fields.Fields;
import it.bioko.utils.validator.format.EmailValidator;
import it.bioko.utils.validator.type.BooleanValidator;
import it.bioko.utils.validator.type.CalendarValidator;
import it.bioko.utils.validator.type.DateTimeValidator;
import it.bioko.utils.validator.type.DoubleValidator;
import it.bioko.utils.validator.type.InputStreamValidator;
import it.bioko.utils.validator.type.IntegerValidator;
import it.bioko.utils.validator.type.LocalDateValidator;
import it.bioko.utils.validator.type.LongValidator;
import it.bioko.utils.validator.type.StringValidator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;


public class Validator {

	public static final String FORMAT_EMAIL = "email";
	
	public static final String ISO_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String ISO_DATE = "yyyy-MM-dd";

	@SuppressWarnings("unchecked")
	private static final List<Class<?>> CALENDAR_TYPES = 
		new ArrayList<Class<?>>(Arrays.asList(Calendar.class, DateTime.class, LocalDate.class));


	private Map<String, ValidatorRule> _descriptors;
	
	private Map<Class<?>, TypeValidator> _typeValidators;
	
	private Map<String, FormatValidator> _formatValidators;


	private List<ErrorEntity> _errors = new ArrayList<ErrorEntity>();



	public Validator(Map<String, ValidatorRule> descriptors) {
		_descriptors = descriptors;
		
		_init();
	}

	public boolean validate(Fields dummyEntityFields) {
		boolean valid = true;
		_errors = new ArrayList<ErrorEntity>();

		// validate mandatory fields basing on descriptions
		valid = validateMandatoryFields(dummyEntityFields) && valid;

		List<String> entityKeys = dummyEntityFields.keys();
		for(String fieldName: entityKeys) {
			ValidatorRule fieldValidationRule = _descriptors.get(fieldName);
			if (fieldValidationRule!=null) {
				Object value = dummyEntityFields.objectNamed(fieldName);
				valid = validateFieldType(fieldName, value,fieldValidationRule) && valid;
				if (value instanceof String) {
					valid = validateFieldFormat(fieldName, (String) value,fieldValidationRule) && valid;
					valid = validateFieldPattern(fieldName, (String) value,fieldValidationRule) && valid;
				}
			}
		}


		return valid;
	}



	private boolean validateFieldPattern(String fieldName, String value, ValidatorRule rule) {
		String pattern = rule.getPattern();
		boolean valid = true;		
		
		if (!StringUtils.isEmpty(pattern)) {
			PatternValidator patternValidator = new PatternValidator(pattern);
			valid = patternValidator.isValid(value);
		}
				
		if (!valid)
			_errors.add(ValidatorErrorBuilder.buildWrongPatternError(fieldName));
			
			
		
		return valid;
	}

	private boolean validateFieldFormat(String fieldName, String value, ValidatorRule rule) {
		String format = rule.getFormat();
		boolean valid = true;
		
		if (!StringUtils.isEmpty(format)) {
			FormatValidator formatValidator = _formatValidators.get(format);
			if (formatValidator != null) {
				valid = formatValidator.isValid(value);
			}
		}						
				
		if (!valid)
			_errors.add(ValidatorErrorBuilder.buildFieldFormatNotValidError(fieldName));
		
		return valid;
	}

	private boolean validateFieldType(String fieldName, Object value, ValidatorRule rule) {
		Class<?> type = rule.getType();
		TypeValidator validator = _typeValidators.get(type);
		
		// TODO domain entities should have their own validator?
		if (DomainEntity.class.isAssignableFrom(type)) {
			return true;
		}
		
		if (validator == null) {
			_errors.add(ValidatorErrorBuilder.buildTypeDontKnowError(fieldName));						
			return false;
		}
		
		if (CALENDAR_TYPES.contains(type) && !StringUtils.isEmpty(rule.getDateFormat()))
			validator.setPattern(rule.getDateFormat());
		
		boolean valid = validator.isValid(value);
		if (!valid)
			_errors.add(ValidatorErrorBuilder.buildWrongTypeError(fieldName));
		return valid;
	}

	private boolean validateMandatoryFields(Fields dummyEntiyFields) {
		boolean retVal = true;

		Set<String> descriptorKeys = _descriptors.keySet();
		for (String fieldName: descriptorKeys) {
			ValidatorRule fieldDescription = _descriptors.get(fieldName);
			
			if (fieldDescription.isMandatory()) {
				Object object = dummyEntiyFields.objectNamed(fieldName);
				if (object == null) {					
					_errors.add(ValidatorErrorBuilder.buildMandatoryFieldsMissingError(fieldName));
					retVal = false;
				} else if (object instanceof String && StringUtils.isEmpty(dummyEntiyFields.stringNamed(fieldName))) {
					_errors.add(ValidatorErrorBuilder.buildMandatoryFieldsMissingError(fieldName));				
					retVal = false;
				} else if (object instanceof List && ((List<?>)object).isEmpty()) {
					_errors.add(ValidatorErrorBuilder.buildMandatoryFieldsMissingError(fieldName));
					retVal = false;
				}
			}
		}
		return retVal;
	}

	

	

	private void _init() {
		_typeValidators = new HashMap<Class<?>, TypeValidator>();
		_typeValidators.put(String.class, new StringValidator());
		_typeValidators.put(Boolean.class, new BooleanValidator());
		_typeValidators.put(Long.class, new LongValidator());
		_typeValidators.put(Integer.class, new IntegerValidator());
		_typeValidators.put(Double.class, new DoubleValidator());
		_typeValidators.put(Calendar.class, new CalendarValidator());
		_typeValidators.put(DateTime.class, new DateTimeValidator());
		_typeValidators.put(LocalDate.class, new LocalDateValidator());
		_typeValidators.put(InputStream.class, new InputStreamValidator());
		
		_formatValidators = new HashMap<String, FormatValidator>();
		_formatValidators.put(Validator.FORMAT_EMAIL, new EmailValidator());

	}

	public List<ErrorEntity> getErrors() {		
		return _errors;
	}

	public boolean validate(DomainEntity de) {
		return this.validate(de.fields());
	}



}
