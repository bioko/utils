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

package rg.biokoframework.utils.domain.reflection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.biokoframework.utils.domain.reflection.DummyParameterizedType;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

/**
 * 
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Feb 7, 2014
 *
 */
public class GuiceInjectionTests {

	@SuppressWarnings("rawtypes")
	@Test
	public void simpleTest() {

		// This is the usual way to represent a generic type (for Injection) preferable because its type safe
		TypeLiteral<List<String>> literal = new TypeLiteral<List<String>>() {};
		// And with its Key you can ask for injection
		Key literalKey = Key.get(literal);
		
		// But I cannot do that because the the class were it is used is parameteric itself
		// so I create a dummy parametric type
		// with raw type List
		// and parameter String
		Type dummyType = new DummyParameterizedType(List.class, String.class);
		
		// Watch out this is very unsafe
		Key manualKey = Key.get(dummyType);
		
		// But it works
		assertThat(manualKey, is(equalTo(literalKey)));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void multiKeyTest() {
		// Even with multiparameter types
		
		TypeLiteral<Map<String, Integer>> literal = new TypeLiteral<Map<String ,Integer>>(){};
		Key literalKey = Key.get(literal);
		
		Type dummyType = new DummyParameterizedType(Map.class, String.class, Integer.class);
		Key manualKey = Key.get(dummyType);
		
		assertThat(manualKey, is(equalTo(literalKey)));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void completeInjectionTest() {
		// Given that the keys are the same it is possible to create complete injection
		
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(new TypeLiteral<List<Boolean>>(){}).
				to(new TypeLiteral<LinkedList<Boolean>>(){}).
				in(Singleton.class);
			}
		});
		
		List<Boolean> expected = injector.getInstance(Key.get(new TypeLiteral<List<Boolean>>(){}));
		
		// ITS UNSAFE!!!
		List<Boolean> actual = (List<Boolean>) injector.getInstance(Key.get(new DummyParameterizedType(List.class, Boolean.class)));
		
		// but those should be equal because the binding has Singleton scope
		assertThat(actual, is(equalTo(expected)));
		
	}
	
}
