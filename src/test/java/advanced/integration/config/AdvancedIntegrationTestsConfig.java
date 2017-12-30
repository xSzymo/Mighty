package advanced.integration.config;

import game.mightywarriors.MightyWarriorsApplication;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.other.enums.WeaponType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MightyWarriorsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application.properties")
public abstract class AdvancedIntegrationTestsConfig {
    private static ItemService itemService;
    private static MissionService missionService;
    private static boolean run = true;

    @Before
    public void createMonstersAndItems() {
        if (!run)
            return;

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

    @BeforeClass
    public static void setUpBefore() {
        SystemVariablesManager.JWTTokenCollection.clear();
        SystemVariablesManager.INSERT_EXAMPLE_DATA = true;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        AdvancedIntegrationTestsConfig.itemService = itemService;
    }

    @Autowired
    public void setMissionService(MissionService missionService) {
        AdvancedIntegrationTestsConfig.missionService = missionService;
    }
}
