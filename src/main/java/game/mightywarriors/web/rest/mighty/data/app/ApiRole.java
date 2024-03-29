package game.mightywarriors.web.rest.mighty.data.app;


import game.mightywarriors.data.services.RoleService;
import game.mightywarriors.data.tables.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ApiRole {
    @Autowired
    private RoleService service;

    @GetMapping("userRoles")
    public Set<Role> getUserRoles() {
        return service.findAll();
    }

    @GetMapping("userRoles/{id}")
    public Role getUserRole(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }
}
