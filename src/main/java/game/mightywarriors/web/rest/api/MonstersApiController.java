package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.services.MonsterService;
import game.mightywarriors.data.tables.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class MonstersApiController {
    @Autowired
    MonsterService monsterService;

    @GetMapping("api/monsters")
    public LinkedList<Monster> getChampions() {
        return monsterService.findAll();
    }

    @GetMapping("api/monsters/{id}")
    public Monster getChampion(@PathVariable("id") String id) {
        return monsterService.findOne(Long.parseLong(id));
    }
}
