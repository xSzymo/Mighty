package integration.data.services;


import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.WeaponType;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.LinkedList;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceTest extends IntegrationTestsConfig {
    @Autowired
    private UserService objectUnderTest;
    @Autowired
    private MissionService missionService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ChampionService championService;
    @Autowired
    private UserRoleService userRoleService;

    private LinkedList<User> users;
    private LinkedList<Mission> missions;
    private LinkedList<Champion> champions;
    private LinkedList<Image> images;
    private LinkedList<Statistic> statistics;
    private LinkedList<Monster> monsters;
    private LinkedList<Shop> shops;
    private LinkedList<Item> items;
    private Shop shop;
    private Champion champion;
    private Mission mission;

    @Before
    public void beforeEachTest() {
        users = new LinkedList<>();
        missions = new LinkedList<>();
        champions = new LinkedList<>();
        images = new LinkedList<>();
        statistics = new LinkedList<>();
        monsters = new LinkedList<>();
        shops = new LinkedList<>();
        items = new LinkedList<>();
        addExampleDataToEquipments();
    }

    @After
    public void afterEachTest() {
        if (shop != null)
            shopService.delete(shop);
        if (champion != null)
            championService.delete(champion);
        if (mission != null)
            missionService.delete(mission);

        monsters.forEach(monsterService::delete);
        items.forEach(itemService::delete);
        missions.forEach(missionService::delete);
        users.forEach(objectUnderTest::delete);

        assertEquals(0, monsterService.findAll().size());
        assertEquals(0, itemService.findAll().size());
        assertEquals(0, missionService.findAll().size());
        assertEquals(0, monsterService.findAll().size());
        assertEquals(0, objectUnderTest.findAll().size());
    }

    @Test
    public void save() {
        objectUnderTest.save(users.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(users);

        long counter = objectUnderTest.findAll().size();

        assertEquals(3, counter);
    }

    @Test
    public void findOne() {
        objectUnderTest.save(users.getFirst());

        assertNotNull(objectUnderTest.findOne(users.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(users.getFirst());

        assertNotNull(objectUnderTest.findOne(users.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(users);

        long counter = objectUnderTest.findAll().size();

        assertEquals(3, counter);
    }

    @Test
    public void delete() {
        objectUnderTest.save(users.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(users.getFirst());

        counter = objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void delete1() {
        objectUnderTest.save(users.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(users.getFirst().getId());

        counter = objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void delete2() {
        objectUnderTest.save(users);

        long counter = objectUnderTest.findAll().size();

        assertEquals(3, counter);

        objectUnderTest.delete(users);

        counter = objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void deleteFromShop() {
        shop = new Shop();
        shop.setItems(items);
        User user = users.getFirst();
        user.setShop(shop);

        objectUnderTest.save(user);

        assertNotNull(shopService.findOne(shop));
        assertNotNull(objectUnderTest.findOne(user));

        objectUnderTest.delete(user);

        assertNull(shopService.findOne(shop));
        assertNull(objectUnderTest.findOne(user));
    }

    @Test
    public void deleteFromChampion() {
        champion = new Champion();
        User user = new User("simple login" + System.currentTimeMillis());
        user.addChampion(champion);

        objectUnderTest.save(user);

        assertNotNull(championService.findOne(champion));
        assertNotNull(objectUnderTest.findOne(user));

        objectUnderTest.delete(user);

        assertNull(championService.findOne(champion));
        assertNull(objectUnderTest.findOne(user));
    }

    @Test
    public void deleteFromMission() {
        mission = new Mission();
        User user = new User("simple login" + System.currentTimeMillis());
        user.addMission(mission);

        objectUnderTest.save(user);

        assertNotNull(missionService.findOne(mission));
        assertNotNull(objectUnderTest.findOne(user));

        objectUnderTest.delete(user);

        assertNull(championService.findOne(champion));
        assertNull(objectUnderTest.findOne(user));
    }

    @Test
    public void deleteFromRole() {
        UserRole userRole = new UserRole("Admino");
        userRoleService.save(userRole);

        User user = new User("simple login" + System.currentTimeMillis());
        user.setUserRole(userRole);
        objectUnderTest.save(user);

        assertNotNull(objectUnderTest.findOne(user));

        objectUnderTest.delete(user);

        assertNull(objectUnderTest.findOne(user));
        assertNotNull(userRoleService.findOne(userRole));
    }

    private void addExampleDataToEquipments() {
        Statistic statistic;
        Shop shop;
        User user;
        LinkedList<Item> myItems = new LinkedList<>();

        for (int a = 0, i = 3; i < 6; i++) {
            myItems.clear();
            shop = new Shop();
            statistic = new Statistic(i * i, i * i, i * i, i * i, i * i, i * i);

            myItems.add(new Item("name" + i, WeaponType.WEAPON, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.ARMOR, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.BOOTS, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.BRACELET, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.GLOVES, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.HELMET, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.LEGS, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.NECKLACE, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.OFFHAND, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.RING, statistic, i));

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
