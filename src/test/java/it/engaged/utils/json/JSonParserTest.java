package it.engaged.utils.json;

import static org.junit.Assert.assertEquals;
import it.bioko.utils.fields.FieldNames;
import it.bioko.utils.fields.Fields;
import it.bioko.utils.json.JSonBuilder;
import it.bioko.utils.json.JSonParser;
import it.engaged.utils.domain.AnnotatedPersonExample;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Ignore;
import org.junit.Test;


public class JSonParserTest {

	@Test
	public void testSingleFields() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		String aKey = "A";
		String aValue = "a";
		
		String actualJSon = jSonBuilder.buildFrom(Fields.single(aKey, aValue));
		
		JSonParser jSonParser = new JSonParser();
		LinkedHashMap<String, Object> map = jSonParser.parseToMap(actualJSon);
		
		assertEquals(aValue, map.get(aKey));
	}
	
	@Test
	public void testFields() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		String actualJSon = jSonBuilder.buildFrom(FieldsMother.twoFields());
		
		JSonParser jSonParser = new JSonParser();
		LinkedHashMap<String, Object> map = jSonParser.parseToMap(actualJSon);
		
		assertEquals(FieldNames.NAME_VALUE, map.get(FieldNames.NAME));
		assertEquals(FieldNames.SURNAME_VALUE, map.get(FieldNames.SURNAME));
	}
	
	@Test
	public void testSimpleFieldsFromFieldsToJsonAndBack() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		Fields someFields = FieldsMother.twoFields();
		String actualJSon = jSonBuilder.buildFrom(someFields);

		Fields fieldsFromJson = Fields.empty().fromJson(actualJSon);
		
		assertEquals(someFields.asString(), fieldsFromJson.asString());
	}
	
	@Test
	public void testFieldsWithList() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		Fields someFields = FieldsMother.fieldsWithList();
		String actualJSon = jSonBuilder.buildFrom(someFields);
		
		Fields fieldsFromJson = Fields.empty().fromJson(actualJSon);
		
		assertEquals(someFields.asString(), fieldsFromJson.asString());
	}
	
	@Test
	public void testFieldsWithObjects() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		Fields someFields = FieldsMother.fieldsWithMap();
		String actualJSon = jSonBuilder.buildFrom(someFields);
		
		Fields fieldsFromJson = Fields.empty().fromJson(actualJSon);
		
		assertEquals(someFields.asString(), fieldsFromJson.asString());
	}
	
	// TODO Andrà implementata la creazione dell'ArrayList di DomainEntity da json se serve
	@Ignore
	@Test
	public void testComplexFieldsFromFieldsToJsonAndBack() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		ArrayList<AnnotatedPersonExample> domainEntityArray = new ArrayList<AnnotatedPersonExample>();
		AnnotatedPersonExample mattoEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		AnnotatedPersonExample paoloEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		domainEntityArray.add(mattoEntity);
		domainEntityArray.add(paoloEntity);
		
		String aKey = "domainEntityArray";
		Fields arrayOfDomainEntity = Fields.single(aKey, domainEntityArray);
		
		Fields arrayOfDomainEntityAndFields = FieldsMother.twoFields().putAll(arrayOfDomainEntity);
		String actualJSon = jSonBuilder.buildFrom(arrayOfDomainEntityAndFields);
		
		Fields fieldsFromJson = Fields.empty().fromJson(actualJSon);
		
		assertEquals(arrayOfDomainEntityAndFields.asString(), fieldsFromJson.asString());
	}
}
