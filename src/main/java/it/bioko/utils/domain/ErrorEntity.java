package it.bioko.utils.domain;

import it.bioko.utils.domain.annotation.field.Field;
import it.bioko.utils.fields.Fields;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class ErrorEntity extends DomainEntity {

	@Field
	public static final String ERROR_MESSAGE = "errorMessage";
	@Field(type=Integer.class)
	public static final String ERROR_CODE = "errorCode";
	@Field(mandatory=false)
	public static final String ERROR_FIELD = "errorField";
	@Field(mandatory=false)
	public static final String ERROR_DESCRIPTION = "errorDescription";
	
	public ErrorEntity(Fields input) {
		//super(input, componingKeys());
		super(input);
	}
	
	public ErrorEntity(Fields input, ArrayList<String> componingFieldKeys) {
		super(input);
	}
	
}
