package com.green.house.login.advice;

import com.green.house.login.controller.LoginController;
import com.green.house.login.exception.ErrorCodes;
import com.green.house.login.exception.UserLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice(assignableTypes = {LoginController.class})
public class LoginControllerAdvice {
	
	@ExceptionHandler(value = {UserLoginException.class})
    public ResponseEntity<Object> handleCacheException(UserLoginException ex) {
        log.error("user login exception: ", ex.getMessage());
        if(ErrorCodes.INVALID_LOGIN == ex.getErrorCodes()) {
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleArgumentMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Invalid types: ", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
