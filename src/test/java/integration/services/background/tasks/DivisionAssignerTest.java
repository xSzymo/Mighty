package integration.services.background.tasks;

import game.mightywarriors.configuration.system.SystemFightsVariablesManager;
import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.League;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.services.background.tasks.DivisionAssigner;
import integration.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

public class DivisionAssignerTest extends IntegrationTestsConfig {
    @Autowired
    private DivisionAssigner divisionAssigner;
    @Autowired
    private UserService userService;
    @Autowired
    private DivisionService divisionService;

    @Before
    public void setUp() throws Exception {
        userService.save(setUpUser1("boss1"));
        for (int i = 2; i < 10; i++)
            userService.save(setUpUser1("boss" + i));
        for (int i = 2; i < 10; i++)
            userService.save(setUpUser2("low" + i));
        userService.save(setUpUser3());
    }

    @Test
    @Transactional
    public void assignUsersDivisions() {
        divisionAssigner.assignUsersDivisions();

        assertEquals(2, divisionService.findByLeague(League.CHALLENGER).getUsers().size());
        assertEquals(5, divisionService.findByLeague(League.DIAMOND).getUsers().size());
        assertEquals(2, divisionService.findByLeague(League.GOLD).getUsers().size());
        assertEquals(4, divisionService.findByLeague(League.SILVER).getUsers().size());
        assertEquals(5, divisionService.findByLeague(League.BRONZE).getUsers().size());
        assertEquals(0, divisionService.findByLeague(League.WOOD).getUsers().size());

        assertEquals(0, divisionService.findByLeague(League.CHALLENGER).getUsers().stream().
                filter(x -> userService.findByLogin(x.getLogin()).getUserChampiongHighestLevel() < SystemFightsVariablesManager.MIN_LEVEL_FOR_CHALLENGER).count());
    }

    private User setUpUser1(String login) throws Exception {
        User user = new User(login, "boss", "boss");

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

    private User setUpUser2(String login) throws Exception {
        User user = new User(login, "example", "example");

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

        user.getChampions().add(new Champion(new Statistic(1, 1, 60, 0, 1, 1), equipment).setLevel(45));

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