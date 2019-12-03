package com.example.counter.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotFoundException.class})
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return getErrorInfo(req, e);
    }

    private static ErrorInfo getErrorInfo(HttpServletRequest req, Throwable e) {
        return new ErrorInfo(req.getRequestURL(), "ERROR", e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName());
    }
}
