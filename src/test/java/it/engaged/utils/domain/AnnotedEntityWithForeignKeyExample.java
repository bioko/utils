package it.engaged.utils.domain;

import it.bioko.utils.domain.DomainEntity;
import it.bioko.utils.domain.annotation.field.Field;
import it.bioko.utils.fields.Fields;

@SuppressWarnings("serial")
public class AnnotedEntityWithForeignKeyExample extends DomainEntity {

	@Field
	public static final String		VALUE 			= "value";
	@Field(type=AnnotatedPersonExample.class)
	public static final String		A_FOREIGN_KEY	= "aForeignKey";
	
	public AnnotedEntityWithForeignKeyExample(Fields input) {
		super(input);
	}
	
	public AnnotedEntityWithForeignKeyExample() {
		super(Fields.empty());
	}
	
}
