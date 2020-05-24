package com.kuntapong.employee.exceptionhandler;

import com.kuntapong.employee.service.exception.AuthServiceLoginFailException;
import com.kuntapong.employee.service.exception.EmployeeServiceNotfoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class EmployeeControllerAdvice {
    protected static final Logger log = LoggerFactory.getLogger(EmployeeControllerAdvice.class);

    @ExceptionHandler(EmployeeServiceNotfoundException.class)
    public ResponseEntity<String> notFoundException(final EmployeeServiceNotfoundException e) {
        log.warn("Error not found({})", e.getMessage());

        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthServiceLoginFailException.class)
    public ResponseEntity<String> loginFailException(final AuthServiceLoginFailException e) {
        log.warn("Login fail({})", e.getMessage());

        return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> wrongRequestTypeException(final MethodArgumentTypeMismatchException e) {
        log.warn("Error Invalid request type({})", e.getMessage());

        return new ResponseEntity<String>("The parameter is invalid.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> general(final Exception e) {
        log.error("Error({})", e.getMessage());

        return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
