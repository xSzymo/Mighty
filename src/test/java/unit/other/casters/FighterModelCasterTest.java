package unit.other.casters;

import game.mightywarriors.data.interfaces.IFighter;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.other.casters.FighterModelCaster;
import game.mightywarriors.web.json.objects.fights.Fighter;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;

public class FighterModelCasterTest {
    private FighterModelCaster objectUnderTest;
    private Champion champion;
    private Monster monster;

    @Before
    public void setUp() {
        objectUnderTest = new FighterModelCaster();
    }

    @Test
    public void castChampionToChampionModel() {
        champion = new Champion().setStatistic(new Statistic(4, 4, 4, 4, 4, 4));
        checker(objectUnderTest.castChampionToChampionModel(champion));


        monster = new Monster().setStatistic(new Statistic(4, 4, 4, 4, 4, 4));
        checker(objectUnderTest.castChampionToChampionModel(monster));
    }

    @Test
    public void castChampionToChampionModel1_champs() {
        LinkedList<IFighter> fighters = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            champion = new Champion().setStatistic(new Statistic(4, 4, 4, 4, 4, 4));
            fighters.add(champion);
        }
        objectUnderTest.castChampionToChampionModel(fighters);
    }

    @Test
    public void castChampionToChampionModel1_monsters() {
        LinkedList<IFighter> fighters = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            monster = new Monster().setStatistic(new Statistic(4, 4, 4, 4, 4, 4));
            fighters.add(monster);
        }
        objectUnderTest.castChampionToChampionModel(fighters);
    }

    private void checker(Fighter fighter) {
        assertEquals(1, fighter.getLevel());
        assertEquals(1.2, fighter.getHp());
        assertEquals(0.0, fighter.getDmg());

        assertEquals(0.4, fighter.getArmor());
        assertEquals(0.4, fighter.getMagicResist());
        assertEquals(0.4, fighter.getIntelligence());
        assertEquals(0.4, fighter.getStrength());
        assertEquals(0.4, fighter.getCriticalChance());
    }
}