package it.bioko.utils.fields;

public class Key {

	private static final String NAME_TYPE_SEPARATOR = "^";

	public static String create(String aFieldName, String aFieldType) {
		return new StringBuilder(aFieldName).append(NAME_TYPE_SEPARATOR).append(aFieldType).toString();
	}
	
	public static boolean isSatisfied(String aPossibleKey) {
		return aPossibleKey.contains(NAME_TYPE_SEPARATOR);
	}
}