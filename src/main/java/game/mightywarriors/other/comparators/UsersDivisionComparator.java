package game.mightywarriors.other.comparators;

import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.league.PointsForDivisionCounter;

import java.util.Comparator;

public class UsersDivisionComparator implements Comparator<User> {

    private final PointsForDivisionCounter pointsForDivisionCounter = new PointsForDivisionCounter();

    @Override
    public int compare(User o1, User o2) {
        return Double.compare(pointsForDivisionCounter.getPointsOfFighterPower(o1), pointsForDivisionCounter.getPointsOfFighterPower(o2));
    }
}