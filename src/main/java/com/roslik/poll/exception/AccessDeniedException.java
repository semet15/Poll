package com.roslik.poll.exception;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(Integer id) {
        super("Access is denied to disable poll " + id);
    }
}
