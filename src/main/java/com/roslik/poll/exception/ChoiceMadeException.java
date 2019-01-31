package com.roslik.poll.exception;

public class ChoiceMadeException extends RuntimeException{

    public ChoiceMadeException(Integer optionId) {
        super("Option " + optionId + " is chose already");
    }
}
