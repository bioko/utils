package it.bioko.utils.matcher;

import static org.hamcrest.Matchers.anything;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class AnyString extends TypeSafeMatcher<String> {

	private static final Matcher<Object> _anything = anything();
	
	@Override
	public void describeTo(Description description) {
		_anything.describeTo(description);
	}

	@Override
	protected boolean matchesSafely(String string) {
		return _anything.matches(string);
	}
	
	public static final Matcher<String> anyString() {
		return new AnyString();
	}

}
