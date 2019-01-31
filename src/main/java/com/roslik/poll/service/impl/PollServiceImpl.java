package com.roslik.poll.service.impl;

import com.roslik.poll.model.Poll;
import com.roslik.poll.repository.PollRepository;
import com.roslik.poll.service.PollServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollServise {

    @Autowired
    private PollRepository pollRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Poll> findById(int id) {
        return pollRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    @Override
    @Transactional
    public Poll save(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    @Transactional
    public void delete(Poll poll) {
        pollRepository.delete(poll);
    }
}
