package unit.other.comparators;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.comparators.UsersDivisionComparator;
import game.mightywarriors.other.enums.ItemType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersDivisionComparatorTest {
    private UsersDivisionComparator objectUnderTest;

    @Before
    public void setUp() {
        objectUnderTest = new UsersDivisionComparator();
    }

    @Test
    public void compare() throws Exception {
        assertEquals(1, objectUnderTest.compare(setUpUser1(), setUpUser2()));
        assertEquals(-1, objectUnderTest.compare(setUpUser2(), setUpUser1()));
        assertEquals(0, objectUnderTest.compare(setUpUser1(), setUpUser1()));
    }

    private User setUpUser1() throws Exception {
        User user = new User("boss", "boss", "boss");

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

        user.getChampions().add(new Champion(new Statistic(25, 500, 50, 55, 51, 57), equipment).setLevel(10));
        user.getChampions().add(new Champion(new Statistic(25, 22, 50, 55, 51, 57), equipment).setLevel(10));
        user.getChampions().add(new Champion(new Statistic(25, 22, 50, 55, 51, 57), equipment).setLevel(100));

        return user;
    }

    private User setUpUser2() throws Exception {
        User user = new User("medium", "medium", "medium");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(ItemType.WEAPON, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(ItemType.ARMOR, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setRing(new Item(ItemType.RING, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setOffhand(new Item(ItemType.OFFHAND, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(ItemType.NECKLACE, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setLegs(new Item(ItemType.LEGS, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setHelmet(new Item(ItemType.HELMET, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setGloves(new Item(ItemType.GLOVES, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setBracelet(new Item(ItemType.BRACELET, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setBoots(new Item(ItemType.BOOTS, new Statistic(0, 0, 0, 0, 0, 0), 1));

        user.getChampions().add(new Champion(new Statistic(11, 11, 50, 10, 11, 11), equipment).setLevel(60));

        return user;
    }
}