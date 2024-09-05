package no.hvl.dat250Spring.controller;

import no.hvl.dat250Spring.model.Poll;
import no.hvl.dat250Spring.model.PollManager;
import no.hvl.dat250Spring.model.User;
import no.hvl.dat250Spring.model.Vote;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pollapp")
public class PollController {
    private final PollManager pollManager = new PollManager();

    @GetMapping()
    public String index() {
        return "Hello World";
    }

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if(pollManager.getUsers().containsKey(user.getUsername()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username taken");

        pollManager.addUser(user);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/polls")
    public ResponseEntity<Object> displayAllPolls() {
        if(pollManager.getPolls().isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(pollManager.getPolls());
    }

    @PostMapping("/polls")
    public ResponseEntity<Object> createPoll(@RequestBody Poll poll) {

        pollManager.addPoll(poll);

        URI newPath = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pollId}").buildAndExpand(poll.getID()).toUri();

        return ResponseEntity.ok().body(poll);
    }

    @GetMapping("/polls/{pollId}")
    public ResponseEntity<Object> displayPoll(@PathVariable int pollId) {
        String pollDetails = "Details for given poll: " + pollManager.getPolls().get(pollId);

        return ResponseEntity.ok().body(pollDetails);
    }

    @PutMapping("/polls/{pollId}")
    public ResponseEntity<Object> voteOnPoll(@PathVariable int pollId, @RequestBody Vote vote) {
        pollManager.getPolls().get(pollId).addVote(vote);

        return ResponseEntity.ok().body("Vote casted");
    }

    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<Object> deletePoll(@PathVariable int pollId) {
        pollManager.getPolls().remove(pollId);
        return ResponseEntity.ok().body("Poll deleted");
    }
}
