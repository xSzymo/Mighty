package unit.services.bookmarks.league;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.services.bookmarks.league.PointsForDivisionCounter;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PointsForDivisionCounterTest {

    private PointsForDivisionCounter pointsForDivisionCounter;

    @Before
    public void setUp() {
        pointsForDivisionCounter = new PointsForDivisionCounter();
    }

    @Test
    public void checkIsUserStrongerThanMonster() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(ItemType.WEAPON, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setArmor(new Item(ItemType.ARMOR, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setRing(new Item(ItemType.RING, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setOffhand(new Item(ItemType.OFFHAND, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setNecklace(new Item(ItemType.NECKLACE, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setLegs(new Item(ItemType.LEGS, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setHelmet(new Item(ItemType.HELMET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setGloves(new Item(ItemType.GLOVES, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBracelet(new Item(ItemType.BRACELET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBoots(new Item(ItemType.BOOTS, new Statistic(2, 5, 7, 9, 10, 12), 1));

        user.getChampions().add(new Champion(new Statistic(2, 2, 3, 5, 5, 7), equipment));

        assertEquals(47.4, pointsForDivisionCounter.getPointsOfFighterPower(user));
    }

    @Test
    public void checkWhoIsStronger() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(ItemType.WEAPON, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setArmor(new Item(ItemType.ARMOR, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setRing(new Item(ItemType.RING, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setOffhand(new Item(ItemType.OFFHAND, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setNecklace(new Item(ItemType.NECKLACE, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setLegs(new Item(ItemType.LEGS, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setHelmet(new Item(ItemType.HELMET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setGloves(new Item(ItemType.GLOVES, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBracelet(new Item(ItemType.BRACELET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBoots(new Item(ItemType.BOOTS, new Statistic(2, 5, 7, 9, 10, 12), 1));

        user.getChampions().add(new Champion(new Statistic(2, 2, 3, 5, 5, 7), equipment));

        assertEquals(47.4, pointsForDivisionCounter.getPointsOfFighterPower(user.getChampions().iterator().next()));
    }
}
