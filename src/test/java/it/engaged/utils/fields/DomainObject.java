package it.engaged.utils.fields;

public class DomainObject {

	private final String _body;
	private final String _email;

	public DomainObject(String body, String email) {
		_body = body;
		_email = email;
	}
	
	public String report() {
		return _email + " " + _body;
	}
}