package game.mightywarriors.web.rest.mighty.data.user;


import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.tables.Champion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ChampionApiController {
    @Autowired
    private ChampionService service;

    @GetMapping("champions")
    public Set<Champion> getChampions() {
        return service.findAll();
    }

    @GetMapping("champions/{id}")
    public Champion getChampion(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }
}
