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

import java.io.InputStream;
import java.util.Arrays;

import org.biokoframework.utils.domain.DomainEntity;
import org.biokoframework.utils.fields.Fields;
import org.hamcrest.Matcher;

public class Matchers {
	
	public static final Matcher<String> anyString() {
		return AnyString.anyString();
	}
	
	public static final Matcher<String> matchesJSONString(String expectedString) {
		return MatchesJSONString.matchesJSONString(expectedString);
	}
	
	public static Matcher<String> matchesPattern(String pattern) {
		return MatchesPattern.matchesPattern(pattern);
	}
	
	public static Matcher<String> substringMatchesPattern(String pattern) {
		return SubstringMatchesPattern.substringMatchesPattern(pattern);
	}

	public static Matcher<InputStream> equalToStream(InputStream stream) {
		return EqualToStream.equalToStream(stream);
	}
	
	public static Matcher<Fields> empty() {
		return IsEmpty.empty();
	}
	
	public static <DE extends DomainEntity> Matcher<DE> valid() {
		return ValidEntity.valid();
	}

    public static Matcher<Fields> contains(String name, Object value) {
        return ContainsFields.contains(name, value);
    }

}
