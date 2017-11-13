package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.ChampionRepository;
import game.mightywarriors.data.tables.Champion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class ChampionApiController {
    @Autowired
    ChampionRepository championRepository;

    @GetMapping("api/champions")
    public LinkedList<Champion> getChampions() {
        return championRepository.findAll();
    }

    @GetMapping("api/champions/{id}")
    public Champion getChampion(@PathVariable("id") String id) {
        return championRepository.findById(Long.parseLong(id));
    }
}
