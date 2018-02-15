package game.mightywarriors.web.rest.mighty.data.user;


import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.services.combat.PointsInFightCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class UserApiController {
    @Autowired
    private PointsInFightCounter pointsInFightCounter;
    @Autowired
    private UserService service;

    @GetMapping("users/{id}")
    public User getUser(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }

    @GetMapping("users")
    public Set<User> getUsers() {
        return service.findAll();
    }

    @GetMapping("users/statistics")
    public Set<User> getUsersWithValidStatistics() {
        HashSet<User> users = service.findAll();

        for (User user : users)
            user.getChampions().forEach(x -> x.setStatistic(setValidStatistic(x)));

        return users;
    }

    @GetMapping("users/statistics/{id}")
    public User getUserWithValidStatistics(@PathVariable("id") String id) {
        User user = service.find(Long.parseLong(id));
        user.getChampions().forEach(x -> x.setStatistic(setValidStatistic(x)));

        return user;
    }

    private Statistic setValidStatistic(Champion champion) {
        Statistic statistic = new Statistic();

        statistic.setArmor(Math.round(pointsInFightCounter.getPointsForSpecificType(champion, StatisticType.ARMOR)));
        statistic.setMagicResist(Math.round(pointsInFightCounter.getPointsForSpecificType(champion, StatisticType.MAGIC_RESIST)));
        statistic.setStrength(Math.round(pointsInFightCounter.getPointsForSpecificType(champion, StatisticType.STRENGTH)));
        statistic.setIntelligence(Math.round(pointsInFightCounter.getPointsForSpecificType(champion, StatisticType.INTELLIGENCE)));
        statistic.setCriticalChance(Math.round(pointsInFightCounter.getPointsForSpecificType(champion, StatisticType.CRITICAL_CHANCE)));
        statistic.setVitality(Math.round(pointsInFightCounter.getPointsForSpecificType(champion, StatisticType.VITALITY)));
        champion.setStatistic(statistic);

        return statistic;
    }
}
