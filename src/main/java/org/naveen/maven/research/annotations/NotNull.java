package org.naveen.maven.research.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NotNull {
	String message() default "Parameter value is not nullable.";
	NotNullPolicy policy() default NotNullPolicy.THROW_EXCEPTION;
	Class<? extends Throwable> exception() default IllegalArgumentException.class;
}
