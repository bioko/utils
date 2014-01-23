package it.bioko.utils.filter;

import it.bioko.utils.domain.DomainEntity;

public interface Filter<T extends DomainEntity> {

	public boolean allows(T entity); 
	
}
