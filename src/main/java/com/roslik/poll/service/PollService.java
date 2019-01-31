package com.roslik.poll.service;

import com.roslik.poll.model.Poll;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PollService {

    Optional<Poll> findById(int id);

    List<Poll> findAll();

    Poll create(Poll poll, String ip);

    void disable(Integer id, String ip);

    void vote(Integer pollId, Integer optionId, String ip);

    Map<Integer, Integer> getStats(Integer id);
}
