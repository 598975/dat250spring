package no.hvl.dat250Spring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;

    @JsonManagedReference
    private List<Poll> createdPolls;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
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
}
