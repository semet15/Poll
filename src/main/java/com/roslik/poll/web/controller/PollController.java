package com.roslik.poll.web.controller;

import com.roslik.poll.exception.PollNotFoundException;
import com.roslik.poll.model.Option;
import com.roslik.poll.model.Poll;
import com.roslik.poll.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/polls", produces = MediaType.APPLICATION_JSON_VALUE)
public class PollController {

    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<Poll>> createPoll(@RequestBody @Valid Poll poll, HttpServletRequest request) {

        Poll createdPoll = pollService.create(poll, request.getRemoteAddr());
        Resource<Poll> resource = new Resource<>(createdPoll,
                linkTo(methodOn(PollController.class).getPoll(createdPoll.getId())).withRel("poll"));

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void disablePoll(@PathVariable("id") Integer id, HttpServletRequest request) {
        pollService.disable(id, request.getRemoteAddr());
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Resource<Poll>>> getPolls() {

        List<Resource<Poll>> list =  pollService.findAll().stream()
                .map(poll -> new Resource<>(poll,
                        linkTo(methodOn(PollController.class).getPoll(poll.getId())).withSelfRel(),
                        linkTo(methodOn(PollController.class).pollStats(poll.getId())).withRel("stats")))
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping(value ="/{id}")
    public ResponseEntity<Resource<Poll>> getPoll(@PathVariable("id") Integer id) {

        Poll poll = pollService.findById(id).orElseThrow(() -> new PollNotFoundException(id));
        Resource<Poll> resource = new Resource<>(poll,
                    linkTo(methodOn(PollController.class).getPoll(id)).withSelfRel(),
                    linkTo(methodOn(PollController.class).pollStats(id)).withRel("stats"));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping(value ="/{id}/stats")
    public ResponseEntity<Map<Option, Integer>> pollStats(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(pollService.getStats(id), HttpStatus.OK);
    }


    @PostMapping(value ="/{pollId}/options/{optionId}")
    @ResponseStatus(HttpStatus.OK)
    public void voteForOption(@PathVariable("pollId") Integer pollId,
                              @PathVariable("optionId") Integer optionId,
                              HttpServletRequest request) {
        pollService.vote(pollId, optionId, request.getRemoteAddr());
    }
}

