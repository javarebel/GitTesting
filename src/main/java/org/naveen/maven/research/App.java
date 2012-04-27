package org.naveen.maven.research;

import org.naveen.maven.research.annotations.NotNull;
import org.naveen.maven.research.annotations.NotNullPolicy;
import org.naveen.maven.research.exception.ParameterException;

/**
 * Hello world!
 * 
 */
public class App {
	
	public String test(@NotNull(exception=ParameterException.class) String name) {
		System.out.println("Name is " + name);
		return "Hello World " + name;
	}
	
	private String privateTest(@NotNull(policy=NotNullPolicy.RETURN_NULL) String message, 
							   @NotNull(policy=NotNullPolicy.RETURN_NULL_IMMEDIATE) String greet,
			                   @NotNull(policy=NotNullPolicy.RETURN_NULL, message="Name cannot be null.") String name) {
		System.out.println("Message received is " + message);
		return "Hello World " + name;
	}
	
	public String testing(int age, String name) {
		System.out.println("Testing Name is " + name);
		System.out.println("From private test ==> " + privateTest("Message", "HEllo", null));
		return "Hello World Testing " + name;
	}
	
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
