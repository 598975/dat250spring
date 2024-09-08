package no.hvl.dat250Spring.controller;

import no.hvl.dat250Spring.model.Poll;
import no.hvl.dat250Spring.model.PollManager;
import no.hvl.dat250Spring.model.User;
import no.hvl.dat250Spring.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pollapp")
public class PollController {
    @Autowired
    private PollManager pollManager;

    @GetMapping()
    public String index() {
        return "Hello World";
    }

    @GetMapping("/polls")
    public ResponseEntity<Object> displayAllPolls() {
        if(pollManager.getPolls().isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(pollManager.getPolls());
    }

    @GetMapping("/polls/{pollId}")
    public ResponseEntity<Object> displayPoll(@PathVariable int pollId) {
        Optional<Poll> poll = pollManager.getPoll(pollId);
        if(poll.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll does not exist");

        return ResponseEntity.ok(poll.get());
    }

    @PostMapping("/polls")
    public ResponseEntity<Object> createPoll(@RequestBody Poll poll) {
        Optional<User> creatorOpt = pollManager.getUser(poll.getPollCreator().getUsername());

        if(creatorOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");

        User creator = creatorOpt.get();

        pollManager.addPoll(poll);

        creator.getCreatedPolls().add(poll);

        return ResponseEntity.created(URI.create("/user/" + poll.getID())).body(poll);
    }

    @PutMapping("polls/{pollId}")
    public ResponseEntity<Object> updatePoll(@PathVariable int pollId, @RequestBody Poll poll) {
        Optional<Poll> optPoll = pollManager.getPoll(pollId);
        Optional<User> optCreator = pollManager.getUser(poll.getPollCreator().getUsername());

        if(optPoll.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll does not exist");
        if(optCreator.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");

        Poll thisPoll = optPoll.get();
        thisPoll.setPollCreator(optCreator.get());
        thisPoll.setOptions(poll.getOptions());
        thisPoll.setQuestion(poll.getQuestion());
        thisPoll.setValidUntil(poll.getValidUntil());

        return ResponseEntity.ok(thisPoll);
    }

    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<Object> deletePoll(@PathVariable int pollId) {
        if(!pollManager.getPolls().containsKey(pollId)) {
            return ResponseEntity.notFound().build();
        }

        for(Map.Entry<String, User> e : pollManager.getUsers().entrySet()) {
            Optional<Vote> optVote = e.getValue().getVote(pollId);
            if (optVote.isPresent()) {
                Vote vote = optVote.get();
                e.getValue().getVotes().remove(vote);
            }
        }

        pollManager.getPolls().remove(pollId);
        return ResponseEntity.noContent().build();
    }

}
