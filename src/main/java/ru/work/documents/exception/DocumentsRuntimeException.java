package ru.work.documents.exception;

import org.springframework.http.HttpStatus;

public class DocumentsRuntimeException extends RuntimeException {

	private final HttpStatus status;

	public DocumentsRuntimeException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public DocumentsRuntimeException(String message, HttpStatus status, Throwable cause) {
		super(message, cause);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
