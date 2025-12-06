package com.petproject.workflow.api.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final Instant timestamp;
}
