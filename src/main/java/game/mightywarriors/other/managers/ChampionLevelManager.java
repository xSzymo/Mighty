package game.mightywarriors.other.managers;

import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.other.enums.Level;

public class ChampionLevelManager {

    private ChampionLevelManager() {
    }

    public static Champion getUserLevel(Champion champion) {
        Level[] levels = Level.values();
        for (int i = 0; i < levels.length; i++)
            if (champion.getLevel() == levels[i].getLevel())
                if (champion.getExperience() >= levels[i].getExperience()) {
                    champion.setExperience(champion.getExperience() - levels[i].getExperience());
                    champion.setLevel(champion.getLevel() + 1);
                }

        return champion;
    }
}
