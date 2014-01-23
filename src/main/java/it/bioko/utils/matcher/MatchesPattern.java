package it.bioko.utils.matcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class MatchesPattern extends TypeSafeMatcher<String>{

	private String _pattern;

	public MatchesPattern(String pattern) {
		_pattern = pattern;
	}

	@Override
	protected boolean matchesSafely(String item) {
		return item.matches(_pattern);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("match with pattern /")
				.appendText(_pattern)
				.appendText("/");
	}

    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("was \"").appendText(item).appendText("\"");
    }
	
	@Factory
	public static Matcher<String> matchesPattern(String pattern) {
		return new MatchesPattern(pattern);
	}

}
