package game.mightywarriors.other;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.*;
import game.mightywarriors.services.background.tasks.DivisionAssigner;
import game.mightywarriors.web.rest.mighty.bookmarks.guild.GuildController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RunAtStart {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DivisionAssigner divisionAssigner;
    @Autowired
    private DivisionService divisionService;
    @Autowired
    private DungeonService dungeonService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private GuildController guildController;
    @Autowired
    private GuildService guildService;

    private HashSet<User> users = new HashSet<>();
    private LinkedList<Mission> missions = new LinkedList<>();
    private LinkedList<Mission> missions1 = new LinkedList<>();
    private LinkedList<Champion> champions = new LinkedList<>();
    private LinkedList<Image> images = new LinkedList<>();
    private LinkedList<Statistic> statistics = new LinkedList<>();
    private LinkedList<Monster> monsters = new LinkedList<>();
    private LinkedList<Shop> shops = new LinkedList<>();
    private LinkedList<Item> items = new LinkedList<>();
    private LinkedList<Equipment> equipments = new LinkedList<>();
    private LinkedList<Dungeon> dungeons = new LinkedList<>();

    @PostConstruct
    public void addExampleDataForTestAtStartApplication() throws Exception {
        if (!SystemVariablesManager.INSERT_EXAMPLE_DATA)
            return;

        Role admin_role = roleService.find("admin");
        Role user_role = roleService.find("user");
        roleService.save(admin_role);
        roleService.save(user_role);

        LinkedList<Item> myItems = new LinkedList<>();
        Statistic statistic;
        Shop shop;
        User user;
        Equipment equipment;

        for (int a, b, i = 3; i < 15; i++) {
            b = i;
            i /= 2.5;
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

            i = b;
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

        for (int i = 0; i < 30; i++) {
            missions1.add(new Mission(30 + i, "", new BigDecimal("1"), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(i)).setImage(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((5)));
            missions1.add(new Mission(40 + i, "", new BigDecimal("10"), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(i)).setImage(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((5)));
            missions1.add(new Mission(50 + i, "", new BigDecimal("100"), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(i)).setImage(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((5)));
        }

        if (SystemVariablesManager.ADD_SAMPLE_DUNGEONS)
            for (int i = 0; i < 10; i++) {
                HashSet floors = new HashSet();

                //make it works
                int a = 0;
                HashSet<Monster> objects = new HashSet<>();
                objects.add(monsters.get(i));
                floors.add(new Floor(i * 5 + a++ + 1, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));
                floors.add(new Floor(i * 5 + a++ + 2, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));
                floors.add(new Floor(i * 5 + a++ + 3, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));
                floors.add(new Floor(i * 5 + a++ + 4, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));
                floors.add(new Floor(i * 5 + a++ + 5, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));
                floors.add(new Floor(i * 5 + a++ + 6, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));
                floors.add(new Floor(i * 5 + a++ + 7, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));
                floors.add(new Floor(i * 5 + a++ + 8, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));
                floors.add(new Floor(i * 5 + a++ + 9, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));
                floors.add(new Floor(i * 5 + a++ + 10, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), items.get(i), null));

                Dungeon dungeon = new Dungeon("dungeon " + i, i, new Image(), floors);
                dungeonService.save(dungeon);
                dungeons.add(dungeon);
            }

        for (int i = 0; i < 12; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://beaver.blox.pl/resource/bober22.jpg"));
            if (i > 6)
                champions.add(new Champion(statistics.get(i), images.get(i), equipments.get(i)).setLevel(i * 2));
            else
                champions.add(new Champion(statistics.get(i), images.get(i), equipments.get(i)).setLevel(i * 4 + 1));
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
            if (SystemVariablesManager.ADD_SAMPLE_DUNGEONS)
                user.setDungeon(new UserDungeon(dungeons.get(i)));
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

        HashSet<User> all = userService.findAll();
        all.forEach(x -> x.setDungeonPoints(10));
        userService.save(all);

        divisionAssigner.assignUsersDivisions();
        Division division = divisionService.find(League.WOOD);
        division.getUsers().add(userService.find("admin0"));
        divisionService.save(division);

        missionService.save(missions1);

        user = userService.find("admin0");
        Role role = roleService.find(GuildRole.OWNER.getRole());
        role.getUsers().add(user);
        roleService.save(role);

        Chat chat = new Chat();
        chat.getAdmins().add(new Admin(ChatRole.OWNER, user.getLogin()));
        Guild guild = new Guild("test");
        guild.setMinimumLevel(1);
        guild.addUser(user);
        guild.setChat(chat);
        guildService.save(guild);
        user.setRole(roleService.find(role));
        user.setGuild(guild);
        user.addChat(chat);
        userService.save(user);

        User invitedUser = userService.find("user1");
        guild = user.getGuild();
        invitedUser.setGuild(guild);
        invitedUser.getChats().add(user.getGuild().getChat());
        guild.addUser(invitedUser);
        userService.save(invitedUser);

        invitedUser = userService.find("user3");
        guild = user.getGuild();
        invitedUser.setGuild(guild);
        invitedUser.getChats().add(user.getGuild().getChat());
        guild.addUser(invitedUser);
        userService.save(invitedUser);
    }
}
