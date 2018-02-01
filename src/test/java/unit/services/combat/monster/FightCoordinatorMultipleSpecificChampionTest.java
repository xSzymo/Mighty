package unit.services.combat.monster;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.FighterType;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.web.json.objects.fights.FightResult;
import game.mightywarriors.web.json.objects.fights.Fighter;
import game.mightywarriors.web.json.objects.fights.RoundProcess;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class FightCoordinatorMultipleSpecificChampionTest {
    private FightCoordinator fightCoordinator;
    private User user1;
    private Monster monster;

    @Before
    public void setUp() throws Exception {
        fightCoordinator = new FightCoordinator();
        user1 = setUpUser1();
        monster = setUpMonster();
    }

    @Test
    public void fightBetweenPlayerAndMonster() throws Exception {
        long[] ids = {1, 2};
        FightResult fightResult = fightCoordinator.fight(user1, monster, ids);

        int i = -1;
        for (RoundProcess roundProcess : fightResult.getRounds()) {
            i++;
            LinkedList<Fighter> championModels = roundProcess.getUserChampions();
            LinkedList<Fighter> opponentChampions = roundProcess.getOpponentChampions();

            if (i == 0) {
                assertEquals(18.0, championModels.get(0).getHp());
                assertEquals(36.0, championModels.get(1).getHp());
                assertEquals(12.0, opponentChampions.get(0).getHp());

                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
            } else if (i == 1) {
                assertEquals(18.0, championModels.get(0).getHp());
                assertEquals(36.0, championModels.get(1).getHp());
                assertEquals(2.6, opponentChampions.get(0).getHp());

                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(9.4, championModels.get(1).getDmg());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
            } else if (i == 2) {
                assertEquals(16.58, championModels.get(0).getHp());
                assertEquals(36.0, championModels.get(1).getHp());
                assertEquals(2.6, opponentChampions.get(0).getHp());

                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(1.42, opponentChampions.get(0).getDmg());
            } else if (i == 3) {
                assertEquals(16.58, championModels.get(0).getHp());
                assertEquals(36.0, championModels.get(1).getHp());
                assertEquals(-2.0, opponentChampions.get(0).getHp());

                assertEquals(4.6, championModels.get(0).getDmg());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
            }
        }

        for(int j = 0; j < fightResult.getRounds().size(); j++) {
            assertEquals(FighterType.CHAMPION.getType(), fightResult.getRounds().get(i).getUserChampions().getFirst().getFighterType().getType());
            assertEquals(FighterType.MONSTER.getType(), fightResult.getRounds().get(i).getOpponentChampions().getFirst().getFighterType().getType());
        }
        assertEquals(2, fightResult.getRounds().get(0).getUserChampions().size());
        assertEquals(1, fightResult.getRounds().get(0).getOpponentChampions().size());
        assertEquals(user1.getLogin(), fightResult.getWinner().getLogin());
    }

    private User setUpUser1() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(ItemType.WEAPON, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(ItemType.ARMOR, new Statistic(0, 0, 10, 0, 5, 5), 1));
        equipment.setRing(new Item(ItemType.RING, new Statistic(0, 0, 5, 0, 0, 0), 1));
        equipment.setOffhand(new Item(ItemType.OFFHAND, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(ItemType.NECKLACE, new Statistic(5, 5, 10, 0, 0, 0), 1));
        equipment.setLegs(new Item(ItemType.LEGS, new Statistic(0, 0, 10, 0, 10, 10), 1));
        equipment.setHelmet(new Item(ItemType.HELMET, new Statistic(0, 0, 5, 0, 5, 5), 1));
        equipment.setGloves(new Item(ItemType.GLOVES, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setBracelet(new Item(ItemType.BRACELET, new Statistic(0, 5, 5, 0, 0, 0), 1));
        equipment.setBoots(new Item(ItemType.BOOTS, new Statistic(0, 0, 5, 0, 5, 5), 1));

        Champion champion1 = spy(new Champion(new Statistic(10, 1, 10, 0, 5, 5), equipment).setLevel(1));
        Champion champion2 = spy(new Champion(new Statistic(11, 1, 10, 0, 5, 5), equipment).setLevel(2));
        Champion champion3 = spy(new Champion(new Statistic(10, 1, 10, 0, 5, 5), equipment).setLevel(1000));

        when(champion1.getId()).thenReturn(1L);
        when(champion2.getId()).thenReturn(2L);
        when(champion3.getId()).thenReturn(3L);

        user.addChampion(champion1);
        user.addChampion(champion2);
        user.addChampion(champion3);

        return user;
    }

    private Monster setUpMonster() {
        return new Monster(new Statistic(25, 25, 40, 0, 10, 10)).setLevel(1);
    }
}
