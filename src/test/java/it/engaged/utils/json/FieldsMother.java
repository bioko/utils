package it.engaged.utils.json;

import it.bioko.utils.fields.FieldNames;
import it.bioko.utils.fields.Fields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FieldsMother {

	public static final String FIRST_EXPECTED = "{\"" + FieldNames.NAME + "\":\"" + FieldNames.NAME_VALUE + "\",\"" + FieldNames.SURNAME + "\":\"" + FieldNames.SURNAME_VALUE + "\"}";
	public static final String FIRST_AND_SECOND_EXPECTED = "{\"" + FieldNames.NAME + "\":\"" + FieldNames.NAME_VALUE + "\",\"" + FieldNames.SURNAME + "\":\"" + FieldNames.SURNAME_VALUE + "\"," +
															"\"" + FieldNames.EMAIL + "\":\"" + FieldNames.EMAIL_VALUE + "\",\"" + FieldNames.AREA + "\":\"" + FieldNames.AREA_VALUE + "\"}";
	
	public static Fields twoFields() {
		Fields field = Fields.fromRow(
				FieldNames.NAME + Fields.KEY_VALUE_SEPARATOR + FieldNames.NAME_VALUE + Fields.FIELDS_SEPARATOR +
				FieldNames.SURNAME + Fields.KEY_VALUE_SEPARATOR + FieldNames.SURNAME_VALUE + Fields.FIELDS_SEPARATOR
				);
		return field;
	}
	
	public static Fields fourFields() {
		Fields field = Fields.fromRow(
				FieldNames.NAME + Fields.KEY_VALUE_SEPARATOR + FieldNames.NAME_VALUE + Fields.FIELDS_SEPARATOR +
				FieldNames.SURNAME + Fields.KEY_VALUE_SEPARATOR + FieldNames.SURNAME_VALUE + Fields.FIELDS_SEPARATOR +
				FieldNames.EMAIL + Fields.KEY_VALUE_SEPARATOR + FieldNames.EMAIL_VALUE + Fields.FIELDS_SEPARATOR +
				FieldNames.AREA + Fields.KEY_VALUE_SEPARATOR + FieldNames.AREA_VALUE + Fields.FIELDS_SEPARATOR
				);
		return field;
	}

	public static Fields fieldsWithList() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("First");
		list.add("Second");
		return Fields.single("list", list);
	}

	public static Fields fieldsWithMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		return Fields.single("object", map );
	}
}
