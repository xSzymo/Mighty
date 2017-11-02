package game.mightywarriors.other;

import game.mightywarriors.data.enums.Level;
import game.mightywarriors.data.tables.Champion;

public class ChampionLevelManager {

    private ChampionLevelManager() {
    }

    public static long getUserLevel(Champion champion) {
        int championLevel = 0;

        for (Level level : Level.values())
            if (level.getLevel() < champion.getExperience())
                championLevel++;

        if(champion.getLevel() < championLevel)
            champion.setLevel(championLevel);

        return championLevel;
    }
}
