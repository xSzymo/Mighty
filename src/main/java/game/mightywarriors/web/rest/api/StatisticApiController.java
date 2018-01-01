package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.StatisticRepository;
import game.mightywarriors.data.tables.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Set;

@RestController
public class StatisticApiController {
    @Autowired
    StatisticRepository statisticRepository;

    @GetMapping("statistics")
    public Set<Statistic> getStatistics() {
        return statisticRepository.findAll();
    }

    @GetMapping("statistics/{id}")
    public Statistic getStatistic(@PathVariable("id") String id) {
        return statisticRepository.findById(Long.parseLong(id));
    }
}
