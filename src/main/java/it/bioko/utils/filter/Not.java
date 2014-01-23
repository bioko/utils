package it.bioko.utils.filter;

import it.bioko.utils.domain.DomainEntity;

public class Not<T extends DomainEntity> implements Filter<T>{
	
	private final Filter<T> _filter;
	
	public Not(Filter<T> filter) {
		_filter = filter;
	}

	@Override
	public boolean allows(T entity) {
		return !_filter.allows(entity);
	}

	public static final <T extends DomainEntity> Filter<T> not(Filter<T> filter) {
		return new Not<T>(filter);
	}
}
