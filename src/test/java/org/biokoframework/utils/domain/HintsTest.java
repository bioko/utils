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

package org.biokoframework.utils.domain;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.AbstractMap;
import java.util.Map;

import org.biokoframework.utils.domain.annotation.field.ComponingFieldsFactory;
import org.biokoframework.utils.domain.annotation.hint.HintFactory;
import org.hamcrest.Matchers;
import org.junit.Test;

public class HintsTest {

	@Test
	public void testAllHintsForAnnotatedPersonExample() throws Exception {
		Map<String, Map<String, String>> hintsMap = HintFactory.createMap(AnnotatedPersonExample.class);
		
		assertThat(hintsMap.keySet(), 
				containsInAnyOrder(ComponingFieldsFactory.create(AnnotatedPersonExample.class).toArray()));
		
		assertThat(hintsMap.get(AnnotatedPersonExample.NAME).entrySet(), Matchers.<Object>containsInAnyOrder(
				new AbstractMap.SimpleEntry<String, String>("maxLength","255"),
				new AbstractMap.SimpleEntry<String, String>("anOtherHint", "pino")));
		
		assertThat(hintsMap.get(AnnotatedPersonExample.SURNAME).entrySet(), is(empty()));
		assertThat(hintsMap.get(AnnotatedPersonExample.AGE).entrySet(), is(empty()));
		assertThat(hintsMap.get(AnnotatedPersonExample.EMAIL).entrySet(), is(not(empty())));
		assertThat(hintsMap.get(AnnotatedPersonExample.BIRTH_DATE).entrySet(), is(not(empty())));
		
	}
	
	@Test
	public void testHintsForHintedField() throws Exception {
		
		Map<String, String> hintsMap = HintFactory.createMap(AnnotatedPersonExample.class, AnnotatedPersonExample.NAME);
		
		assertThat(hintsMap.entrySet(), Matchers.<Object>containsInAnyOrder(
				new AbstractMap.SimpleEntry<String, String>("maxLength","255"),
				new AbstractMap.SimpleEntry<String, String>("anOtherHint", "pino")));
		
	}
	
	@Test
	public void testHintsForNonHintedField() throws Exception {
		
		Map<String, String> hintsMap = HintFactory.createMap(AnnotatedPersonExample.class, AnnotatedPersonExample.SURNAME);
		
		assertThat(hintsMap.entrySet(), is(empty()));
	}
		
}
