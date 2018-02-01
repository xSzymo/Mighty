package unit.services.combat.user;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.FighterType;
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

public class FightCoordinatorSpecificChampionUserTest {
    private FightCoordinator fightCoordinator;
    private User user1;
    private User user2;

    @Before
    public void setUp() throws Exception {
        fightCoordinator = new FightCoordinator();
        user1 = setUpUser1();
        user2 = setUpUser2();
    }

    @Test
    public void fightBetweenPlayerAndMonster() throws Exception {
        FightResult fightResult = fightCoordinator.fight(user1, user2, 1, 4);

        int i = -1;
        for (RoundProcess roundProcess : fightResult.getRounds()) {
            i++;
            LinkedList<Fighter> championModels = roundProcess.getUserChampions();
            LinkedList<Fighter> opponentChampions = roundProcess.getOpponentChampions();

            if (i == 0) {
                assertEquals(3.0, championModels.get(0).getHp());
                assertEquals(3.0, opponentChampions.get(0).getHp());

                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
            } else if (i == 1) {
                assertEquals(3.0, championModels.get(0).getHp());
                assertEquals(1.69, opponentChampions.get(0).getHp());

                assertEquals(1.31, championModels.get(0).getDmg());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
            } else if (i == 2) {
                assertEquals(1.43, championModels.get(0).getHp());
                assertEquals(1.69, opponentChampions.get(0).getHp());

                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.57, opponentChampions.get(0).getDmg());
            } else if (i == 3) {
                assertEquals(1.43, championModels.get(0).getHp());
                assertEquals(0.38, opponentChampions.get(0).getHp());

                assertEquals(1.31, championModels.get(0).getDmg());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
            } else if (i == 4) {
                assertEquals(-0.14, championModels.get(0).getHp());
                assertEquals(0.38, opponentChampions.get(0).getHp());

                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.57, opponentChampions.get(0).getDmg());
            }
        }

        for(int j = 0; j < fightResult.getRounds().size(); j++) {
            assertEquals(FighterType.CHAMPION.getType(), fightResult.getRounds().get(i).getUserChampions().getFirst().getFighterType().getType());
            assertEquals(FighterType.CHAMPION.getType(), fightResult.getRounds().get(i).getOpponentChampions().getFirst().getFighterType().getType());
        }
        assertEquals(5, fightResult.getRounds().size());
        assertEquals(user1.getLogin(), fightResult.getLooser().getLogin());
        assertEquals(user2.getLogin(), fightResult.getWinner().getLogin());
    }

    private User setUpUser1() throws Exception {
        User user = new User("user", "user", "user");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(0, 0, 0, 0, 10, 10), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(0, 5, 0, 0, 0, 0), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(0, 0, 0, 0, 5, 5), 1));

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

    private User setUpUser2() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(0, 0, 0, 0, 10, 10), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(0, 5, 0, 0, 0, 0), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(0, 0, 0, 0, 5, 5), 1));

        Champion champion1 = spy(new Champion(new Statistic(15, 5, 10, 0, 5, 5), equipment).setLevel(1));
        Champion champion2 = spy(new Champion(new Statistic(11, 1, 10, 0, 5, 5), equipment).setLevel(2));
        Champion champion3 = spy(new Champion(new Statistic(10, 1, 10, 0, 5, 5), equipment).setLevel(1000));

        when(champion1.getId()).thenReturn(4L);
        when(champion2.getId()).thenReturn(5L);
        when(champion3.getId()).thenReturn(6L);

        user.addChampion(champion1);
        user.addChampion(champion2);
        user.addChampion(champion3);

        return user;
    }
}
