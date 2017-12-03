package accept.game.mightywarriors.config;

import game.mightywarriors.MightyWarriorsApplication;
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
@SpringBootTest(classes = MightyWarriorsApplication.class)
@TestPropertySource("classpath:application.properties")
public abstract class AcceptTestConfig {
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

    }

    @AfterClass
    public static void CleanUpAfter() {
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
        AcceptTestConfig.userService = userService;
    }

    @Autowired
    public void setMonsterService(MonsterService monsterService) {
        AcceptTestConfig.monsterService = monsterService;
    }

    @Autowired
    public void setEquipmentService(EquipmentService equipmentService) {
        AcceptTestConfig.equipmentService = equipmentService;
    }

    @Autowired
    public void setChampionService(ChampionService championService) {
        AcceptTestConfig.championService = championService;
    }

    @Autowired
    public void setDivisionService(DivisionService divisionService) {
        AcceptTestConfig.divisionService = divisionService;
    }

    @Autowired
    public void setImageService(ImageService imageService) {
        AcceptTestConfig.imageService = imageService;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        AcceptTestConfig.inventoryService = inventoryService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        AcceptTestConfig.itemService = itemService;
    }

    @Autowired
    public void setMissionService(MissionService missionService) {
        AcceptTestConfig.missionService = missionService;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        AcceptTestConfig.shopService = shopService;
    }

    @Autowired
    public void setStatisticService(StatisticService statisticService) {
        AcceptTestConfig.statisticService = statisticService;
    }

    @Autowired
    public void setUserService(UserRoleService userRoleService) {
        AcceptTestConfig.userRoleService = userRoleService;
    }
}
