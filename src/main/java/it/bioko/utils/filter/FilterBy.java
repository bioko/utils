package it.bioko.utils.filter;

import it.bioko.utils.domain.DomainEntity;

import java.util.ArrayList;
import java.util.List;


public class FilterBy {

	public static <T extends DomainEntity, C extends List<T>> ArrayList<T> applyFilter(List<T> list, Filter<T> filter) {
		ArrayList<T> filteredSet = new ArrayList<T>();
		try {
			for (T anEntity : list) {
				if (filter.allows(anEntity)) {
					filteredSet.add(anEntity);
				}
			}
		} catch (Exception exception) {
			System.out.println("[easy-man]: exception" + exception.getMessage());
			exception.printStackTrace();
		}
		return filteredSet;
	}
	
}
