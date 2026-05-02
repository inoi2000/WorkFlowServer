package com.petproject.workflow.api.exceptions;

public class NotFoundIdException extends Exception {

    public NotFoundIdException() {
        super();
    }

    public NotFoundIdException(String message) {
        super(message);
    }

    public NotFoundIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundIdException(Throwable cause) {
        super(cause);
    }
}
