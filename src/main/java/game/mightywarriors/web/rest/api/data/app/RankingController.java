package game.mightywarriors.web.rest.api.data.app;


import game.mightywarriors.data.services.RankingService;
import game.mightywarriors.data.tables.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RankingController {
    @Autowired
    private RankingService service;

    @GetMapping("rankings")
    public Set<Ranking> getRankings() {
        return service.findAll();
    }

    @GetMapping("rankings/{name}")
    public Ranking getRankingByName(@PathVariable("name") String name) {
        return service.find(name);
    }

    @GetMapping("rankings/{ranking}")
    public Ranking getRankingBy(@PathVariable("ranking") long ranking) {
        return service.find(ranking);
    }
}
