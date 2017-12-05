package unit.game.mightywarriors.services.league;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.casters.FighterModelCaster;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.services.league.PointsForDivisionCounter;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class PointsForDivisionCounterTest {

    private PointsForDivisionCounter pointsForDivisionCounter;
    private FighterModelCaster caster;

    @Before
    public void setUp() {
        pointsForDivisionCounter = new PointsForDivisionCounter();
        caster = new FighterModelCaster();
    }

    @Test
    public void checkIsUserStrongerThanMonster() throws Exception {
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

        assertEquals(47.4, pointsForDivisionCounter.getPointsOfFighterPower(user));
    }

    @Test
    public void checkWhoIsStronger() throws Exception {
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

        assertEquals(47.4, pointsForDivisionCounter.getPointsOfFighterPower(user.getChampions().get(0)));
    }
}
