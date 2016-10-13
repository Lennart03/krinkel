package com.realdolmen.chiro.exception;

public class EmailSenderServiceException extends RuntimeException {

	public EmailSenderServiceException() {
	}

	public EmailSenderServiceException(String msg) {
		super(msg);
	}

	public EmailSenderServiceException(Throwable cause) {
		super(cause);
	}

	public EmailSenderServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public EmailSenderServiceException(String msg, Throwable cause, boolean arg2, boolean arg3) {
		super(msg, cause, arg2, arg3);
	}

}
