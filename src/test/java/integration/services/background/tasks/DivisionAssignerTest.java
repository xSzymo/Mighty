package integration.services.background.tasks;

import game.mightywarriors.configuration.system.variables.SystemFightsVariablesManager;
import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.other.enums.League;
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
        userService.save(new User("example", "", "").addChampion(new Champion().setLevel(5)));
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

        assertEquals(2, divisionService.find(League.CHALLENGER).getUsers().size());
        assertEquals(5, divisionService.find(League.DIAMOND).getUsers().size());
        assertEquals(2, divisionService.find(League.GOLD).getUsers().size());
        assertEquals(4, divisionService.find(League.SILVER).getUsers().size());
        assertEquals(5, divisionService.find(League.BRONZE).getUsers().size());
        assertEquals(0, divisionService.find(League.WOOD).getUsers().size());

        assertEquals(0, divisionService.find(League.CHALLENGER).getUsers().stream().
                filter(x -> userService.find(x.getLogin()).getUserChampionHighestLevel() < SystemFightsVariablesManager.MIN_LEVEL_FOR_CHALLENGER).count());
    }

    private User setUpUser1(String login) throws Exception {
        User user = new User(login, "boss", "boss");

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

    private User setUpUser2(String login) throws Exception {
        User user = new User(login, "example", "example");

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

        user.getChampions().add(new Champion(new Statistic(1, 1, 60, 0, 1, 1), equipment).setLevel(45));

        return user;
    }

    private User setUpUser3() throws Exception {
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