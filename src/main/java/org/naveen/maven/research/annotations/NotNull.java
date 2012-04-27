package org.naveen.maven.research.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark parameter which have to checked for null.
 * 
 * @author <a href='mailto:naveensisupalan@yahoo.com'>Naveen Sisupalan</a>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NotNull {
	/**
	 * Message set in the exception thrown when parameter is null.
	 */
	String message() default "Parameter value is not nullable.";
	
	/**
	 * Will specify how null values have to be treated. 
	 */
	NotNullPolicy policy() default NotNullPolicy.THROW_EXCEPTION;
	
	/**
	 * If you want to throw a custom exception. 
	 * By default this will throw an IllegalArgument Exception.
	 */
	Class<? extends Throwable> exception() default IllegalArgumentException.class;
}
