package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.services.RankingService;
import game.mightywarriors.data.tables.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class RankingController {
    @Autowired
    private RankingService rankingService;

    @GetMapping("rankings")
    public LinkedList<Ranking> getUsers() {
        return rankingService.findAll();
    }

    @GetMapping("rankings/{name}")
    public Ranking getUser(@PathVariable("name") String name) {
        return rankingService.findOne(name);
    }
}
