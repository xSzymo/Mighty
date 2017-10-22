package game.mightywarriors.web.rest;


import game.mightywarriors.data.repositories.UserRepository;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class Helo {
    @Autowired
    UserRepository userRepository;

    @GetMapping("user")
    public LinkedList<User> halo() {
        return userRepository.findAll();
    }

    @GetMapping("user/{id}")
    public User halo1(@PathVariable("id") String id) {
        return userRepository.findById(Long.parseLong(id));
    }


}