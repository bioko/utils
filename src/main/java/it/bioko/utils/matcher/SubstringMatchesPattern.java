package it.bioko.utils.matcher;


import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class SubstringMatchesPattern extends MatchesPattern {

	String _pattern;
	
	public SubstringMatchesPattern(String pattern) {
		super(".*" + pattern + ".*");
		_pattern = pattern;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("a substring with pattern /")
				.appendText(_pattern)
				.appendText("/");
	}
	
	@Factory
	public static Matcher<String> substringMatchesPattern(String pattern) {
		return new SubstringMatchesPattern(pattern);
	}

}
