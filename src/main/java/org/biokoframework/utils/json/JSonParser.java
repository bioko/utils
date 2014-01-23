package org.biokoframework.utils.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

/*
 * Copyright (c) 2014																 
 *	Mikol Faro			<mikol.faro@gmail.com>
 *	Simone Mangano		<simone.mangano@ieee.org>
 *	Mattia Tortorelli	<mattia.tortorelli@gmail.com>
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

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