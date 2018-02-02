package unit.services.combat;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.services.combat.PointsInFightCounter;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PointsInFightCounterTest {
    private PointsInFightCounter pointsInFightCounter;

    @Before
    public void setUp() {
        pointsInFightCounter = new PointsInFightCounter();
    }

    @Test
    public void test_fightBetweenPlayerAndMonster() throws Exception {
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

        assertEquals(5.2, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.INTELLIGENCE));
        assertEquals(10.5, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.ARMOR));
        assertEquals(9.5, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.CRITICAL_CHANCE));
        assertEquals(12.7, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.MAGIC_RESIST));
        assertEquals(2.19, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.STRENGTH));
        assertEquals(21.9, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.VITALITY));
    }

    @Test
    public void test_fightBetweenPlayerAndMonster_1() throws Exception {
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

        user.getChampions().add(new Champion(new Statistic(2, 2, 3, 5, 5, 7), equipment).setLevel(5));

        assertEquals(26.0, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.INTELLIGENCE));
        assertEquals(52.5, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.ARMOR));
        assertEquals(47.5, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.CRITICAL_CHANCE));
        assertEquals(63.5, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.MAGIC_RESIST));
        assertEquals(11.0, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.STRENGTH));
        assertEquals(109.5, pointsInFightCounter.getPointsForSpecificType(user, StatisticType.VITALITY));
    }
}
