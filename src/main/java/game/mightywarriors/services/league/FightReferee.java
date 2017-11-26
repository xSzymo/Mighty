package game.mightywarriors.services.league;

import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.helpers.counters.division.StatisticCounter;
import org.springframework.stereotype.Service;

@Service
public class FightReferee {
    private final StatisticCounter statisticCounter = new StatisticCounter();

    public boolean checkIsUserStrongerThanMonster(User user, Monster monster) {
        double userPoints = statisticCounter.getPointsOfFighterPower(user);
        double monsterPoints = statisticCounter.getPointsOfFighterPower(monster);

        return userPoints > monsterPoints;
    }

    public User checkWhoIsStronger(User user, User opponent) {
        double userPoints = statisticCounter.getPointsOfFighterPower(user);
        double opponentPoints = statisticCounter.getPointsOfFighterPower(opponent);

        if (userPoints == opponentPoints)
            return null;

        return userPoints > opponentPoints ? user : opponent;
    }
}
