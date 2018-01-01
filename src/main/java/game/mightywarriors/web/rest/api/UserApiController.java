package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.UserRepository;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Set;

@RestController
public class UserApiController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("users")
    public Set<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userRepository.findById(Long.parseLong(id));
    }
}
