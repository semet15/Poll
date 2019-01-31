package com.roslik.poll.exception;

public class PollNotFoundException extends RuntimeException{

    public PollNotFoundException(Integer id) {
        super("Could not find poll " + id);
    }
}
