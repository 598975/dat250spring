package no.hvl.dat250Spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Poll {
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private int ID;
    private List<VoteOption> options;

    @JsonBackReference
    private User pollCreator;

    public Poll(String question, Instant validUntil, List<VoteOption> options, User pollCreator) {
        this.question = question;
        this.validUntil = validUntil;
        this.options = options;
        this.publishedAt = Instant.now();
        this.ID = idCounter.incrementAndGet();
        this.pollCreator = pollCreator;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<VoteOption> getOptions() {
        return options;
    }

    public void setOptions(List<VoteOption> options) {
        this.options = options;
    }

    public User getPollCreator() {
        return pollCreator;
    }

    public void setPollCreator(User pollCreator) {
        this.pollCreator = pollCreator;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "question='" + question + '\'' +
                ", publishedAt=" + publishedAt +
                ", validUntil=" + validUntil +
                ", ID=" + ID +
                ", options=" + options +
                ", pollCreator=" + pollCreator +
                '}';
    }
}
