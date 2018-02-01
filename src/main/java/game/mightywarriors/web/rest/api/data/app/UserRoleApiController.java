package game.mightywarriors.web.rest.api.data.app;


import game.mightywarriors.data.services.UserRoleService;
import game.mightywarriors.data.tables.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class UserRoleApiController {
    @Autowired
    private UserRoleService service;

    @GetMapping("userRoles")
    public Set<UserRole> getUserRoles() {
        return service.findAll();
    }

    @GetMapping("userRoles/{id}")
    public UserRole getUserRole(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }
}
