package it.bioko.utils.domain.reflection;

import it.bioko.utils.domain.DomainEntity;
import it.bioko.utils.fields.Fields;

import java.lang.reflect.Method;

public class DomainEntityReflection {

	public static <T extends DomainEntity> String keyNameInvocation(Class<T> entityClass) {
		DomainEntity entity;
		String entityKeyName = null;
		try {
			entity = (DomainEntity)Class.forName(entityClass.getName()).getConstructor(Fields.class).newInstance(Fields.empty());
			Method keyNameMethod = entity.getClass().getDeclaredMethod("keyName");
			entityKeyName = (String)keyNameMethod.invoke(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityKeyName;
	}

	public static <T extends DomainEntity> Object domainEntityBuilderInstance(Class<T> aClass) {
		Object result = null;
		try {
			return Class.forName(aClass.getName() + "Builder").getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
