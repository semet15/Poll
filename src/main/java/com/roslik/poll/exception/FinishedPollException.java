package com.roslik.poll.exception;

public class FinishedPollException extends RuntimeException{

    public FinishedPollException(Integer id) {
        super("Poll " + id + " is finished already");
    }
}
