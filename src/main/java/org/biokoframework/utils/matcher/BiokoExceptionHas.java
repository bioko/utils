/*
 * Copyright (c) ${YEAR}.
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
 * @date 2014 Apr 07
 */
public class BiokoExceptionHas extends TypeSafeMatcher<BiokoException> {

    private final long fExceptedErrorCode;

    public BiokoExceptionHas(long errorCode) {
        fExceptedErrorCode = errorCode;
    }

    @Override
    protected boolean matchesSafely(BiokoException exception) {
        for (ErrorEntity anError : exception.getErrors()) {
            Long erroCode = anError.get(ErrorEntity.ERROR_CODE);
            if (erroCode != null && fExceptedErrorCode == erroCode) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" has error code ").appendValue(fExceptedErrorCode);
    }

    public static Matcher<BiokoException> error(Long errorCode) {
        return new BiokoExceptionHas(errorCode);
    }
}
