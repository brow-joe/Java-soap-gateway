package br.com.jonathan.soap.parse;

public class ParserRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ParserRuntimeException() {
		super();
	}

	public ParserRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParserRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserRuntimeException(String message) {
		super(message);
	}

	public ParserRuntimeException(Throwable cause) {
		super(cause);
	}

}