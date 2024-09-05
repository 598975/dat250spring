package no.hvl.dat250Spring.model;

import java.time.Instant;

public class Vote {
    private Instant publishedAt;
    private String voter;
    private VoteOption option;

    public Vote(VoteOption option, String voter) {
        this.option = option;
        this.publishedAt = Instant.now();
        this.voter = voter;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public VoteOption getOption() {
        return option;
    }

    public void setOption(VoteOption option) {
        this.option = option;
    }
}
