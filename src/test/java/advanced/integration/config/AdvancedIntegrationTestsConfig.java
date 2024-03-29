package advanced.integration.config;

import game.mightywarriors.MightyWarriorsApplication;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.other.enums.Kingdom;
import game.mightywarriors.services.background.tasks.DivisionAssigner;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MightyWarriorsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application.properties")
public abstract class AdvancedIntegrationTestsConfig {
    private static ItemService itemService;
    private static MissionService missionService;
    private static DungeonService dungeonService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DivisionAssigner divisionAssigner;
    @Autowired
    private DivisionService divisionService;

    private HashSet<User> users = new HashSet<>();
    private LinkedList<Mission> missions = new LinkedList<>();
    private LinkedList<Champion> champions = new LinkedList<>();
    private LinkedList<Image> images = new LinkedList<>();
    private LinkedList<Statistic> statistics = new LinkedList<>();
    private LinkedList<Monster> monsters = new LinkedList<>();
    private LinkedList<Shop> shops = new LinkedList<>();
    private LinkedList<Item> items = new LinkedList<>();
    private LinkedList<Equipment> equipments = new LinkedList<>();
    private LinkedList<Dungeon> dungeons = new LinkedList<>();
    private static boolean run = true;

    static {
        SystemVariablesManager.ADD_SAMPLE_DUNGEONS = false;
        SystemVariablesManager.INSERT_EXAMPLE_DATA = false;
    }



    @Before
    public void createMonstersAndItems() throws Exception {
        if (!run)
            return;

        run = false;

        addExampleDataForTestAtStartApplication();

        Monster monster = new Monster(new Statistic(1, 1, 1, 1, 1, 1));
        missionService.save(new Mission(1, "", new BigDecimal("1"), monster));

        monster = new Monster(new Statistic(1, 1, 1, 1, 1, 1));
        missionService.save(new Mission(1, "", new BigDecimal("1"), monster));

        monster = new Monster(new Statistic(1, 1, 1, 1, 1, 1));
        missionService.save(new Mission(1, "", new BigDecimal("1"), monster));


        itemService.save(new Item("name" + 1, ItemType.WEAPON, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 2, ItemType.ARMOR, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 3, ItemType.BOOTS, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 4, ItemType.BRACELET, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 5, ItemType.GLOVES, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 6, ItemType.HELMET, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 7, ItemType.LEGS, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 8, ItemType.NECKLACE, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 9, ItemType.OFFHAND, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 10, ItemType.RING, new Statistic(1, 1, 1, 1, 1, 1), 1));


        Dungeon dungeon = new Dungeon();
        dungeon.setStage(1);
        dungeon.setName("dungeon 1");
        for (int i = 1; i <= SystemVariablesManager.MAX_FLOORS_PER_DUNGEON; i++) {
            monster = new Monster(new Statistic(1 + i, 1 + i, 1, 1, 1, 1));
            dungeon.getFloors().add(new Floor(5 + i, 1, i, new BigDecimal("5"), new HashSet<>(Collections.singleton(monster)), itemService.find(i), new Image()));
        }
        dungeonService.save(dungeon);

        dungeon = new Dungeon();
        dungeon.setStage(2);
        dungeon.setName("dungeon 2");
        for (int i = 1; i <= SystemVariablesManager.MAX_FLOORS_PER_DUNGEON; i++) {
            monster = new Monster(new Statistic(1 + i, 1, 1 + i, 1, 1, 1));
            dungeon.getFloors().add(new Floor(20 + i, 1, i, new BigDecimal("10"), new HashSet<>(Collections.singleton(monster)), null, new Image()));
        }
        dungeonService.save(dungeon);
    }


    private void addExampleDataForTestAtStartApplication() throws Exception {
        Role admin_role = roleService.find("admin");
        Role user_role = roleService.find("user");
        roleService.save(admin_role);
        roleService.save(user_role);


        LinkedList<Item> myItems = new LinkedList<>();
        Statistic statistic;
        Shop shop;
        User user;
        Equipment equipment;

        for (int a, i = 3; i < 15; i++) {
            itemService.save(new Item("weapon " + i, ItemType.WEAPON, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));
            itemService.save(new Item("weapon " + i, ItemType.BOOTS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));
            itemService.save(new Item("weapon " + i, ItemType.BRACELET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));
            itemService.save(new Item("weapon " + i, ItemType.GLOVES, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));
            itemService.save(new Item("weapon " + i, ItemType.HELMET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));
            itemService.save(new Item("weapon " + i, ItemType.LEGS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));
            itemService.save(new Item("weapon " + i, ItemType.NECKLACE, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));
            itemService.save(new Item("weapon " + i, ItemType.OFFHAND, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));
            itemService.save(new Item("weapon " + i, ItemType.RING, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));
            itemService.save(new Item("weapon " + i, ItemType.ARMOR, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1).setGold(new BigDecimal("1")));

            myItems.clear();
            shop = new Shop();
            statistic = new Statistic(i * i, i * i, i * i, i * i, i * i, i * i);

            myItems.add(new Item("name" + i, ItemType.WEAPON, statistic, i).setGold(new BigDecimal("1")));
            myItems.add(new Item("name" + i, ItemType.ARMOR, statistic, i).setGold(new BigDecimal("1")));
            myItems.add(new Item("name" + i, ItemType.BOOTS, statistic, i).setGold(new BigDecimal("1")));
            myItems.add(new Item("name" + i, ItemType.BRACELET, statistic, i).setGold(new BigDecimal("1")));
            myItems.add(new Item("name" + i, ItemType.GLOVES, statistic, i).setGold(new BigDecimal("1")));
            myItems.add(new Item("name" + i, ItemType.HELMET, statistic, i).setGold(new BigDecimal("1")));
            myItems.add(new Item("name" + i, ItemType.LEGS, statistic, i).setGold(new BigDecimal("1")));
            myItems.add(new Item("name" + i, ItemType.NECKLACE, statistic, i).setGold(new BigDecimal("1")));
            myItems.add(new Item("name" + i, ItemType.OFFHAND, statistic, i).setGold(new BigDecimal("1")));
            myItems.add(new Item("name" + i, ItemType.RING, statistic, i).setGold(new BigDecimal("1")));

            a = 0;
            equipment = new Equipment();
            equipment.setWeapon(myItems.get(a++));
            equipment.setArmor(myItems.get(a++));
            equipment.setBoots(myItems.get(a++));
            equipment.setBracelet(myItems.get(a++));
            equipment.setGloves(myItems.get(a++));
            equipment.setHelmet(myItems.get(a++));
            equipment.setLegs(myItems.get(a++));
            equipment.setNecklace(myItems.get(a++));
            equipment.setOffhand(myItems.get(a++));
            equipment.setRing(myItems.get(a));
            equipments.add(equipment);

            items.addAll(myItems);
            shop.setItems(new HashSet<>(myItems));
            shops.add(shop);
        }

        for (int i = 0; i < 12; i++) {
            statistics.add(new Statistic(2, 2, 5, 0, 2, 2));
            images.add(new Image("https://cdn.orkin.com/images/rodents/norway-rat-illustration_360x236.jpg"));
            monsters.add(new Monster(statistics.get(i), images.get(i)));
            if (i <= 5 && i > 0)
                missions.add(new Mission(1 + i, "", new BigDecimal("1"), monsters.get(i)).setImage(new Image("https://www.ufopaedia.org/images/6/62/XCOM_Base_Defense_Mission_Screen_(EU2012).png")).setDuration((10 + i * 3)));
            else if (i <= 9 && i > 5)
                missions.add(new Mission(1 + i, "", new BigDecimal("1"), monsters.get(i)).setImage(new Image("https://c1.staticflickr.com/5/4059/4485522732_f73ce8d7f7_b.jpg")).setDuration((10 + i * 3)));
            else
                missions.add(new Mission(1 + i, "", new BigDecimal("1"), monsters.get(i)).setImage(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((10 + i * 3)));
        }

        for (int i = 0; i < 12; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://beaver.blox.pl/resource/bober22.jpg"));
            champions.add(new Champion(statistics.get(i), images.get(i), equipments.get(i)));
        }

        for (int a = 0, b = 0, i = 0; i < 4; i++) {
            if (i % 2 == 0)
                user = new User("admin" + i, "admin", "email@wp.pl", admin_role).setKingdom(Kingdom.KNIGHT);
            else
                user = new User("user" + i, "user", "eMail").setKingdom(Kingdom.KNIGHT);
            Image image = new Image("https://www.reduceimages.com/img/image-after.jpg");
            images.add(image);
            user.setImage(image);
            user.setShop(shops.get(i));
            user.addChampion(champions.get(a++));
            user.addChampion(champions.get(a++));
            user.addChampion(champions.get(a++));
            user.getMissions().add(missions.get(b++));
            user.getMissions().add(missions.get(b++));
            user.getMissions().add(missions.get(b++));

            user.setAccountEnabled(true);
            user.setAccountNonLocked(true);
            user.addGold(new BigDecimal("10000"));
            users.add(user);
        }
        userService.save(users);

        users = userService.findAll();
        Iterator<User> iterator = users.iterator();
        for (int i = 0; i < 4; i++) {
            User next = iterator.next();
            next.getInventory().addItem(itemService.find(i));
            next.getInventory().addItem(itemService.find(i + 1));
            next.getInventory().addItem(itemService.find(i + 2));
        }
        userService.save(users);
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        AdvancedIntegrationTestsConfig.itemService = itemService;
    }

    @Autowired
    public void setMissionService(MissionService missionService) {
        AdvancedIntegrationTestsConfig.missionService = missionService;
    }

    @Autowired
    public void setDungeonService(DungeonService dungeonService) {
        AdvancedIntegrationTestsConfig.dungeonService = dungeonService;
    }
}
