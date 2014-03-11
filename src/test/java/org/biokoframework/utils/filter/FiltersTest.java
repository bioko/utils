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
