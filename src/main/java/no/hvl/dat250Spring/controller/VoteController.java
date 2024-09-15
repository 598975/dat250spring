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
@CrossOrigin
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

    @GetMapping("/votes/{pollId}")
    public ResponseEntity<Object> displayPollVotes(@PathVariable int pollId) {
        Optional<Poll> poll = pollManager.getPoll(pollId);
        if(poll.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Poll does not exist");
        if(poll.get().getVotes().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No votes");

        return ResponseEntity.ok(poll.get().getVotes());
    }

    @PostMapping("/votes/{username}")
    public ResponseEntity<Object> voteOnPoll(@PathVariable String username, @RequestBody Vote vote) {
        Optional<User> optUser = pollManager.getUser(username);
        Optional<Poll> optPoll = pollManager.getPoll(vote.getPollId());
        if(optUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not exist");
        if(optPoll.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Poll does not exist");

        User user = optUser.get();
        user.getVotes().add(vote);

        Poll poll = optPoll.get();
        poll.getVotes().add(vote);

        return ResponseEntity.ok("Vote casted");
    }

    @PutMapping("/votes/{username}")
    public ResponseEntity<Object> changeVote(@PathVariable String username, @RequestBody Vote vote) {
        Optional<User> optUser = pollManager.getUser(username);
        Optional<Poll> optPoll = pollManager.getPoll(vote.getPollId());
        if(optUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not exist");

        User user = optUser.get();
        Optional<Vote> optOriginalVoteUser = user.getVote(vote.getPollId());

        if(optOriginalVoteUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vote does not exist");

        Poll poll = optPoll.get();
        Optional<Vote> optOriginalVotePoll = poll.getVote(vote.getVoter().getUsername());

        Vote originalVoteUser = optOriginalVoteUser.get();
        Vote originalVotePoll = optOriginalVotePoll.get();

        originalVoteUser.setOption(vote.getOption());
        originalVotePoll.setOption(vote.getOption());
        originalVoteUser.setPublishedAt(vote.getPublishedAt());
        originalVotePoll.setPublishedAt(vote.getPublishedAt());

        return ResponseEntity.ok("Vote changed");
    }

    @DeleteMapping("/votes/{username}")
    public ResponseEntity<Object> deleteVote(@PathVariable String username, @RequestBody Vote vote) {
        Optional<User> optUser = pollManager.getUser(username);
        Optional<Poll> optPoll = pollManager.getPoll(vote.getPollId());
        if(optUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not exist");

        User user = optUser.get();
        Optional<Vote> optOriginalVoteUser = user.getVote(vote.getPollId());

        if(optOriginalVoteUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vote does not exist");

        Poll poll = optPoll.get();
        Optional<Vote> optOriginalVotePoll = poll.getVote(vote.getVoter().getUsername());

        Vote originalVoteUser = optOriginalVoteUser.get();
        Vote originalVotePoll = optOriginalVotePoll.get();
        user.getVotes().remove(originalVoteUser);
        poll.getVotes().remove(originalVotePoll);

        return ResponseEntity.noContent().build();
    }

}
