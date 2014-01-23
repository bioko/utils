package it.bioko.utils.matcher;

import java.io.InputStream;

import org.hamcrest.Matcher;

public class Matchers {
	
	public static final Matcher<String> anyString() {
		return AnyString.anyString();
	}
	
	public static final Matcher<String> matchesJSONString(String expectedString) {
		return MatchesJSONString.matchesJSONString(expectedString);
	}
	
	public static Matcher<String> matchesPattern(String pattern) {
		return MatchesPattern.matchesPattern(pattern);
	}
	
	public static Matcher<String> substringMatchesPattern(String pattern) {
		return SubstringMatchesPattern.substringMatchesPattern(pattern);
	}

	public static Matcher<InputStream> equalToStream(InputStream stream) {
		return EqualToStream.equalToStream(stream);
	}
	
}
