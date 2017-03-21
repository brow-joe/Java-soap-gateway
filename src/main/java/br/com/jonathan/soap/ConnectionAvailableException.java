package br.com.jonathan.soap;

public class ConnectionAvailableException extends Exception {
	private static final long serialVersionUID = 1L;

	public ConnectionAvailableException() {
		super();
	}

	public ConnectionAvailableException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConnectionAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionAvailableException(String message) {
		super(message);
	}

	public ConnectionAvailableException(Throwable cause) {
		super(cause);
	}

}