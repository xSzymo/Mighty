package game.mightywarriors.web.rest;


import game.mightywarriors.data.repositories.UserRoleRepository;
import game.mightywarriors.data.tables.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class Helo3 {
    @Autowired
    UserRoleRepository imageRepository;

    @GetMapping("userRoles")
    public LinkedList<UserRole> halo() {
        return imageRepository.findAll();
    }

    @GetMapping("userRoles/{id}")
    public UserRole halo1(@PathVariable("id") String id) {
        return imageRepository.findById(Long.parseLong(id));
    }


}