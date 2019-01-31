package com.roslik.poll.service.impl;

import com.roslik.poll.exception.AccessDeniedException;
import com.roslik.poll.exception.ChoiceMadeException;
import com.roslik.poll.exception.OptionNotFoundException;
import com.roslik.poll.exception.PollNotFoundException;
import com.roslik.poll.model.Option;
import com.roslik.poll.model.Poll;
import com.roslik.poll.model.User;
import com.roslik.poll.repository.PollRepository;
import com.roslik.poll.repository.UserRepository;
import com.roslik.poll.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final UserRepository userRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

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
    public Poll create(Poll poll, String ip) {

        User owner = new User();
        Optional<User> user = userRepository.findByIp(ip);
        if (user.isPresent()) {
            owner = user.get();
        } else {
            owner.setIp(ip);
            owner = userRepository.save(owner);
        }

        poll.setOwner(owner);
        poll.setEnabled(true);
        poll.getOptions().forEach(o -> o.setPoll(poll));

        return pollRepository.save(poll);
    }

    @Override
    @Transactional
    public void disable(Integer id, String ip) {

        Poll poll = pollRepository.findById(id).orElseThrow(() -> new PollNotFoundException(id));

        if(!poll.getOwner().getIp().equals(ip)) {
            throw new AccessDeniedException(id);
        }

        poll.setEnabled(false);
        pollRepository.save(poll);
    }

    @Override
    @Transactional
    public void vote(Integer pollId, Integer optionId, String ip) {

        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new PollNotFoundException(pollId));

        Option option = poll.getOptions().stream()
                                        .filter(o -> o.getId() == optionId)
                                        .findAny()
                                        .orElseThrow(() ->  new OptionNotFoundException(optionId, pollId));

        if(option.getUsers().stream().anyMatch(u -> u.getIp().equals(ip))) {
            throw new ChoiceMadeException(optionId);
        } else {
            User user = null;
            Optional<User> userDb = userRepository.findByIp(ip);
            if(userDb.isPresent()) {
                 user = userDb.get();
                 user.getOptions().add(option);
            } else {
                user.setIp(ip);
                List<Option> options = new ArrayList<>();
                options.add(option);
                user.setOptions(options);
                user = userRepository.save(user);
            }

            option.getUsers().add(user);
            pollRepository.save(poll);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Map<Option, Integer> getStats(Integer id) {

        Poll poll = pollRepository.findById(id).orElseThrow(() -> new PollNotFoundException(id));

        return poll.getOptions().stream().collect(Collectors.toMap(opt -> opt, opt -> opt.getUsers().size()));
    }
}
