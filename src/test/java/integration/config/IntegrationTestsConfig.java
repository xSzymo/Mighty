package integration.config;

import game.mightywarriors.MightyWarriorsApplication;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.other.enums.WeaponType;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
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
    private static UserRoleService userRoleService;
    private static MissionFightService missionFightService;
    private static boolean run = true;

    @BeforeClass
    public static void setUpBefore() {
        SystemVariablesManager.JWTTokenCollection.clear();
        SystemVariablesManager.INSERT_EXAMPLE_DATA = false;
    }

    @AfterClass
    @Transactional
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

        userRoleService.deleteAll();
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
        assertEquals(0, userRoleService.findAll().size());
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


        itemService.save(new Item("name" + 1, WeaponType.WEAPON, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 2, WeaponType.ARMOR, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 3, WeaponType.BOOTS, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 4, WeaponType.BRACELET, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 5, WeaponType.GLOVES, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 6, WeaponType.HELMET, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 7, WeaponType.LEGS, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 8, WeaponType.NECKLACE, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 9, WeaponType.OFFHAND, new Statistic(1, 1, 1, 1, 1, 1), 1));
        itemService.save(new Item("name" + 10, WeaponType.RING, new Statistic(1, 1, 1, 1, 1, 1), 1));
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
    public void setUserService(UserRoleService userRoleService) {
        IntegrationTestsConfig.userRoleService = userRoleService;
    }

    @Autowired
    public void setPreparedFightService(MissionFightService missionFightService) {
        IntegrationTestsConfig.missionFightService = missionFightService;
    }
}
