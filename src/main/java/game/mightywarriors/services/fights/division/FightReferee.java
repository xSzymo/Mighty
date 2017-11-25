package game.mightywarriors.services.fights.division;

import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.helpers.StatisticCounter;
import org.springframework.stereotype.Service;

@Service
public class FightReferee {
    private final StatisticCounter statisticCounter = new StatisticCounter();

    public boolean checkIsUserStrongerThanMonster(User user, Monster monster) {
        double userPoints = statisticCounter.countUserStatistic(user);
        double monsterPoints = statisticCounter.countMonsterStatistic(monster);

        return userPoints > monsterPoints ? true : false;
    }

    public User checkWhoIsStronger(User user, User opponent) {
        double userPoints = statisticCounter.countUserStatistic(user);
        double opponentPoints = statisticCounter.countUserStatistic(opponent);

        if(userPoints == opponentPoints)
            return null;

        return userPoints > opponentPoints ? user : opponent;
    }
}
