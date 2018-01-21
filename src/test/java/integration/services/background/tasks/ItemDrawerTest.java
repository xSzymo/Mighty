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

    private static boolean run = true;

    @Before
    @Transactional
    public void setUp() {
        if (run) {
            run = false;

            int level = 10;
            for (int i = 3; i < 100; i++) {
                if (i == level)
                    level += 10;

                User user = new User("" + i, "", "");
                userService.save(user);
                user.getChampions().iterator().next().setLevel(level / 10);
                userService.save(user);

                itemService.save(new Item("weapon " + i, WeaponType.WEAPON, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                itemService.save(new Item("weapon " + i, WeaponType.BOOTS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                itemService.save(new Item("weapon " + i, WeaponType.BRACELET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                itemService.save(new Item("weapon " + i, WeaponType.GLOVES, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                itemService.save(new Item("weapon " + i, WeaponType.HELMET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                itemService.save(new Item("weapon " + i, WeaponType.LEGS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                itemService.save(new Item("weapon " + i, WeaponType.NECKLACE, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                itemService.save(new Item("weapon " + i, WeaponType.OFFHAND, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                itemService.save(new Item("weapon " + i, WeaponType.RING, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                itemService.save(new Item("weapon " + i, WeaponType.ARMOR, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));

            }
        }
    }

    @Test
    @Transactional
    public void drawItemsForUsers() {
        objectUnderTest.drawItemsForUser();

        userService.findAll().forEach(x -> assertEquals(10, x.getShop().getItems().size()));
    }

    @Test
    @Transactional
    public void drawItemsForUser_oneUser() {
        User user = userService.find("3");
        user.getShop().getItems().clear();
        userService.save(user);
        user = userService.find(user.getId());
        assertEquals(0, user.getShop().getItems().size());

        objectUnderTest.drawItemsForUser(user.getId());

        assertEquals(10, userService.find(user.getId()).getShop().getItems().size());
    }
}
