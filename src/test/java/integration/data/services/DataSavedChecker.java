package integration.data.services;


import game.mightywarriors.data.services.RoleService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import integration.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DataSavedChecker extends IntegrationTestsConfig {
    @Autowired
    private UserService objectUnderTest;
    @Autowired
    private RoleService roleService;

    private HashSet<User> users;
    private LinkedList<Mission> missions;
    private LinkedList<Champion> champions;
    private LinkedList<Image> images;
    private LinkedList<Statistic> statistics;
    private LinkedList<Monster> monsters;
    private LinkedList<Shop> shops;
    private HashSet<Item> items;
    private User user;

    private static Long id;

    @Before
    public void beforeEachTest() {
        users = new HashSet<>();
        missions = new LinkedList<>();
        champions = new LinkedList<>();
        images = new LinkedList<>();
        statistics = new LinkedList<>();
        monsters = new LinkedList<>();
        shops = new LinkedList<>();
        items = new HashSet<>();

        addExampleDataToEquipments();
    }


    @Test
    @Transactional
    public void save_check_basic_variables() {
        if (roleService.find("user") == null)
            roleService.save(new Role("user"));
        user = new User("halu", "halu", "halu@gmail.com").addChampion(new Champion());

        objectUnderTest.save(user);
        User one = objectUnderTest.find(user);
        id = user.getId();

        check(one, "0");
    }

    @Test
    @Transactional
    public void save_check_basic_variables123() {
        User one = objectUnderTest.find(id);

        check(one, "0.00");
    }

    private void check(User user, String gold) {
        assertEquals(1, user.getChampions().size());
        assertEquals(1, user.getChampions().iterator().next().getLevel());
        assertEquals(1, user.getChampions().iterator().next().getExperience());
        assertEquals(null, user.getChampions().iterator().next().getBlockUntil());
        assertNotNull(user.getChampions().iterator().next().getEquipment());
        assertNotNull(user.getChampions().iterator().next().getStatistic());

        assertEquals(new BigDecimal(gold), user.getGold());
        assertNull(null, user.getImage());

        assertNotNull(user.getInventory());
        assertEquals(0, user.getInventory().getItems().size());

        assertNotNull(user.getMissions());
        assertEquals(3, user.getMissions().size());

        assertNotNull(user.getShop());
        assertEquals(10, user.getShop().getItems().size());

        assertEquals("user", user.getRole().getRole());
    }

    private void addExampleDataToEquipments() {
        Statistic statistic;
        Shop shop;
        User user;
        HashSet<Item> myItems = new HashSet<>();

        for (int i = 3; i < 6; i++) {
            myItems.clear();
            shop = new Shop();
            statistic = new Statistic(i * i, i * i, i * i, i * i, i * i, i * i);

            myItems.add(new Item("name" + i, ItemType.WEAPON, statistic, i));
            myItems.add(new Item("name" + i, ItemType.ARMOR, statistic, i));
            myItems.add(new Item("name" + i, ItemType.BOOTS, statistic, i));
            myItems.add(new Item("name" + i, ItemType.BRACELET, statistic, i));
            myItems.add(new Item("name" + i, ItemType.GLOVES, statistic, i));
            myItems.add(new Item("name" + i, ItemType.HELMET, statistic, i));
            myItems.add(new Item("name" + i, ItemType.LEGS, statistic, i));
            myItems.add(new Item("name" + i, ItemType.NECKLACE, statistic, i));
            myItems.add(new Item("name" + i, ItemType.OFFHAND, statistic, i));
            myItems.add(new Item("name" + i, ItemType.RING, statistic, i));

            items.addAll(myItems);
            shop.setItems(myItems);
            shops.add(shop);
        }

        for (int i = 0; i < 9; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            monsters.add(new Monster(statistics.get(i), images.get(i)));
            missions.add(new Mission(1, "", new BigDecimal("1"), monsters.get(i)));
        }

        for (int i = 0; i < 9; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            champions.add(new Champion(statistics.get(i), images.get(i)));
        }

        for (int a = 0, b = 0, i = 0; i < 3; i++) {
            user = new User("login" + i, "password", "eMail");
            user.setShop(shops.get(i));
            user.addChampion(champions.get(a++));
            user.addChampion(champions.get(a++));
            user.addChampion(champions.get(a++));
            user.getMissions().add(missions.get(b++));
            user.getMissions().add(missions.get(b++));
            user.getMissions().add(missions.get(b++));

            users.add(user);
        }
    }
}
