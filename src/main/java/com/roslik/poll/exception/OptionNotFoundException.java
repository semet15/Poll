package com.roslik.poll.exception;

public class OptionNotFoundException extends RuntimeException{

    public OptionNotFoundException(Integer optionId, Integer pollId) {
        super("Could not find option " + optionId + " inside poll " + pollId);
    }
}
