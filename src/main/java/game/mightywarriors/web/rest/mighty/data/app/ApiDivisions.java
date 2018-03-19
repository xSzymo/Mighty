package game.mightywarriors.web.rest.mighty.data.app;


import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.tables.Division;
import game.mightywarriors.other.enums.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
public class ApiDivisions {
    @Autowired
    private DivisionService service;

    @GetMapping("divisions")
    public HashSet<Division> getUserRoles() {
        return service.findAll();
    }

    @GetMapping("divisions/id/{id}")
    public Division getUserRole(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }

    @GetMapping("divisions/{name}")
    public Division checkChampionBlockTime(@PathVariable("name") String name) {

        return service.find(League.valueOf(name.toUpperCase()));
    }
}
