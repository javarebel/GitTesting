package org.naveen.maven.research.annotations;

/**
 * How to handle null arguments.
 * 
 * @author <a href='mailto:naveensisupalan@yahoo.com'>Naveen Sisupalan</a>
 *
 */
public enum NotNullPolicy {
	/** Irrespective of method body, method will return null if all arguments with this policy is null. */
	RETURN_NULL, 
	/** Will throw an exception. [Default] */
	THROW_EXCEPTION, 
	/** Don't do anything and will continue with method body */
	CONTINUE,
	/** Irrespective of method body, method will return null. */
	RETURN_NULL_IMMEDIATE
}
