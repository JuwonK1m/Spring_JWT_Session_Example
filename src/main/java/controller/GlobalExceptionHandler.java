package controller;

import exception.ConflictException;
import exception.UnauthorizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UnauthorizeException.class)
    public ResponseEntity<Map<String, Object>> unauthorizeException(UnauthorizeException e) {
        return new ResponseEntity<Map<String, Object>>(e.getMap(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Map<String, Object>> conflictException(ConflictException e) {
        return new ResponseEntity<Map<String, Object>>(e.getMap(), HttpStatus.CONFLICT);
    }
}
