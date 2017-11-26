package integration.game.mightywarriors.services.backgroundTasks;

import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.League;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.services.backgroundTasks.DivisionAssinger;
import integration.game.mightywarriors.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class DivisionAssingerTest extends IntegrationTestsConfig {
    @Autowired
    private DivisionAssinger divisionAssinger;
    @Autowired
    private UserService userService;
    @Autowired
    private DivisionService divisionService;

    @Before
    public void setUp() throws Exception {
        userService.save(setUpUser1());
        userService.save(setUpUser2());
        userService.save(setUpUser3());
    }

    @Test
    public void assignUsersDivisions() throws Exception {
        divisionAssinger.assignUsersDivisions();

        assertEquals(1, divisionService.findByLeague(League.CHALLENGER).getUsers().size());
        assertEquals(1, divisionService.findByLeague(League.DIAMOND).getUsers().size());
        assertEquals(1, divisionService.findByLeague(League.BRONZE).getUsers().size());
    }

    private User setUpUser1() throws Exception {
        User user = new User("boss", "boss", "boss");

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

        user.getChampions().add(new Champion(new Statistic(25, 500, 50, 55, 51, 57), equipment).setLevel(10));
        user.getChampions().add(new Champion(new Statistic(25, 22, 50, 55, 51, 57), equipment).setLevel(10));
        user.getChampions().add(new Champion(new Statistic(25, 22, 50, 55, 51, 57), equipment).setLevel(100));

        return user;
    }

    private User setUpUser2() throws Exception {
        User user = new User("example", "example", "example");

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

        user.getChampions().add(new Champion(new Statistic(1, 1, 60, 0, 1, 1), equipment).setLevel(30));

        return user;
    }

    private User setUpUser3() throws Exception {
        User user = new User("medium", "medium", "medium");

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

        user.getChampions().add(new Champion(new Statistic(11, 11, 50, 10, 11, 11), equipment).setLevel(60));

        return user;
    }
}