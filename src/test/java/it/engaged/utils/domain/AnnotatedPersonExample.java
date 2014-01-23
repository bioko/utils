package it.engaged.utils.domain;

import it.bioko.utils.domain.DomainEntity;
import it.bioko.utils.domain.annotation.field.Field;
import it.bioko.utils.domain.annotation.hint.Hint;
import it.bioko.utils.domain.annotation.hint.HintNames;
import it.bioko.utils.fields.Fields;
import it.bioko.utils.validator.Validator;

import org.joda.time.LocalDate;


@SuppressWarnings("serial")
public class AnnotatedPersonExample extends DomainEntity{

	@Field(hints = {
		@Hint(name = HintNames.MAX_LENGTH, value = "255"),
		@Hint(name = "anOtherHint", value = "pino")
	})
	public static final String		NAME 		= "name";
	@Field(mandatory=false)
	public static final String		SURNAME 	= "surname";
	@Field(mandatory=false, type=Integer.class)
	public static final String		AGE			= "age";
	@Field(mandatory=false, type=String.class, format=Validator.FORMAT_EMAIL)
	public static final String		EMAIL		= "email";
	@Field(mandatory=false, type=LocalDate.class, dateFormat="dd/MM/yyyy")
	public static final String		BIRTH_DATE	= "birthDate";

	
	public AnnotatedPersonExample(Fields input) {
		super(input);
	}
	
	public AnnotatedPersonExample() {
		super(Fields.empty());
	}
	
}