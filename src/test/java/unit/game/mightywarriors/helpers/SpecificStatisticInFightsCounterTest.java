package unit.game.mightywarriors.helpers;

import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.helpers.counters.SpecificStatisticInFightsCounter;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SpecificStatisticInFightsCounterTest {
    SpecificStatisticInFightsCounter specificStatisticInFightsCounter = new SpecificStatisticInFightsCounter();

    @Test
    public void test_fightBetweenPlayerAndMonster() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(2, 5, 7, 9, 10, 12), 1));

        user.getChampions().add(new Champion(new Statistic(2, 2, 3, 5, 5, 7), equipment));

        assertEquals(5.2, specificStatisticInFightsCounter.countUserStatistic(user, StatisticType.INTELLIGENCE));
        assertEquals(10.5, specificStatisticInFightsCounter.countUserStatistic(user, StatisticType.ARMOR));
        assertEquals(9.5, specificStatisticInFightsCounter.countUserStatistic(user, StatisticType.CRITIC_CHANCE));
        assertEquals(12.7, specificStatisticInFightsCounter.countUserStatistic(user, StatisticType.MAGIC_RESIST));
        assertEquals(2.19, specificStatisticInFightsCounter.countUserStatistic(user, StatisticType.STRENGTH));
        assertEquals(21.9, specificStatisticInFightsCounter.countUserStatistic(user, StatisticType.VITALITY));
    }

}