package it.bioko.utils.validator;

import it.bioko.utils.domain.ErrorEntity;
import it.bioko.utils.fields.Fields;

public class ValidatorErrorBuilder {
	
	private static final String ERROR_CODE_MANDATORY = "51";
	private static final String ERROR_CODE_TYPE = "52";
	private static final String ERROR_CODE_TYPE_DONT_KNOW = "53";
	private static final String ERROR_CODE_FORMAT = "54";
	private static final String ERROR_CODE_PATERN = "55";	
	private static final String ERROR_CODE_UNIQUE_VIOLATION = "56";
	private static final String ERROR_CODE_READ_ONLY_VIOLATION = "57";
	
	private static final String ERROR_MSG_MANDATORY = "Missing field ";
	private static final String ERROR_MSG_TYPE = "Wrong type for field ";
	private static final String ERROR_MSG_TYPE_DONT_KNOW = "Don't know how to validate type for field ";
	private static final String ERROR_MSG_FORMAT = "Wrong format for field ";
	private static final String ERROR_MSG_PATTERN = "Wrong pattern for field ";	
	private static final String ERROR_MSG_UNIQUE_VIOLATION = "Field must be unique";	
	private static final String ERROR_MSG_READ_ONLY_VIOLATION = "Field is read only";	
	
	

	public static ErrorEntity buildFieldFormatNotValidError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_FORMAT, ERROR_MSG_FORMAT);
	}
	
	
	public static ErrorEntity createErrorEntity(String fieldName, String errorCode, String errorMsg) {
		Fields input = Fields.empty();
		input.put(ErrorEntity.ERROR_FIELD, fieldName);
		input.put(ErrorEntity.ERROR_CODE, errorCode);
		input.put(ErrorEntity.ERROR_MESSAGE, errorMsg);
		ErrorEntity e = new ErrorEntity(input);
				
		return e;
	}
	
	public static ErrorEntity createErrorEntity(String errorCode, String errorMsg) {
		Fields input = Fields.empty();		
		input.put(ErrorEntity.ERROR_CODE, errorCode);
		input.put(ErrorEntity.ERROR_MESSAGE, errorMsg);
		ErrorEntity e = new ErrorEntity(input);
				
		return e;
	}


	public static ErrorEntity buildWrongPatternError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_PATERN, ERROR_MSG_PATTERN);
	}


	public static ErrorEntity buildTypeDontKnowError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_TYPE_DONT_KNOW, ERROR_MSG_TYPE_DONT_KNOW);
	}


	public static ErrorEntity buildWrongTypeError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_TYPE, ERROR_MSG_TYPE);
	}


	public static ErrorEntity buildMandatoryFieldsMissingError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_MANDATORY, ERROR_MSG_MANDATORY);
	}


	public static ErrorEntity buildUniqueViolationError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_UNIQUE_VIOLATION, ERROR_MSG_UNIQUE_VIOLATION);
	}


	public static ErrorEntity buildReadOnlyViolationError(String fieldName) {
		return createErrorEntity(fieldName, ERROR_CODE_READ_ONLY_VIOLATION, ERROR_MSG_READ_ONLY_VIOLATION);
	}

}
