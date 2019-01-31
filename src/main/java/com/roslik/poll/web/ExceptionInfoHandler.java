package com.roslik.poll.web;

import com.roslik.poll.exception.AccessDeniedException;
import com.roslik.poll.exception.ChoiceMadeException;
import com.roslik.poll.exception.OptionNotFoundException;
import com.roslik.poll.exception.PollNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionInfoHandler {

    @ResponseBody
    @ExceptionHandler(PollNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String pollNotFoundHandler(PollNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(OptionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String optionNotFoundHandler(OptionNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ChoiceMadeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String choiceMadeExceptionHandler(ChoiceMadeException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDeniedHandler(AccessDeniedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String constraintViolationHandler(ConstraintViolationException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Exception ex) {
        return ex.getMessage();
    }
}
