package store.lookup.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import store.lookup.ApplicationException;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandlingAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public Map<String, Object> handleApplicationException(ApplicationException ae) {
        log.error("INTERNAL_SERVER_ERROR", ae);
        return Map.of(
                "error", "INTERNAL_SERVER_ERROR",
                "message", "Service is temporarily unavailable, try again later"
        );
    }
}
