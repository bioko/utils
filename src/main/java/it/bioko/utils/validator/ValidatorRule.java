package it.bioko.utils.validator;

public class ValidatorRule {

	private Class<?> 	_type;
	private boolean _mandatory;
	private String 	_pattern;
	private String 	_format;
	private String _dateFormat;
	

	public ValidatorRule(Class<?> type, boolean mandatory, String pattern, String format) {
		_type = type;
		_mandatory = mandatory;
		_pattern = pattern;
		_format = format;
	}


	public Class<?> getType() {
		return _type;
	}


	public void setType(Class<?> type) {
		_type = type;
	}


	public boolean isMandatory() {
		return _mandatory;
	}


	public void setMandatory(boolean mandatory) {
		_mandatory = mandatory;
	}


	public String getPattern() {
		return _pattern;
	}


	public void setPattern(String pattern) {
		_pattern = pattern;
	}


	public String getFormat() {
		return _format;
	}


	public void setFormat(String format) {
		_format = format;
	}


	public String getDateFormat() {
		return _dateFormat;
	}


	public void setDateFormat(String dateFormat) {
		_dateFormat = dateFormat;
	}
	
	
	
	
	
	
}
