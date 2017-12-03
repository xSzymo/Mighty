package unit.game.mightywarriors.other.managers;

import game.mightywarriors.configuration.system.SystemFightsVariablesManager;
import game.mightywarriors.data.tables.Champion;
import org.junit.Test;

import static game.mightywarriors.other.managers.ChampionLevelManager.getChampionRealLevel;
import static junit.framework.TestCase.assertEquals;

public class ChampionLevelManagerTest {
    private Champion champion;

    @Test
    public void getChampionRealLevel_real() {
        checker(getChampionRealLevel(new Champion().setLevel(1).setExperience(SystemFightsVariablesManager.Levels.TWO - 1)), 1, (int) SystemFightsVariablesManager.Levels.TWO - 1);
        checker(getChampionRealLevel(new Champion().setLevel(1).setExperience(SystemFightsVariablesManager.Levels.TWO)), 2, 0);
        checker(getChampionRealLevel(new Champion().setLevel(1).setExperience(SystemFightsVariablesManager.Levels.TWO + 1)), 2, 1);

        checker(getChampionRealLevel(new Champion().setLevel(2).setExperience(SystemFightsVariablesManager.Levels.THREE - 1)), 2, (int) SystemFightsVariablesManager.Levels.THREE - 1);
        checker(getChampionRealLevel(new Champion().setLevel(2).setExperience(SystemFightsVariablesManager.Levels.THREE)), 3, 0);
        checker(getChampionRealLevel(new Champion().setLevel(2).setExperience(SystemFightsVariablesManager.Levels.THREE + 1)), 3, 1);

        checker(getChampionRealLevel(new Champion().setLevel(3).setExperience(SystemFightsVariablesManager.Levels.FOUR - 1)), 3, (int) SystemFightsVariablesManager.Levels.FOUR - 1);
        checker(getChampionRealLevel(new Champion().setLevel(3).setExperience(SystemFightsVariablesManager.Levels.FOUR)), 4, 0);
        checker(getChampionRealLevel(new Champion().setLevel(3).setExperience(SystemFightsVariablesManager.Levels.FOUR + 1)), 4, 1);
    }

    @Test
    public void getChampionRealLevel_unreal() {
        checker(getChampionRealLevel(new Champion().setLevel(1).setExperience(250)), 7, 40);
        checker(getChampionRealLevel(new Champion().setLevel(2).setExperience(400)), 9, 50);
        checker(getChampionRealLevel(new Champion().setLevel(4).setExperience(500)), 11, 10);
        checker(getChampionRealLevel(new Champion().setLevel(5).setExperience(600)), 12, 50);
        checker(getChampionRealLevel(new Champion().setLevel(6).setExperience(700)), 14, 0);
        checker(getChampionRealLevel(new Champion().setLevel(7).setExperience(800)), 15, 60);
        checker(getChampionRealLevel(new Champion().setLevel(8).setExperience(800)), 16, 30);
        checker(getChampionRealLevel(new Champion().setLevel(9).setExperience(800)), 17, 10);
        checker(getChampionRealLevel(new Champion().setLevel(10).setExperience(800)), 18, 0);
    }

    private void checker(Champion champion, int level, int experience) {
        assertEquals(level, champion.getLevel());
        assertEquals(experience, champion.getExperience());
    }
}
