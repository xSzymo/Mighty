package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class UserApiController {
    @Autowired
    private UserService service;

    @GetMapping("users")
    public Set<User> getUsers() {
        return service.findAll();
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }
}
