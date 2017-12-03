package game.mightywarriors.data.interfaces;

import game.mightywarriors.data.tables.Statistic;

public interface IFighter {
    Statistic getStatistic();

    long getLevel();

    Long getId();
}
