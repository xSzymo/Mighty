package unit.game.mightywarriors.services.combat.user;

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

public class FightCoordinatorUserMultipleChampionsTest {
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
    public void fightBetweenPlayerAndMonster() {
        FightResult fightResult = fightCoordinator.fight(user1, user2);

        int i = -1;
        for (RoundProcess roundProcess : fightResult.getRounds()) {
            i++;
            LinkedList<Fighter> championModels = roundProcess.getUserChampions();
            LinkedList<Fighter> opponentChampions = roundProcess.getOpponentChampions();

            if (i == 0) {
                assertEquals(1.5, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.5, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(1.5, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 1) {
                assertEquals(1.5, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.5, championModels.get(1).getHp());
                assertEquals(1.31, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(0.19, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 2) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.5, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(0.19, opponentChampions.get(0).getHp());
                assertEquals(1.57, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 3) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.5, championModels.get(1).getHp());
                assertEquals(1.31, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(-1.12, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 4) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(-0.07, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(-1.12, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(1.57, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 5) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(-0.07, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(1.31, championModels.get(2).getDmg());

                assertEquals(-1.12, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(0.19, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 6) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(-0.07, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(-0.07, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(-1.12, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(0.19, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(1.57, opponentChampions.get(2).getDmg());
            }
        }

        assertEquals(7, fightResult.getRounds().size());
        assertEquals(user1, fightResult.getLooser());
        assertEquals(user2, fightResult.getWinner());
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

        user.getChampions().add(new Champion(new Statistic(10, 1, 5, 0, 5, 5), equipment).setLevel(1));
        user.getChampions().add(new Champion(new Statistic(10, 1, 5, 0, 5, 5), equipment).setLevel(1));
        user.getChampions().add(new Champion(new Statistic(10, 1, 5, 0, 5, 5), equipment).setLevel(1));

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

        user.getChampions().add(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(1));
        user.getChampions().add(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(1));
        user.getChampions().add(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(1));

        return user;
    }
}
