package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.UserRoleRepository;
import game.mightywarriors.data.tables.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class UserRoleApiController {
    @Autowired
    UserRoleRepository userRoleRepository;

    @GetMapping("userRoles")
    public LinkedList<UserRole> getUserRoles() {
        return userRoleRepository.findAll();
    }

    @GetMapping("userRoles/{id}")
    public UserRole getUserRole(@PathVariable("id") String id) {
        return userRoleRepository.findById(Long.parseLong(id));
    }
}
