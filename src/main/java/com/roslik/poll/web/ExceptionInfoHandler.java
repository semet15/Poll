package com.roslik.poll.web;

import com.roslik.poll.exception.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ExceptionInfoHandler {

    @ResponseBody
    @ExceptionHandler(PollNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String pollNotFoundHandler(PollNotFoundException ex) {
        log.info(ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(OptionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String optionNotFoundHandler(OptionNotFoundException ex) {
        log.info(ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ChoiceMadeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String choiceMadeExceptionHandler(ChoiceMadeException ex) {
        log.info(ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDeniedHandler(AccessDeniedException ex) {
        log.info(ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(FinishedPollException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String finishedPollExceptionHandler(FinishedPollException ex) {
        log.info(ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String constraintViolationHandler(MethodArgumentNotValidException ex) {
        log.info(ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Exception ex) {
        log.warn(ex);
        return ex.getMessage();
    }
}
