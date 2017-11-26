package integration.game.mightywarriors.other.managers;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.tables.Champion;
import integration.game.mightywarriors.config.IntegrationTestsConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

public class ChampionLevelManagerTest extends IntegrationTestsConfig {
    @Autowired
    private ChampionService championService;

    @Test
    public void getUserLevel() throws Exception {
        Champion champion = new Champion().setLevel(3).setExperience(90);
        championService.save(champion);
        champion = championService.findOne(champion);
        assertEquals(4, champion.getLevel());
        assertEquals(50, champion.getExperience());

        champion = new Champion().setLevel(1).setExperience(250);
        championService.save(champion);
        champion = championService.findOne(champion);
        assertEquals(5, champion.getLevel());
        assertEquals(100, champion.getExperience());

        champion = new Champion().setLevel(3).setExperience(2000);
        championService.save(champion);
        champion = championService.findOne(champion);
        assertEquals(8, champion.getLevel());
        assertEquals(760, champion.getExperience());
    }

}