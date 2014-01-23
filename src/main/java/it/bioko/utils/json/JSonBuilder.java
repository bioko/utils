package it.bioko.utils.json;

import it.bioko.utils.fields.Fields;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JSonBuilder {
	private JSONObject _jsonObject = new JSONObject();

	@SuppressWarnings("unchecked")
	private void add(String key, Object value) {
			_jsonObject.put(key, value);
	}
	
//	public void append(Set<Entry<String, Object>> entrySet) throws Exception {
//		for (Entry<String, Object> each : entrySet) {
//			add(each.getKey(), each.getValue());
//		}
//	}
	
	public String buildFrom(Fields someFields) {
		if (!someFields.isEmpty()) {
			for (String each : someFields.keys()) {
				Object value = someFields.valueFor(each);
				if (value != null)
					add(each, value);
				else
					System.out.println(each + " - is null" );
			}
		}
		// TODO verificare che non sia questo che cambia l'escaping
		return JSONValue.toJSONString(_jsonObject);
	}
}