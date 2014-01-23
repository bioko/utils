package it.bioko.utils.matcher;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class EqualToStream extends TypeSafeMatcher<InputStream> {

	private final InputStream _expectedStream;

	public EqualToStream(InputStream expectedStream) {
		_expectedStream = expectedStream;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("a stream");
	}

	@Override
	protected boolean matchesSafely(InputStream actualStream) {
		try {
			boolean equals = IOUtils.contentEquals(_expectedStream, actualStream);
			_expectedStream.close();
			actualStream.close();
			return equals;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void describeMismatchSafely(InputStream actualStream, Description mismatch) {
		mismatch.appendText("was an other stream");
	}
	
	public static Matcher<InputStream> equalToStream(InputStream stream) {
		return new EqualToStream(stream);
	}

}
