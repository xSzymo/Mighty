package game.mightywarriors.services.league;

import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import org.springframework.stereotype.Service;

@Service
public class FightReferee {
    private final PointsForDivisionCounter pointsForDivisionCounter = new PointsForDivisionCounter();

    public boolean checkIsUserStrongerThanMonster(User user, Monster monster) {
        double userPoints = pointsForDivisionCounter.getPointsOfFighterPower(user);
        double monsterPoints = pointsForDivisionCounter.getPointsOfFighterPower(monster);

        return userPoints > monsterPoints;
    }

    public User checkWhoIsStronger(User user, User opponent) {
        double userPoints = pointsForDivisionCounter.getPointsOfFighterPower(user);
        double opponentPoints = pointsForDivisionCounter.getPointsOfFighterPower(opponent);

        if (userPoints == opponentPoints)
            return null;

        return userPoints > opponentPoints ? user : opponent;
    }
}
