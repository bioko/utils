package org.biokoframework.utils.filter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.biokoframework.utils.domain.AnnotatedPersonExample;
import org.biokoframework.utils.domain.DomainEntity;
import org.biokoframework.utils.fields.Fields;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Feb 22, 2014
 */
public class FiltersTest {

    @Test
    public void filterByTest() {
        MockFilter<AnnotatedPersonExample> filter = new MockFilter<>();
        
		ArrayList<AnnotatedPersonExample> entities = new ArrayList<>();
		entities.add(new AnnotatedPersonExample(new Fields(
				AnnotatedPersonExample.EMAIL, "dino@bovino.it")));
		entities.add(new AnnotatedPersonExample(new Fields(
				AnnotatedPersonExample.EMAIL, "dino@bovino.com")));
		entities.add(new AnnotatedPersonExample(new Fields(
				AnnotatedPersonExample.EMAIL, "dino@bovino.eu")));
		ArrayList<AnnotatedPersonExample> result = FilterBy.applyFilter(entities, filter);
		
		assertThat(result, is(equalTo(entities)));
		assertThat(filter.getCaptured(), is(equalTo(entities)));
        
    }
    
    @Ignore("don't what is the correct soluion")
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
