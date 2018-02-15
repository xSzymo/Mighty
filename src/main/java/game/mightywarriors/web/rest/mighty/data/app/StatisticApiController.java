package game.mightywarriors.web.rest.mighty.data.app;


import game.mightywarriors.data.services.StatisticService;
import game.mightywarriors.data.tables.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class StatisticApiController {
    @Autowired
    private StatisticService service;

    @GetMapping("statistics")
    public Set<Statistic> getStatistics() {
        return service.findAll();
    }

    @GetMapping("statistics/{id}")
    public Statistic getStatistic(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }
}
