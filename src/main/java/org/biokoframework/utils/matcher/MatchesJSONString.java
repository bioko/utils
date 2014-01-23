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

package org.biokoframework.utils.matcher;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MatchesJSONString extends TypeSafeMatcher<String> {

	private String _expectedString;
	private Matcher<Object> equalTo;
	private Matcher<Iterable<? extends Object>> itarableEqualTo;
	

	public MatchesJSONString(String expectedString) {
		_expectedString = expectedString;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendValue(_expectedString);
	}

	@Override
	protected boolean matchesSafely(String actualString) {
		JSONParser parser = new JSONParser();
		
		try {
			Object jsonActual = parser.parse(actualString);
			Object jsonExpected = parser.parse(_expectedString);
			
			if (jsonActual instanceof JSONArray && jsonExpected instanceof JSONArray) {
				itarableEqualTo = containsInAnyOrder(((JSONArray)jsonExpected).toArray());
				return itarableEqualTo.matches(jsonActual);
			} else {
				equalTo = equalTo(jsonExpected);
				return equalTo.matches(jsonActual);
			}
		} catch (ParseException e) {
			return false;
		}
		
	}

	public static final Matcher<String> matchesJSONString(String expectedString) {
		return new MatchesJSONString(expectedString);
	}
}
