package integration.services.background.tasks;

import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.services.background.tasks.ItemDrawer;
import integration.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

public class ItemDrawerTest extends IntegrationTestsConfig {
    @Autowired
    private ItemDrawer objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @Before
    public void setUp() {
        userService.save(new User("1", "", ""));
        userService.save(new User("2", "", ""));
        userService.save(new User("3", "", ""));
        for (int a, i = 3; i < 15; i++) {
            itemService.save(new Item("weapon " + i, WeaponType.WEAPON, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.BOOTS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.BRACELET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.GLOVES, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.HELMET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.LEGS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.NECKLACE, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.OFFHAND, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.RING, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.ARMOR, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
        }
    }

    @Test
    @Transactional
    public void drawItemsForUser() {
        objectUnderTest.drawItemsForUser();

        userService.findAll().forEach(x -> assertEquals(10, x.getShop().getItems().size()));
    }
}
