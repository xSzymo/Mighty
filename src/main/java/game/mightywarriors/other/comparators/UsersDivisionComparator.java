package game.mightywarriors.other.comparators;

import game.mightywarriors.data.tables.User;
import game.mightywarriors.helpers.counters.StatisticCounter;

import java.util.Comparator;

public class UsersDivisionComparator implements Comparator<User> {

    private final StatisticCounter statisticCounter = new StatisticCounter();

    @Override
    public int compare(User o1, User o2) {
        return Double.compare(statisticCounter.countStatistic(o1), statisticCounter.countStatistic(o2));
    }
}