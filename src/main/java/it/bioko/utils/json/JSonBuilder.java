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