package it.engaged.utils.domain;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import it.bioko.utils.domain.annotation.field.ComponingFieldsFactory;
import it.bioko.utils.domain.annotation.hint.HintFactory;

import java.util.AbstractMap;
import java.util.Map;

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
		assertThat(hintsMap.get(AnnotatedPersonExample.EMAIL).entrySet(), is(empty()));
		assertThat(hintsMap.get(AnnotatedPersonExample.BIRTH_DATE).entrySet(), is(empty()));
		
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
