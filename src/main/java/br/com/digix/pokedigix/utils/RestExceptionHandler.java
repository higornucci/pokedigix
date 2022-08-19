package br.com.digix.pokedigix.utils;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> getExceptionResponseEntity(final Exception exception,
            final HttpStatus status,
            final WebRequest request) {
        final Map<String, Object> body = new LinkedHashMap<>();
        final String path = request.getDescription(false);
        body.put("timestamp", Instant.now());
        body.put("path", path);
        body.put("status", status.value());
        body.put("error", exception.getClass());
        body.put("message", exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }
}
