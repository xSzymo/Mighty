package integration.config;

import game.mightywarriors.MightyWarriorsApplication;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MightyWarriorsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application.properties")
public abstract class IntegrationTestsConfig {
    private static UserService userService;
    private static ChampionService championService;
    private static DivisionService divisionService;
    private static EquipmentService equipmentService;
    private static ImageService imageService;
    private static InventoryService inventoryService;
    private static ItemService itemService;
    private static MissionService missionService;
    private static MonsterService monsterService;
    private static ShopService shopService;
    private static StatisticService statisticService;
    private static RoleService roleService;
    private static MissionFightService missionFightService;
    private static FloorService floorService;
    private static DungeonService dungeonService;
    private static UserDungeonService userDungeonService;
    private static boolean run = true;

    @BeforeClass
    public static void setUpBefore() {
        SystemVariablesManager.JWTTokenCollection.clear();
        SystemVariablesManager.INSERT_EXAMPLE_DATA = false;
    }

    @AfterClass
    @Deprecated
    public static void cleanUpAfter() {
        run = true;
        SystemVariablesManager.JWTTokenCollection.clear();

        userService.deleteAll();
        equipmentService.deleteAll();
        inventoryService.deleteAll();
        championService.deleteAll();

        missionService.deleteAll();
        monsterService.deleteAll();

        missionFightService.deleteAll();
        shopService.deleteAll();
        itemService.deleteAll();

        floorService.deleteAll();
        dungeonService.deleteAll();
        userDungeonService.deleteAll();

        roleService.deleteAll();
        statisticService.deleteAll();
        imageService.deleteAll();

        assertEquals(0, userService.findAll().size());
        assertEquals(0, missionFightService.findAll().size());
        assertEquals(0, championService.findAll().size());
        assertEquals(0, equipmentService.findAll().size());
        assertEquals(0, inventoryService.findAll().size());
        assertEquals(0, itemService.findAll().size());
        assertEquals(0, missionService.findAll().size());
        assertEquals(0, monsterService.findAll().size());
        assertEquals(0, shopService.findAll().size());
        assertEquals(0, statisticService.findAll().size());
        assertEquals(0, roleService.findAll().size());
        assertEquals(6, divisionService.findAll().size());
    }

    @Before
    public void createMonstersAndItems() {
        if (!run)
            return;
        else
            run = false;

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
        dungeon.getFloors().add(new Floor());
        dungeonService.save(dungeon);
    }

    @Autowired
    public void setUserService(UserService userService) {
        IntegrationTestsConfig.userService = userService;
    }

    @Autowired
    public void setMonsterService(MonsterService monsterService) {
        IntegrationTestsConfig.monsterService = monsterService;
    }

    @Autowired
    public void setEquipmentService(EquipmentService equipmentService) {
        IntegrationTestsConfig.equipmentService = equipmentService;
    }

    @Autowired
    public void setChampionService(ChampionService championService) {
        IntegrationTestsConfig.championService = championService;
    }

    @Autowired
    public void setDivisionService(DivisionService divisionService) {
        IntegrationTestsConfig.divisionService = divisionService;
    }

    @Autowired
    public void setImageService(ImageService imageService) {
        IntegrationTestsConfig.imageService = imageService;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        IntegrationTestsConfig.inventoryService = inventoryService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        IntegrationTestsConfig.itemService = itemService;
    }

    @Autowired
    public void setMissionService(MissionService missionService) {
        IntegrationTestsConfig.missionService = missionService;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        IntegrationTestsConfig.shopService = shopService;
    }

    @Autowired
    public void setStatisticService(StatisticService statisticService) {
        IntegrationTestsConfig.statisticService = statisticService;
    }

    @Autowired
    public void setUserService(RoleService roleService) {
        IntegrationTestsConfig.roleService = roleService;
    }

    @Autowired
    public void setPreparedFightService(MissionFightService missionFightService) {
        IntegrationTestsConfig.missionFightService = missionFightService;
    }

    @Autowired
    public void setDungeonService(DungeonService dungeonService) {
        IntegrationTestsConfig.dungeonService = dungeonService;
    }

    @Autowired
    public void setUserDungeonService(UserDungeonService userDungeonService) {
        IntegrationTestsConfig.userDungeonService = userDungeonService;
    }

    @Autowired
    public void setFloorService(FloorService floorService) {
        IntegrationTestsConfig.floorService = floorService;
    }
}
