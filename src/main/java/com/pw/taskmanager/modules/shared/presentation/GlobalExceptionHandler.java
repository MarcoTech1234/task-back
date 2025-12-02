package com.pw.taskmanager.modules.shared.presentation;

import com.pw.taskmanager.modules.shared.errors.ApplicationException;
import com.pw.taskmanager.modules.shared.errors.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationError(ApplicationException ex, HttpServletRequest request) {
        String requestId = UUID.randomUUID().toString();
        log.warn("ApplicationException requestId={} message={}", requestId, ex.getMessage());

        HttpStatus status = HttpStatus.resolve(ex.getStatusCode());
        String error = status != null ? status.getReasonPhrase() : "Unknown Error";

        ErrorResponse body = new ErrorResponse(
                requestId,
                OffsetDateTime.now(),
                ex.getStatusCode(),
                error,
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }


}
