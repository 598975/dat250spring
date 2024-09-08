package no.hvl.dat250Spring.controller;

import no.hvl.dat250Spring.model.Poll;
import no.hvl.dat250Spring.model.PollManager;
import no.hvl.dat250Spring.model.User;
import no.hvl.dat250Spring.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pollapp")
public class VoteController {
    @Autowired
    private PollManager pollManager;

    @GetMapping("/votes/{username}")
    public ResponseEntity<Object> displayUserVotes(@PathVariable String username) {
        Optional<User> user = pollManager.getUser(username);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not exist");
        if(user.get().getVotes().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No votes");

        return ResponseEntity.ok(user.get().getVotes());
    }

    @PostMapping("/votes/{username}")
    public ResponseEntity<Object> voteOnPoll(@PathVariable String username, @RequestBody Vote vote) {
        Optional<User> optUser = pollManager.getUser(username);
        if(optUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not exist");
        if(!pollManager.getPolls().containsKey(vote.getPollId()))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Poll does not exist");

        User user = optUser.get();
        user.getVotes().add(vote);

        return ResponseEntity.ok("Vote casted");
    }

    @PutMapping("/votes/{username}")
    public ResponseEntity<Object> changeVote(@PathVariable String username, @RequestBody Vote vote) {
        Optional<User> optUser = pollManager.getUser(username);
        if(optUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not exist");

        User user = optUser.get();
        Optional<Vote> optOriginalVote = user.getVote(vote.getPollId());

        if(optOriginalVote.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vote does not exist");

        Vote originalVote = optOriginalVote.get();
        originalVote.setOption(vote.getOption());
        originalVote.setPublishedAt(vote.getPublishedAt());

        return ResponseEntity.ok("Vote changed");
    }

    @DeleteMapping("/votes/{username}")
    public ResponseEntity<Object> deleteVote(@PathVariable String username, @RequestBody Vote vote) {
        Optional<User> optUser = pollManager.getUser(username);
        if(optUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not exist");

        User user = optUser.get();
        Optional<Vote> optOriginalVote = user.getVote(vote.getPollId());

        if(optOriginalVote.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vote does not exist");

        Vote originalVote = optOriginalVote.get();
        user.getVotes().remove(originalVote);

        return ResponseEntity.noContent().build();
    }

}
