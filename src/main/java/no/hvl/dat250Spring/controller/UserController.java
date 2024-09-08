package no.hvl.dat250Spring.controller;

import no.hvl.dat250Spring.model.PollManager;
import no.hvl.dat250Spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/pollapp")
public class UserController {
    @Autowired
    private PollManager pollManager;

    @GetMapping("/user")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok(pollManager.getUsers());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Object> displayUser(@PathVariable String username) {
        Optional<User> optUser = pollManager.getUser(username);
        if(optUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not exist");

        User user = optUser.get();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if(pollManager.getUser(user.getUsername()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username taken");

        pollManager.addUser(user);

        return ResponseEntity.created(URI.create("/user/" + user.getUsername())).body(user);
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable String username, @RequestBody User user) {
        Optional<User> optUser = pollManager.getUser(username);
        if(optUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");

        User thisUser = optUser.get();
        thisUser.setUsername(user.getUsername());
        thisUser.setEmail(user.getEmail());

        return ResponseEntity.ok(thisUser);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username) {
        if(!pollManager.getUsers().containsKey(username))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not exist");

        pollManager.getUsers().remove(username);
        return ResponseEntity.noContent().build();
    }

}
