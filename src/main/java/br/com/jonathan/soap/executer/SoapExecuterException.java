package br.com.jonathan.soap.executer;

public class SoapExecuterException extends Exception {
	private static final long serialVersionUID = 1L;

	public SoapExecuterException() {
		super();
	}

	public SoapExecuterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SoapExecuterException(String message, Throwable cause) {
		super(message, cause);
	}

	public SoapExecuterException(String message) {
		super(message);
	}

	public SoapExecuterException(Throwable cause) {
		super(cause);
	}

}