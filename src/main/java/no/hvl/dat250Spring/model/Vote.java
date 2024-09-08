package no.hvl.dat250Spring.model;

import java.time.Instant;
import java.util.Objects;

public class Vote {
    private Instant publishedAt;
    private User voter;
    private VoteOption option;
    private int pollId;

    public Vote(VoteOption option, User voter, int pollId) {
        this.option = option;
        this.publishedAt = Instant.now();
        this.voter = voter;
        this.pollId = pollId;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public VoteOption getOption() {
        return option;
    }

    public void setOption(VoteOption option) {
        this.option = option;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(voter, vote.voter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publishedAt, voter, option, pollId);
    }
}
