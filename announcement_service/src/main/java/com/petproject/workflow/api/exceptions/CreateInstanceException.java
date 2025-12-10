package com.petproject.workflow.api.exceptions;

public class CreateInstanceException extends Exception {

    public CreateInstanceException() {
        super();
    }

    public CreateInstanceException(String message) {
        super(message);
    }

    public CreateInstanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateInstanceException(Throwable cause) {
        super(cause);
    }
}
