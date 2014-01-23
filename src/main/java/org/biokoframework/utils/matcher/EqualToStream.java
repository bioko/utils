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

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class EqualToStream extends TypeSafeMatcher<InputStream> {

	private final InputStream _expectedStream;

	public EqualToStream(InputStream expectedStream) {
		_expectedStream = expectedStream;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("a stream");
	}

	@Override
	protected boolean matchesSafely(InputStream actualStream) {
		try {
			boolean equals = IOUtils.contentEquals(_expectedStream, actualStream);
			_expectedStream.close();
			actualStream.close();
			return equals;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void describeMismatchSafely(InputStream actualStream, Description mismatch) {
		mismatch.appendText("was an other stream");
	}
	
	public static Matcher<InputStream> equalToStream(InputStream stream) {
		return new EqualToStream(stream);
	}

}
