package integration.data.services;


import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
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

    private HashSet<User> users;
    private LinkedList<Mission> missions;
    private LinkedList<Champion> champions;
    private LinkedList<Image> images;
    private LinkedList<Statistic> statistics;
    private LinkedList<Monster> monsters;
    private LinkedList<Shop> shops;
    private HashSet<Item> items;
    private Shop shop;
    private User user;
    private Champion champion;
    private Mission mission;

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

    @After
    public void afterEachTest() {
        if (shop != null)
            shopService.delete(shop);
        if (user != null)
            objectUnderTest.delete(user);
        if (champion != null)
            championService.delete(champion);
        if (mission != null)
            missionService.delete(mission);

        monsters.forEach(monsterService::delete);
        items.forEach(itemService::delete);
        missions.forEach(missionService::delete);
        users.forEach(objectUnderTest::delete);

        assertEquals(0, objectUnderTest.findAll().size());
    }

    @Test
    public void save() {
        objectUnderTest.save(users.iterator().next());

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
        objectUnderTest.save(users.iterator().next());

        assertNotNull(objectUnderTest.find(users.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(users.iterator().next());

        assertNotNull(objectUnderTest.find(users.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(users);

        long counter = objectUnderTest.findAll().size();

        assertEquals(3, counter);
    }

    @Test
    public void delete() {
        objectUnderTest.save(users.iterator().next());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(users.iterator().next());

        counter = objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void delete1() {
        objectUnderTest.save(users.iterator().next());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(users.iterator().next().getId());

        counter = objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    @Transactional
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
        User user = users.iterator().next();
        user.setShop(shop);

        objectUnderTest.save(user);

        assertNotNull(shopService.find(shop));
        assertNotNull(objectUnderTest.find(user));

        objectUnderTest.delete(user);

        assertNull(shopService.find(shop));
        assertNull(objectUnderTest.find(user));
    }

    @Test
    public void deleteFromChampion() {
        champion = new Champion();
        User user = new User("simple login" + System.currentTimeMillis());
        user.addChampion(champion);

        objectUnderTest.save(user);

        assertNotNull(championService.find(champion));
        assertNotNull(objectUnderTest.find(user));

        objectUnderTest.delete(user);

        assertNull(championService.find(champion));
        assertNull(objectUnderTest.find(user));
    }

    @Test
    public void deleteFromMission() {
        mission = new Mission();
        User user = new User("simple login" + System.currentTimeMillis());
        user.addMission(mission);

        objectUnderTest.save(user);

        assertNotNull(missionService.find(mission));
        assertNotNull(objectUnderTest.find(user));

        objectUnderTest.delete(user);

        assertNull(championService.find(champion));
        assertNull(objectUnderTest.find(user));
    }

    @Test
    public void deleteFromRole() {
        UserRole userRole = new UserRole("Admino");
        userRoleService.save(userRole);

        User user = new User("simple login" + System.currentTimeMillis());
        user.setUserRole(userRole);
        objectUnderTest.save(user);

        User one = objectUnderTest.find(user);
        assertNotNull(one);

        objectUnderTest.delete(user);

        assertNull(objectUnderTest.find(user));
        assertNotNull(userRoleService.find(userRole));
    }

    @Test
    @Transactional
    public void save_check_basic_variables() {
        if (userRoleService.find("user") == null)
            userRoleService.save(new UserRole("user"));

        user = new User("halu", "halu", "halu@gmail.com");

        objectUnderTest.save(user);
        User one = objectUnderTest.find(user);

        assertEquals(SystemVariablesManager.ARENA_POINTS, one.getArenaPoints());
        assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_1_AND_10, one.getMissionPoints());

        assertEquals(1, one.getChampions().size());
        assertEquals(1, one.getChampions().iterator().next().getLevel());
        assertEquals(1, one.getChampions().iterator().next().getExperience());
        assertEquals(null, one.getChampions().iterator().next().getBlockUntil());
        assertNotNull(one.getChampions().iterator().next().getEquipment());
        assertNotNull(one.getChampions().iterator().next().getStatistic());

        assertEquals(new BigDecimal("0"), one.getGold());
        assertNull(null, one.getImage());

        assertNotNull(one.getInventory());
        assertEquals(0, one.getInventory().getItems().size());

        assertNotNull(one.getMissions());
        assertEquals(3, one.getMissions().size());

        assertNotNull(one.getShop());
        assertEquals(10, one.getShop().getItems().size());

        assertEquals("user", one.getUserRole().getRole());
    }

    @Test
    public void userGetsNewDungeonWith3LevelChampion() {
        User user = new User("testing", "testing", "testing");
        user.getChampions().add(new Champion(new Statistic()).setLevel(3));
        objectUnderTest.save(user);
        users.add(user);

        assertNotNull(user.getDungeon());
        assertNotNull(user.getDungeon().getDungeon());
        assertEquals(10, user.getDungeon().getCurrentFloor());
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
