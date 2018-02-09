package advanced.integration.config;

import game.mightywarriors.MightyWarriorsApplication;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.DungeonService;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MightyWarriorsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application.properties")
public abstract class AdvancedIntegrationTestsConfig {
    private static ItemService itemService;
    private static MissionService missionService;
    private static DungeonService dungeonService;
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
        dungeon.setNumber(1);
        dungeon.setName("dungeon 1");
        for(int i = 1; i <= SystemVariablesManager.MAX_FLOORS_PER_DUNGEON; i++) {
            monster = new Monster(new Statistic(1 + i, 1 + i, 1, 1, 1, 1));
            dungeon.getFloors().add(new Floor(5 + i, 1, i, new BigDecimal("5"), new HashSet<>(Collections.singleton(monster)), itemService.find(i), new Image()));
        }
        dungeonService.save(dungeon);

        dungeon = new Dungeon();
        dungeon.setNumber(2);
        dungeon.setName("dungeon 2");
        for(int i = 1; i <= SystemVariablesManager.MAX_FLOORS_PER_DUNGEON; i++) {
            monster = new Monster(new Statistic(1 + i, 1, 1 + i, 1, 1, 1));
            dungeon.getFloors().add(new Floor(20 + i, 1, i, new BigDecimal("10"), new HashSet<>(Collections.singleton(monster)), null, new Image()));
        }
        dungeonService.save(dungeon);
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
