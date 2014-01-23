/*
 * Copyright (c) 2014																 
 *	Mikol Faro			<mikol.faro@gmail.com>
 *	Simone Mangano		<simone.mangano@ieee.org>
 *	Mattia Tortorelli	<mattia.tortorelli@gmail.com>
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

package org.biokoframework.utils.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.biokoframework.utils.domain.AnnotatedPersonExample;
import org.biokoframework.utils.fields.Fields;
import org.biokoframework.utils.json.JSonBuilder;
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