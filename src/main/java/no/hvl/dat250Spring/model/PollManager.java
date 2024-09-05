package no.hvl.dat250Spring.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PollManager {
    private HashMap<Integer, Poll> polls;
    private HashMap<String, User> users;

    public PollManager() {
        this.polls = new HashMap<Integer, Poll>();
        this.users = new HashMap<String, User>();
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public void setPolls(HashMap<Integer, Poll> polls) {
        this.polls = polls;
    }

    public HashMap<Integer, Poll> getPolls() {
        return polls;
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void removeUser(User user) {
        users.remove(user.getUsername());
    }

    public void addPoll(Poll poll) {
        polls.put(poll.getID(), poll);
    }
}
