package org.naveen.maven.research.annotations;

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
