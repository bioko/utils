package it.bioko.utils.filter;

import it.bioko.utils.domain.DomainEntity;

import org.apache.commons.lang3.StringUtils;

public class HasForeignKeyWithValue<DE extends DomainEntity> implements Filter<DE> {

	private final String _fieldName;
	private final String _fieldValue;

	public HasForeignKeyWithValue(String fieldName, String fieldValue) {
		_fieldName = fieldName;
		_fieldValue = fieldValue;
	}

	@Override
	public boolean allows(DE entity) {
		if (entity == null) {
			return false;
		}
		
		return StringUtils.equals(_fieldValue, entity.get(_fieldName));
	}
	
}
