package no.hvl.dat250Spring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class User {
    private String username;
    private String email;
    private List<Vote> votes;

    @JsonManagedReference
    private List<Poll> createdPolls;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.votes = new ArrayList<Vote>();
        this.createdPolls = new ArrayList<Poll>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Poll> getCreatedPolls() {
        return createdPolls;
    }

    public void setCreatedPolls(List<Poll> createdPolls) {
        this.createdPolls = createdPolls;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Optional<Vote> getVote(int pollId) {
        return this.getVotes().stream()
                .filter(vote -> vote.getPollId() == pollId)
                .findAny();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdPolls=" + createdPolls +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(createdPolls, user.createdPolls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, createdPolls);
    }
}
