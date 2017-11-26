package game.mightywarriors.services.fights.division;

import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.helpers.counters.StatisticCounter;
import org.springframework.stereotype.Service;

@Service
public class FightReferee {
    private final StatisticCounter statisticCounter = new StatisticCounter();

    public boolean checkIsUserStrongerThanMonster(User user, Monster monster) {
        double userPoints = statisticCounter.countStatistic(user);
        double monsterPoints = statisticCounter.countStatistic(monster);

        return userPoints > monsterPoints;
    }

    public User checkWhoIsStronger(User user, User opponent) {
        double userPoints = statisticCounter.countStatistic(user);
        double opponentPoints = statisticCounter.countStatistic(opponent);

        if(userPoints == opponentPoints)
            return null;

        return userPoints > opponentPoints ? user : opponent;
    }
}
