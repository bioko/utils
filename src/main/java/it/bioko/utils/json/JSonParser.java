package it.bioko.utils.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class JSonParser {

	private JsonFactory _jsonFactory;

	public JSonParser() {
		_jsonFactory = new JsonFactory();
	}
	
	public LinkedHashMap<String, Object> parseToMap(String json) {
		LinkedHashMap<String, Object> hashMap = new LinkedHashMap<String, Object>();
		JsonParser jsonParser;
		try {
			jsonParser = _jsonFactory.createJsonParser(json);
			jsonParser.nextToken();
			hashMap = expandObject(jsonParser);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hashMap;
	}
	
	private LinkedHashMap<String, Object> expandObject(JsonParser jsonParser) throws JsonParseException, IOException {
		LinkedHashMap<String, Object> hashMap = new LinkedHashMap<String, Object>();
		
		JsonToken token;
		while ((token = jsonParser.nextToken()) != JsonToken.END_OBJECT && token != null) {
			String currentName = jsonParser.getCurrentName();
			if (token.compareTo(JsonToken.START_OBJECT) == 0) {
				hashMap.put(currentName, expandObject(jsonParser));
			} else if (token.compareTo(JsonToken.START_ARRAY) == 0) {				
				ArrayList<Object> list = new ArrayList<Object>();
				while ((token = jsonParser.nextToken()) != JsonToken.END_ARRAY && token != null) {
					if (token == JsonToken.START_OBJECT) {
						list.add(expandObject(jsonParser));
					} else {
						list.add(jsonParser.getText());
					}
				}
				hashMap.put(currentName, list);
			} else {
				hashMap.put(currentName, jsonParser.getText());
			}
		}
		
		return hashMap;
	}
}