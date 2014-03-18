/*
 * Copyright (c) $year. 
 * 	Mikol Faro		<mikol.faro@gmail.com>
 * 	Simone Mangano	 	<simone.mangano@ieee.org>
 * 	Mattia Tortorelli	<mattia.tortorelli@gmail.com>
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
 */

package org.biokoframework.utils.matcher;

import org.biokoframework.utils.domain.ErrorEntity;
import org.biokoframework.utils.exception.BiokoException;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date 2014-03-12
 */
public class ContainsSameError extends TypeSafeMatcher<BiokoException> {
    private final ErrorEntity fExpectedError;

    public ContainsSameError(ErrorEntity error) {
        fExpectedError = error;
    }

    @Override
    protected boolean matchesSafely(BiokoException exception) {
        for (ErrorEntity anError : exception.getErrors()) {
            if (anError.equals(fExpectedError)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("error ").appendText(fExpectedError.toJSONString());
    }

    @Override
    protected void describeMismatchSafely(BiokoException item, Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendText(fExpectedError.toJSONString());
    }

    public static Matcher<BiokoException> error(ErrorEntity error) {
        return new ContainsSameError(error);
    }
}
