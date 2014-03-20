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

package org.biokoframework.utils.filter;

import org.biokoframework.utils.domain.AnnotatedPersonExample;
import org.biokoframework.utils.domain.DomainEntity;
import org.biokoframework.utils.fields.Fields;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Feb 22, 2014
 */
public class FiltersTest {

    @Test
    public void filterByTest() {
        MockFilter<AnnotatedPersonExample> filter = new MockFilter<>();
        
		ArrayList<AnnotatedPersonExample> entities = new ArrayList<>();
		
		AnnotatedPersonExample entity = new AnnotatedPersonExample();
		entity.setAll(new Fields(AnnotatedPersonExample.EMAIL, "dino@bovino.it"));
		entities.add(entity);
		
		entity = new AnnotatedPersonExample();
		entity.setAll(new Fields(AnnotatedPersonExample.EMAIL, "dino@bovino.com"));
		entities.add(entity);
		
		entity = new AnnotatedPersonExample();
		entity.setAll(new Fields(AnnotatedPersonExample.EMAIL, "dino@bovino.eu"));
		entities.add(entity);
		entities.add(new AnnotatedPersonExample());
		
		ArrayList<AnnotatedPersonExample> result = FilterBy.applyFilter(entities, filter);
		
		assertThat(result, is(equalTo(entities)));
		assertThat(filter.getCaptured(), is(equalTo(entities)));
        
    }
    
    @Ignore("don't know what is the correct solution")
    @Test
    public void filterByExceptionHandling() {
    	FilterBy.applyFilter(new ArrayList<AnnotatedPersonExample>(), new Filter<AnnotatedPersonExample>(){
			@Override
			public boolean allows(AnnotatedPersonExample entity) {
//				throw new Exception();
				return false;
			}
    	});
    }

    private static final class MockFilter<DE extends DomainEntity> implements Filter<DE> {

		private ArrayList<DE> fCaptured = new ArrayList<>();

		@Override
		public boolean allows(DE entity) {
			fCaptured.add(entity);
			return true;
		}
		
		public ArrayList<DE> getCaptured() {
			return fCaptured;
		}

    }

}
