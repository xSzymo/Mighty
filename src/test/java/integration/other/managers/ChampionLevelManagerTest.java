package integration.other.managers;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.tables.Champion;
import integration.config.IntegrationTestsConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

public class ChampionLevelManagerTest extends IntegrationTestsConfig {
    @Autowired
    private ChampionService championService;
    private Champion champion;

    @Test
    public void getUserLevel_1() {
        champion = new Champion().setLevel(1).setExperience(250);
        championService.save(champion);
        champion = championService.findOne(champion);
        assertEquals(6, champion.getLevel());
        assertEquals(50, champion.getExperience());
    }

    @Test
    public void getUserLevel_2() {
        champion = new Champion().setLevel(3).setExperience(90);
        championService.save(champion);
        champion = championService.findOne(champion);
        assertEquals(5, champion.getLevel());
        assertEquals(0, champion.getExperience());
    }

    @Test
    public void getUserLevel_3() {
        champion = new Champion().setLevel(3).setExperience(2000);
        championService.save(champion);
        champion = championService.findOne(champion);
        assertEquals(19, champion.getLevel());
        assertEquals(610, champion.getExperience());
    }

    @Test
    public void getUserLevel_4() {
        champion = new Champion().setLevel(4).setExperience(3000);
        championService.save(champion);
        champion = championService.findOne(champion);
        assertEquals(19, champion.getLevel());
        assertEquals(1650, champion.getExperience());
    }
}
