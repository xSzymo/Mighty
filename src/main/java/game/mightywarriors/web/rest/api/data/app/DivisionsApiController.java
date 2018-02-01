package game.mightywarriors.web.rest.api.data.app;


import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.tables.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
public class DivisionsApiController {
    @Autowired
    private DivisionService service;

    @GetMapping("divisions")
    public HashSet<Division> getUserRoles() {
        return service.findAll();
    }

    @GetMapping("divisions/{id}")
    public Division getUserRole(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }
}
