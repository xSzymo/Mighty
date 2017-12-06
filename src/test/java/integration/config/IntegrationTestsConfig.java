package integration.config;

import game.mightywarriors.MightyWarriorsApplication;
import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

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

    @BeforeClass
    public static void setUpBefore() {
        SystemVariablesManager.JWTTokenCollection.clear();
        SystemVariablesManager.RUNNING_TESTS = true;
    }

    @AfterClass
    public static void CleanUpAfter() {
        SystemVariablesManager.JWTTokenCollection.clear();
        userService.deleteAll();
        championService.deleteAll();
        equipmentService.deleteAll();
        imageService.deleteAll();
        inventoryService.deleteAll();
        itemService.deleteAll();
        missionService.deleteAll();
        monsterService.deleteAll();
        shopService.deleteAll();
        statisticService.deleteAll();
        userRoleService.deleteAll();

        assertEquals(0, userService.findAll().size());
        assertEquals(0, championService.findAll().size());
        assertEquals(6, divisionService.findAll().size());
        assertEquals(0, equipmentService.findAll().size());
        assertEquals(0, inventoryService.findAll().size());
        assertEquals(0, itemService.findAll().size());
        assertEquals(0, missionService.findAll().size());
        assertEquals(0, monsterService.findAll().size());
        assertEquals(0, shopService.findAll().size());
        assertEquals(0, statisticService.findAll().size());
        assertEquals(0, userRoleService.findAll().size());
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
}
