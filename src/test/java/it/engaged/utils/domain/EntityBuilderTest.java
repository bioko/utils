package it.engaged.utils.domain;

import static it.bioko.utils.matcher.Matchers.matchesJSONString;
import static org.junit.Assert.assertThat;
import it.bioko.utils.domain.DomainEntity;
import it.bioko.utils.fields.Fields;

import org.junit.Test;


public class EntityBuilderTest {
	
	@Test
	public void buildNotExistingEntityTest() {
		MyTestEntityBuilder myBuilder = new MyTestEntityBuilder();
		
		AnnotatedPersonExample withBuilder = myBuilder.loadExample(MyTestEntityBuilder.EXAMPLE1).build(false);
		AnnotatedPersonExample withoutBuilder =  createTestEntity();
						
		assertThat(withBuilder.toJSONString(), matchesJSONString(withoutBuilder.toJSONString()));
	}
	
	@Test
	public void buildExistingEntityTest() {
		MyTestEntityBuilder myBuilder = new MyTestEntityBuilder();
		
		AnnotatedPersonExample withBuilder = myBuilder.loadExample(MyTestEntityBuilder.EXAMPLE1).build(true);
		AnnotatedPersonExample withoutBuilder =  createTestEntity();
		withoutBuilder.set(DomainEntity.ID, "1");
						
		assertThat(withBuilder.toJSONString(), matchesJSONString(withoutBuilder.toJSONString()));
	}
	
	
	@Test
	public void buildCustomIDExistingEntityTest() {
		MyTestEntityBuilder myBuilder = new MyTestEntityBuilder();
		
		AnnotatedPersonExample withBuilder = myBuilder.loadExample(MyTestEntityBuilder.EXAMPLE1).build("12");
		AnnotatedPersonExample withoutBuilder =  createTestEntity();
		withoutBuilder.set(DomainEntity.ID, "12");
						
		assertThat(withBuilder.toJSONString(), matchesJSONString(withoutBuilder.toJSONString()));
	}
	
	@Test
	public void buildModifiedEntityTest() {
		MyTestEntityBuilder myBuilder = new MyTestEntityBuilder();
		
		AnnotatedPersonExample withBuilder = myBuilder.loadExample(MyTestEntityBuilder.EXAMPLE1).set(AnnotatedPersonExample.AGE,"30").build(false);
		
		AnnotatedPersonExample withoutBulder =  createTestEntity();
		withoutBulder.set(AnnotatedPersonExample.AGE, "30");
						
		assertThat(withBuilder.toJSONString(), matchesJSONString(withoutBulder.toJSONString()));
	}
	
	private AnnotatedPersonExample createTestEntity() {
		Fields input = Fields.empty();
		
		input.put(AnnotatedPersonExample.NAME, "aName");
		input.put(AnnotatedPersonExample.SURNAME, "aSurname");
		input.put(AnnotatedPersonExample.AGE, "27");
		input.put(AnnotatedPersonExample.EMAIL, "email@adress.com");
		
		AnnotatedPersonExample de = new AnnotatedPersonExample(input);
		return de;
	}
		

}
