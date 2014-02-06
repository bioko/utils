# Bioko utils

Core functionalities for the bioko framework

[![Build Status](https://travis-ci.org/bioko/utils.png?branch=dev)](https://travis-ci.org/bioko/utils)

##### Interesting features
	
 * Bioko actively DOES NOT support
  * the `int` and `float` Java base types, it prefers `java.lang.Long` and `java.lang.Double` respectively
  * the Java classes for time representation (e.g. `java.util.Date` and `java.util.Calendar`), 
  it uses [Joda-Time](http://www.joda.org/joda-time/) instead, at least until the release of Java8 and its JSR-310.
