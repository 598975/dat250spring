package no.hvl.dat250Spring.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PollManager {
    private HashMap<String, Poll> polls;
    private HashMap<String, User> users;

    public PollManager() {
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public void setPolls(HashMap<String, Poll> polls) {
        this.polls = polls;
    }

    public HashMap<String, Poll> getPolls() {
        return polls;
    }

}
