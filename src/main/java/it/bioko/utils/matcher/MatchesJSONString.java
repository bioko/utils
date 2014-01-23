package it.bioko.utils.matcher;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MatchesJSONString extends TypeSafeMatcher<String> {

	private String _expectedString;
	private Matcher<Object> equalTo;
	private Matcher<Iterable<? extends Object>> itarableEqualTo;
	

	public MatchesJSONString(String expectedString) {
		_expectedString = expectedString;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendValue(_expectedString);
	}

	@Override
	protected boolean matchesSafely(String actualString) {
		JSONParser parser = new JSONParser();
		
		try {
			Object jsonActual = parser.parse(actualString);
			Object jsonExpected = parser.parse(_expectedString);
			
			if (jsonActual instanceof JSONArray && jsonExpected instanceof JSONArray) {
				itarableEqualTo = containsInAnyOrder(((JSONArray)jsonExpected).toArray());
				return itarableEqualTo.matches(jsonActual);
			} else {
				equalTo = equalTo(jsonExpected);
				return equalTo.matches(jsonActual);
			}
		} catch (ParseException e) {
			return false;
		}
		
	}

	public static final Matcher<String> matchesJSONString(String expectedString) {
		return new MatchesJSONString(expectedString);
	}
}
