package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.tables.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.LinkedList;

@RestController
public class DivisionsApiController {
    @Autowired
    private DivisionService divisionService;

    @GetMapping("divisions")
    public HashSet<Division> getUserRoles() {
        return divisionService.findAll();
    }

    @GetMapping("divisions/{id}")
    public Division getUserRole(@PathVariable("id") String id) {
        return divisionService.findOne(Long.parseLong(id));
    }
}
