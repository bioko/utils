package it.engaged.utils.validator;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import it.bioko.utils.domain.ErrorEntity;
import it.bioko.utils.fields.Fields;
import it.bioko.utils.validator.Validator;
import it.bioko.utils.validator.ValidatorErrorBuilder;
import it.bioko.utils.validator.ValidatorRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Test;

public class ValidatorTest {
	
	@Test
	public void testSimpleFieldsValidatorWithSuccess() {
		
		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
		
		Validator myDummyEntityValidator = new Validator(descriptors);
		
		Fields dummyEntityFields = generateInputFields(); 		
		
		
		boolean validFields = myDummyEntityValidator.validate(dummyEntityFields);
		
		assertThat(validFields, equalTo(true));		
	}
	
	
	@Test
	public void testSimpleFieldsValidatorWithFailForMandatory() {
		
		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
		
		ValidatorRule aMissingFieldDesription = new ValidatorRule(Integer.class, true, null, null);
		descriptors.put("aMissingField", aMissingFieldDesription);
		
		Validator myDummyEntityValidator = new Validator(descriptors);
		
		Fields dummyEntityFields = generateInputFields(); 		
		
		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(false));
		ErrorEntity error = ValidatorErrorBuilder.buildMandatoryFieldsMissingError("aMissingField");
		assertArrayEquals(createSingleErrorEntityArray("aMissingField", error.get(ErrorEntity.ERROR_CODE), error.get(ErrorEntity.ERROR_MESSAGE)).toArray(), 
				myDummyEntityValidator.getErrors().toArray());
	}
	
	@Test
	public void testSimpleFieldsValidatorWithFailForType() {
		
		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
		
		ValidatorRule aDesriptionForAWrongTypedField = new ValidatorRule(Integer.class, true, null, null);
		descriptors.put("aWrongTypedField", aDesriptionForAWrongTypedField);
		
		Validator myDummyEntityValidator = new Validator(descriptors);
		
		Fields dummyEntityFields = generateInputFields(); 		
		dummyEntityFields.put("aWrongTypedField", "this should be a number");
		
		boolean valid = myDummyEntityValidator.validate(dummyEntityFields); 
		
		assertThat(valid, equalTo(false));
		ErrorEntity error = ValidatorErrorBuilder.buildWrongTypeError("aWrongTypedField");
		assertArrayEquals(createSingleErrorEntityArray("aWrongTypedField", error.get(ErrorEntity.ERROR_CODE), error.get(ErrorEntity.ERROR_MESSAGE)).toArray(), 
				myDummyEntityValidator.getErrors().toArray());
		
		
	}
	
	@Test
	public void testSimpleFieldsValidatorWithFailForFormat() {
		
		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
		
		ValidatorRule aDesriptionForAWrongTypedField = new ValidatorRule(String.class, true, null, Validator.FORMAT_EMAIL);
		descriptors.put("aWrongFormatField", aDesriptionForAWrongTypedField);
		
		Validator myDummyEntityValidator = new Validator(descriptors);
		
		Fields dummyEntityFields = generateInputFields(); 		
		dummyEntityFields.put("aWrongFormatField", "this should be a mail address");
		
		
		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(false));
		ErrorEntity error = ValidatorErrorBuilder.buildFieldFormatNotValidError("aWrongFormatField");
		assertArrayEquals(createSingleErrorEntityArray("aWrongFormatField", error.get(ErrorEntity.ERROR_CODE), error.get(ErrorEntity.ERROR_MESSAGE)).toArray(), 
				myDummyEntityValidator.getErrors().toArray());
		
		
	}
	
	
	@Test
	public void testSimpleFieldsValidatorWithFailForPattern() {
		
		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
		
		ValidatorRule myDomainEmailAddress = new ValidatorRule(String.class, true, ".*@mydomain.net", Validator.FORMAT_EMAIL);
		descriptors.put("otherEmail", myDomainEmailAddress);
		
		Validator myDummyEntityValidator = new Validator(descriptors);
		
		Fields dummyEntityFields = generateInputFields(); 		
		dummyEntityFields.put("otherEmail", "you@yourdomain.com");
		
		
		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(false));
		ErrorEntity error = ValidatorErrorBuilder.buildWrongPatternError("otherEmail");
		assertArrayEquals(createSingleErrorEntityArray("otherEmail", error.get(ErrorEntity.ERROR_CODE), error.get(ErrorEntity.ERROR_MESSAGE)).toArray(), 
				myDummyEntityValidator.getErrors().toArray());
		
		// correct one
		dummyEntityFields.put("otherEmail", "me@mydomain.net");
		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(true));
		
		
	}
	
	
	@Test
	public void testSimpleFieldsValidatorForCalendarWithSuccess() {
		
		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
		
		ValidatorRule italianDateDesription = new ValidatorRule(LocalDate.class, false, null, null);
		italianDateDesription.setDateFormat(Validator.ISO_DATE);
		descriptors.put("italianDate", italianDateDesription);
		
		Validator myDummyEntityValidator = new Validator(descriptors);
		
		Fields dummyEntityFields = generateInputFields(); 		
		dummyEntityFields.put("italianDate", "1974-10-19");
		
		printErrors(myDummyEntityValidator.getErrors());
		
		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(true));
	}
	
	
	@Test
	public void testSimpleFieldsValidatorForCalendarWithFail() {
		
		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
		
		ValidatorRule italianDateDesription = new ValidatorRule(LocalDate.class, false, null, null);
		italianDateDesription.setDateFormat(Validator.ISO_DATE);
		descriptors.put("italianDate", italianDateDesription);
		
		Validator myDummyEntityValidator = new Validator(descriptors);
		
		Fields dummyEntityFields = generateInputFields(); 		
		dummyEntityFields.put("italianDate", "1974-19-10");
		
		printErrors(myDummyEntityValidator.getErrors());
		
		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(false));
		printErrors(myDummyEntityValidator.getErrors());
		
		ErrorEntity error = ValidatorErrorBuilder.buildWrongTypeError("italianDate");
		assertArrayEquals(createSingleErrorEntityArray("italianDate", error.get(ErrorEntity.ERROR_CODE), error.get(ErrorEntity.ERROR_MESSAGE)).toArray(), 
				myDummyEntityValidator.getErrors().toArray());
		
		// correct one
		dummyEntityFields.put("italianDate", "1974-10-19");
		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(true));
	}
	
	
	@Test
	public void testFieldNotValidableWithFail() {
		
		Map<String, ValidatorRule> descriptors = createFieldDescriptors();
		ValidatorRule ruleWithWrongType = new ValidatorRule(ArrayList.class, false, null, null);
		descriptors.put("wrongTypedField", ruleWithWrongType);
		
		Validator myDummyEntityValidator = new Validator(descriptors);
		
		
		Fields dummyEntityFields = generateInputFields(); 		
		dummyEntityFields.put("wrongTypedField", "Nothing special to put there");
		
		
		assertThat(myDummyEntityValidator.validate(dummyEntityFields), equalTo(false));
		ErrorEntity error = ValidatorErrorBuilder.buildTypeDontKnowError("wrongTypedField");
		assertArrayEquals(createSingleErrorEntityArray("wrongTypedField", error.get(ErrorEntity.ERROR_CODE), error.get(ErrorEntity.ERROR_MESSAGE)).toArray(), 
				myDummyEntityValidator.getErrors().toArray());
		
		
	}
	
	
	
	private Map<String, ValidatorRule> createFieldDescriptors() {
		Map<String, ValidatorRule> descriptors = new HashMap<String, ValidatorRule>();
		
		ValidatorRule nameDesription = new ValidatorRule(String.class, true, null, null);
		ValidatorRule emailDesription = new ValidatorRule(String.class, false, null, Validator.FORMAT_EMAIL);
		ValidatorRule ageDesription = new ValidatorRule(Integer.class, false, null, null);
		
		
		descriptors.put("name", nameDesription);
		descriptors.put("email", emailDesription);
		descriptors.put("age", ageDesription);
		
		return descriptors;
		
	}


	private Fields generateInputFields() {
		Fields f = Fields.empty();
		f.put("name", "gino pino");
		f.put("email", "gino@pino.net");
		f.put("age","28");
//		f.put("italianDate","19/10/1974");
		
		return f;
	}
	
	public void printErrors(List<ErrorEntity> errors) {
		for (ErrorEntity e: errors)
			System.out.println(e.toJSONString());
	}
	
	private List<ErrorEntity> createSingleErrorEntityArray(String fieldName, String errorCode, String errorMsg) {
		Fields input = Fields.empty();
		input.put(ErrorEntity.ERROR_FIELD, fieldName);
		input.put(ErrorEntity.ERROR_CODE, errorCode);
		input.put(ErrorEntity.ERROR_MESSAGE, errorMsg);
		ErrorEntity e = new ErrorEntity(input);
				
		List<ErrorEntity> a = new ArrayList<ErrorEntity>();
		a.add(e);
		
		return a;
	}

}
