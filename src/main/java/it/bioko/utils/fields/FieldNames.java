package it.bioko.utils.fields;

import it.bioko.utils.domain.ErrorEntity;

public class FieldNames {
	
	// TODO pulire pulire pulire!!!
    public static final String COMMAND_NAME = "commandName";
    public static final String PRINTED_COMMAND_NAME = "printedCommandName";
    public static final String COMMAND_OUTCOME = "commandOutcome";
    public static final String REQUEST_OUTCOME = "requestOutcome";
    public static final String REASON = "errorReason";
	public static final String FAILED_COMMAND = "failedCommand";
	public static final String ERROR_COMMAND = "errorCommand";    
	public static final String COMMAND_INVOCATION_INPUT_INFO = "commandInvocationInputInfo";
	public static final String COMMAND_INVOCATION_OUTPUT_INFO = "commandInvocationOutputInfo";
	public static final String COMMAND_INFORMATION_ON = "commandInformationOn";
	
	public static final String FIELD_NAME = "fieldName";
	
	public static final String NAME = "name";
	public static final String NAME_VALUE = "Michelangelo";
	public static final String SURNAME = "surname";
	public static final String SURNAME_VALUE = "Buonarroti";
	public static final String EMAIL = "email";
	public static final String EMAIL_VALUE = "michelangelo.buonarroti@firenze.it";
	public static final String AREA = "area";
	public static final String AREA_VALUE = "firenze";
	public static final String ID = "id";
	public static final String ID_VALUE = "999";
	
	@Deprecated
	public static final String ERROR_MESSAGE = ErrorEntity.ERROR_MESSAGE;
	@Deprecated
	public static final String ERROR_CODE = ErrorEntity.ERROR_CODE;
	
	public static final Object NO_ERROR_CODE = "000";

	public static final String BAD_COMMAND_INVOCATION_CODE = "100";
	public static final String COMMAND_NOT_FOUND_CODE = "101";
	
	public static final String ENTITY_WITH_ID_NOT_FOUND_CODE = "102";
	public static final String ENTITY_WITH_FIELD_NOT_FOUND_CODE = "103";
	public static final String EXPECTED_FIELD_NOT_FOUND = "104";
	public static final String ENTITY_NOT_COMPLETE_CODE = "105";
	public static final String ENTITY_ALREADY_EXISTING_CODE = "106";
	public static final String REPOSITORY_IS_EMPTY_CODE = "107";
	public static final String REPOSITORY_MISSING_CODE = "108";
	
	public static final String AUTHENTICATION_REQUIRED_CODE = "110";
	public static final String INVALID_LOGIN_CODE = "111";
	public static final String TOKEN_EXPIRED_CODE = "112";
	public static final String INSUFFICIENT_PRIVILEGES_CODE = "113";
	public static final String FACEBOOK_AUTH_FAILURE_CODE = "114";
	
	public static final String DISSOLUTION_INCOMPLETE_CODE = "120";

	public static final String CONTAINER_EXCEPTION_CODE = "190";
	
	
}
