package unit.services.combat.monster;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.WeaponType;
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
        assertEquals(2, fightResult.getRounds().get(0).getUserChampions().size());
        assertEquals(1, fightResult.getRounds().get(0).getOpponentChampions().size());
        assertEquals(user1, fightResult.getWinner());
    }

    private User setUpUser1() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(0, 0, 10, 0, 5, 5), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(0, 0, 5, 0, 0, 0), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(5, 5, 10, 0, 0, 0), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(0, 0, 10, 0, 10, 10), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(0, 0, 5, 0, 5, 5), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(0, 5, 5, 0, 0, 0), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(0, 0, 5, 0, 5, 5), 1));

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
