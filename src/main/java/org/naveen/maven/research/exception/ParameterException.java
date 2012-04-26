package org.naveen.maven.research.exception;

public class ParameterException extends Exception {

	private static final long serialVersionUID = -2022790113135173225L;

	public ParameterException() {
		super();
	}

	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameterException(String message) {
		super(message);
	}

	public ParameterException(Throwable cause) {
		super(cause);
	}
}
