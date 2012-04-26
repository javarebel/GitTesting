package org.naveen.maven.research;

import org.naveen.maven.research.annotations.NotNull;
import org.naveen.maven.research.exception.ParameterException;

/**
 * Hello world!
 * 
 */
public class App {
	
	public String test(String message, @NotNull(exception=ParameterException.class) String name, int age) {
		System.out.println("Name is " + name);
		return "Hello World " + name;
	}
	
	public String testing(int age, String name) {
		System.out.println("Testing Name is " + name);
		return "Hello World Testing " + name;
	}
	
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
