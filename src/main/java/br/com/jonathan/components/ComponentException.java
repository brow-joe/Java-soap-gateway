package br.com.jonathan.components;

public class ComponentException extends Exception {
	private static final long serialVersionUID = 1L;

	public ComponentException() {
		super();
	}

	public ComponentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ComponentException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComponentException(String message) {
		super(message);
	}

	public ComponentException(Throwable cause) {
		super(cause);
	}

}