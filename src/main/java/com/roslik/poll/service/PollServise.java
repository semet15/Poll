package com.roslik.poll.service;

import com.roslik.poll.model.Poll;

import java.util.List;
import java.util.Optional;

public interface PollServise {

    Optional<Poll> findById(int id);

    List<Poll> findAll();

    Poll save(Poll poll);

    void delete(Poll poll);
}
