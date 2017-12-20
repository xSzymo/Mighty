package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class MissionFightController {
    @Autowired
    private MissionFightService service;
    @Autowired
    private UsersRetriever retriever;

    @GetMapping("missionFight")
    public LinkedList<MissionFight> getChampions() {
        return service.findAll();
    }

    @GetMapping("missionFight/{id}")
    public MissionFight getChampion(@PathVariable("id") String id) {
        return service.findOne(Long.parseLong(id));
    }
}
