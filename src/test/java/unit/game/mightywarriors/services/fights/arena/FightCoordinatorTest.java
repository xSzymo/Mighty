package unit.game.mightywarriors.services.fights.arena;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.json.objects.fights.FightResult;
import game.mightywarriors.json.objects.fights.Fighter;
import game.mightywarriors.json.objects.fights.RoundProcess;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.services.fight.FightCoordinator;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class FightCoordinatorTest {
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
        FightResult fightResult = fightCoordinator.fight(user1, user2);

        int i = 0;
        for (RoundProcess roundProcess : fightResult.getRounds()) {
            i++;
            LinkedList<Fighter> fighters = roundProcess.getUserChampions();
            LinkedList<Fighter> opponentChampions = roundProcess.getOpponentChampions();

//            if(i == 1) {
//                assertEquals(50, fighters.get(0).getHp());
//                assertEquals(50, fighters.get(1).getHp());
//                assertEquals(50, fighters.get(2).getHp());
//                assertEquals(30, opponentChampions.get(0).getHp());
//                assertEquals(50, opponentChampions.get(1).getHp());
//                assertEquals(30, opponentChampions.get(2).getHp());
//
//                assertEquals(40, fighters.get(0).getDmg());
//                assertEquals(50, fighters.get(1).getDmg());
//                assertEquals(50, fighters.get(2).getDmg());
//                assertEquals(50, opponentChampions.get(0).getDmg());
//                assertEquals(50, opponentChampions.get(1).getDmg());
//                assertEquals(30, opponentChampions.get(2).getDmg());
//            }
//
//            if(i == 2) {
//                assertEquals(50, fighters.get(0).getHp());
//                assertEquals(50, fighters.get(1).getHp());
//                assertEquals(50, fighters.get(2).getHp());
//                assertEquals(30, opponentChampions.get(0).getHp());
//                assertEquals(50, opponentChampions.get(1).getHp());
//                assertEquals(30, opponentChampions.get(2).getHp());
//
//                assertEquals(40, fighters.get(0).getDmg());
//                assertEquals(50, fighters.get(1).getDmg());
//                assertEquals(50, fighters.get(2).getDmg());
//                assertEquals(50, opponentChampions.get(0).getDmg());
//                assertEquals(50, opponentChampions.get(1).getDmg());
//                assertEquals(30, opponentChampions.get(2).getDmg());
//            }
        }
        assertNotEquals(fightResult.getRounds().get(0).getUserChampions().get(0).getHp(), fightResult.getRounds().get(1).getUserChampions().get(0).getHp());
        assertEquals(user1, fightResult.getWinner());
        assertEquals(user2, fightResult.getLooser());
    }

    private User setUpUser1() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(0, 0, 0, 0, 0, 0), 1));

        user.getChampions().add(new Champion(new Statistic(10, 1, 10, 0, 5, 5), equipment).setLevel(2));

        return user;
    }

    private User setUpUser2() throws Exception {
        User user = new User("user", "user", "user");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(0, 0, 0, 0, 0, 0), 1));

        user.getChampions().add(new Champion(new Statistic(10, 1, 10, 0, 5, 5), equipment).setLevel(2));

        return user;
    }

//    private User setUpUser1() throws Exception {
//        User user = new User("admin", "admin", "admin");
//
//        Equipment equipment = new Equipment();
//        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setRing(new Item(WeaponType.RING, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(2, 5, 7, 9, 10, 12), 1));
//
//        user.getChampions().add(new Champion(new Statistic(2, 2, 50, 5, 5, 7), equipment).setLevel(10));
//        user.getChampions().add(new Champion(new Statistic(2, 2, 50, 5, 5, 7), equipment).setLevel(20));
//        user.getChampions().add(new Champion(new Statistic(2, 2, 50, 5, 5, 7), equipment).setLevel(30));
//
//        return user;
//    }
//    private User setUpUser2() throws Exception {
//        User user = new User("user", "user", "user");
//
//        Equipment equipment = new Equipment();
//        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setRing(new Item(WeaponType.RING, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(2, 5, 7, 9, 10, 12), 1));
//        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(2, 5, 7, 9, 10, 12), 1));
//
//        user.getChampions().add(new Champion(new Statistic(1, 4, 50, 4, 8, 7), equipment).setLevel(20));
//        user.getChampions().add(new Champion(new Statistic(1, 4, 50, 4, 8, 7), equipment).setLevel(30));
//        user.getChampions().add(new Champion(new Statistic(1, 4, 50, 4, 8, 7), equipment).setLevel(10));
//
//        return user;
//    }
}
