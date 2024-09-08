package no.hvl.dat250Spring.controller;

import no.hvl.dat250Spring.model.Poll;
import no.hvl.dat250Spring.model.PollManager;
import no.hvl.dat250Spring.model.VoteOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pollapp")
public class VoteOptionController {
    @Autowired
    private PollManager pollManager;

    @GetMapping("/voteoptions/{pollId}")
    public ResponseEntity<Object> getVoteOptions(@PathVariable int pollId) {
        Optional<Poll> optPoll = pollManager.getPoll(pollId);
        if(optPoll.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Poll does not exist");

        Poll poll = optPoll.get();
        return ResponseEntity.ok(poll.getOptions());
    }

    @PutMapping("/voteoptions/{pollId}")
    public ResponseEntity<Object> updateVoteOptions(@PathVariable int pollId, @RequestBody List<VoteOption> options) {
        Optional<Poll> optPoll = pollManager.getPoll(pollId);
        if(optPoll.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Poll does not exist");

        Poll poll = optPoll.get();
        poll.setOptions(options);

        return ResponseEntity.ok("Options changed");
    }
}
