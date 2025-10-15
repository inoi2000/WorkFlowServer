package com.petproject.workflow.api.exceptions;

public class InvalidTimeException extends Exception {

    public InvalidTimeException() {
        super();
    }

    public InvalidTimeException(String message) {
        super(message);
    }

    public InvalidTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTimeException(Throwable cause) {
        super(cause);
    }
}
