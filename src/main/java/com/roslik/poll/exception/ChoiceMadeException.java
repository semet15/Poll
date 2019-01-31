package com.roslik.poll.exception;

public class ChoiceMadeException extends RuntimeException{

    public ChoiceMadeException() {
        super("Choice is made in this poll already");
    }
}
