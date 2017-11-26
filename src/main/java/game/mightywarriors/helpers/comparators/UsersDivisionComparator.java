package game.mightywarriors.helpers.comparators;

import game.mightywarriors.data.tables.User;
import game.mightywarriors.helpers.counters.StatisticCounter;

import java.util.Comparator;

public class UsersDivisionComparator implements Comparator<User> {

    private final StatisticCounter statisticCounter = new StatisticCounter();

    @Override
    public int compare(User o1, User o2) {
        return statisticCounter.countUserStatistic(o1) < statisticCounter.countUserStatistic(o2) ? -1 :
                statisticCounter.countUserStatistic(o1) == statisticCounter.countUserStatistic(o2) ? 0 : 1;
    }
}