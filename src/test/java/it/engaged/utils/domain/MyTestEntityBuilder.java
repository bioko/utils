package it.engaged.utils.domain;

import it.bioko.utils.domain.EntityBuilder;

public class MyTestEntityBuilder extends EntityBuilder<AnnotatedPersonExample> {

	public static final String EXAMPLE1 = "aNameForExample1";
	public static final String EXAMPLE2 = "aNameForExample2";

	
	public MyTestEntityBuilder() {
		super(AnnotatedPersonExample.class);
		
		putExample(EXAMPLE1,"{ 'id': '1','name': 'aName','surname': 'aSurname','age': '27', 'email': 'email@adress.com' }");
		putExample(EXAMPLE2,"{ 'id': '2','name': 'aName','surname': 'aSurname','age': '30', 'email': 'email@adress.com' }");
		
	}


	@Override
	public EntityBuilder<AnnotatedPersonExample> loadDefaultExample() {		
		return loadExample(EXAMPLE1);
	}


	


	
	
	

}
