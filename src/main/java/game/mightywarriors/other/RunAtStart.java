package game.mightywarriors.other;

import com.github.javafaker.Faker;
import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.services.background.tasks.DivisionAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashSet;
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
        Faker faker = new Faker();
        Role admin_role = roleService.find("admin");
        Role user_role = roleService.find("user");
        roleService.save(admin_role);
        roleService.save(user_role);

        for (int i = 1; i < 100; i++) {
            itemService.save(new Item(faker.harryPotter().location(), ItemType.WEAPON, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.BOOTS, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.BRACELET, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.GLOVES, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.HELMET, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.LEGS, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.NECKLACE, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.OFFHAND, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.RING, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.ARMOR, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));

            itemService.save(new Item(faker.harryPotter().location(), ItemType.WEAPON, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.BOOTS, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.BRACELET, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.GLOVES, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.HELMET, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.LEGS, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.NECKLACE, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.OFFHAND, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.RING, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.ARMOR, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));

            itemService.save(new Item(faker.harryPotter().location(), ItemType.WEAPON, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.BOOTS, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.BRACELET, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.GLOVES, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.HELMET, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.LEGS, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.NECKLACE, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.OFFHAND, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.RING, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
            itemService.save(new Item(faker.harryPotter().location(), ItemType.ARMOR, new Statistic(i  , i , i, i , i, i), i).setGold(new BigDecimal(i * 10)).setDescription(faker.gameOfThrones().quote()).setLevel(i));
        }

        for (int i = 0; i < 30; i++) {
            missionService.save(new Mission( i + 1, faker.harryPotter().quote(), new BigDecimal("1"), new Monster(new Statistic(i + i, i + i, i + i, i + i, i + i, i + i)).setLevel(i)).setImageLight(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((i * i )));
            missionService.save(new Mission( i + 1, faker.harryPotter().quote(), new BigDecimal("1"), new Monster(new Statistic(i + i, i + i, i + i, i + i, i + i, i + i)).setLevel(i)).setImageLight(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((i * i )));
            missionService.save(new Mission( i + 1, faker.harryPotter().quote(), new BigDecimal("1"), new Monster(new Statistic(i + i, i + i, i + i, i + i, i + i, i + i)).setLevel(i)).setImageLight(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((i * i )));
            missionService.save(new Mission( i + 1, faker.harryPotter().quote(), new BigDecimal("1"), new Monster(new Statistic(i + i, i + i, i + i, i + i, i + i, i + i)).setLevel(i)).setImageLight(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((i * i )));
            missionService.save(new Mission( i + 1, faker.harryPotter().quote(), new BigDecimal("1"), new Monster(new Statistic(i + i, i + i, i + i, i + i, i + i, i + i)).setLevel(i)).setImageLight(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((i * i )));
        }

        for (int i = 0; i < 10; i++) {
            HashSet floors = new HashSet();

            int a = 0;
            floors.add(new Floor(i * 5 + a++ + 1, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));
            floors.add(new Floor(i * 5 + a++ + 2, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));
            floors.add(new Floor(i * 5 + a++ + 3, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));
            floors.add(new Floor(i * 5 + a++ + 4, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));
            floors.add(new Floor(i * 5 + a++ + 5, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));
            floors.add(new Floor(i * 5 + a++ + 6, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));
            floors.add(new Floor(i * 5 + a++ + 7, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));
            floors.add(new Floor(i * 5 + a++ + 8, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));
            floors.add(new Floor(i * 5 + a++ + 9, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));
            floors.add(new Floor(i * 5 + a++ + 10, i * 10 + a, a, new BigDecimal(Integer.toString(i * 100 + a)), Stream.of(new Monster(new Statistic(), null)).collect(Collectors.toSet()), itemService.find(i), null));

            Dungeon dungeon = new Dungeon(faker.gameOfThrones().character(), i, new Image(), floors);
            dungeonService.save(dungeon);
        }
    }
}
