package game.mightywarriors.data.interfaces;

import game.mightywarriors.data.tables.Statistic;

public interface Fighter {
    Statistic getStatistic();

    long getLevel();

    Long getId();
}
