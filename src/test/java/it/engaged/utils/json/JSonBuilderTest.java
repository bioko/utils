package it.engaged.utils.json;

import static org.junit.Assert.assertEquals;
import it.bioko.utils.fields.Fields;
import it.bioko.utils.json.JSonBuilder;
import it.engaged.utils.domain.AnnotatedPersonExample;

import java.util.ArrayList;

import org.junit.Test;


public class JSonBuilderTest {

	@Test
	public void testFields() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		String actualJSon = jSonBuilder.buildFrom(FieldsMother.twoFields());
		assertEquals(FieldsMother.FIRST_EXPECTED, actualJSon);
	}
	
	@Test
	public void testArrayListOfDomainEntity() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
		
		ArrayList<AnnotatedPersonExample> domainEntityArray = new ArrayList<AnnotatedPersonExample>();
		AnnotatedPersonExample mattoEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		AnnotatedPersonExample paoloEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		domainEntityArray.add(mattoEntity);
		domainEntityArray.add(paoloEntity);
		
		String aKey = "domainEntityArray";
		String actualJSon = jSonBuilder.buildFrom(Fields.single(aKey, domainEntityArray));
		
		assertEquals("{\"" + aKey + "\":[" + FieldsMother.FIRST_EXPECTED + "," + FieldsMother.FIRST_EXPECTED + "]}", actualJSon);
	}
	
	@Test
	public void testFieldsAndArrayListOfDomainEntity() throws Exception {
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
		
		assertEquals(FieldsMother.FIRST_EXPECTED.substring(0, FieldsMother.FIRST_EXPECTED.length()-1) + ",\"" + aKey + "\":[" + FieldsMother.FIRST_EXPECTED + "," + FieldsMother.FIRST_EXPECTED + "]}", actualJSon);
	}
	
	@Test
	public void testEntity() throws Exception {
		JSonBuilder jSonBuilder = new JSonBuilder();
//		
		String failingFieldsString = "commandName#getCampaignById##campaignId#1##requestOutcome#ok##commandOutcome#successful##title#Campagna test##description#Camapagna di test##startDate#Fri Mar 08 10:19:29 CET 2013##endDate#Fri Mar 08 10:19:29 CET 2013##sendDate#Fri Mar 08 10:19:29 CET 2013##businessId#1##templateId#2##textId#null##numberCoupon#10##";
		
		Fields failingFields = Fields.fromRow(failingFieldsString);
		String actualJSon = jSonBuilder.buildFrom(failingFields);
		System.out.println("actualJSon: " + actualJSon);
//		assertEquals(FieldsMother.FIRST_EXPECTED.substring(0, FieldsMother.FIRST_EXPECTED.length()-1) + ",\"" + aKey + "\":[" + FieldsMother.FIRST_EXPECTED + "," + FieldsMother.FIRST_EXPECTED + "]}", actualJSon);
	}
}