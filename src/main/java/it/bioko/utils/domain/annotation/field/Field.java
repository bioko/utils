package it.bioko.utils.domain.annotation.field;

import it.bioko.utils.domain.annotation.hint.Hint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
	String name() default "";
	Class<?> type() default String.class;
	boolean mandatory() default true;
	Hint[] hints() default {};	
	// TODO trasform to @Hints
	String format() default "";
	String pattern() default "";
	String dateFormat() default "";
	
}
