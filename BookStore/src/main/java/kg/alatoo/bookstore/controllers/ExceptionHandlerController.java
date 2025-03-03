package kg.alatoo.bookstore.controllers;

import kg.alatoo.bookstore.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFoundException(NotFoundException ex) {
        log.debug("Handling NotFoundException");
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", System.currentTimeMillis());
        responseBody.put("message", ex.getMessage());
        responseBody.put("status", HttpStatus.NOT_FOUND);
        return responseBody;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBindingError(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getBindingResult().getAllErrors().stream().map(
                        err -> {
                            Map<String,String> res= new HashMap<>();
                            res.put(err.getCode(),err.getDefaultMessage());
                            return res;
                        }
                ).toList());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDatabaseConstraintException(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        System.out.println(ex.getMessage());
        return ResponseEntity.status(500).body(ex.getMessage());
    }


}
