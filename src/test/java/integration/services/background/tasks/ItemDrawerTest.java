package integration.services.background.tasks;

import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
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

            for (int j = 0; j < 2; j++) {
                int level = 10;
                for (int i = 3; i < 100; i++) {
                    if (i == level)
                        level += 10;

                    User user = new User(j + "" + i, "", "");
                    userService.save(user);
                    user.getChampions().iterator().next().setLevel(level / 10);
                    userService.save(user);

                    itemService.save(new Item("weapon " + i, ItemType.WEAPON, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                    itemService.save(new Item("weapon " + i, ItemType.BOOTS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                    itemService.save(new Item("weapon " + i, ItemType.BRACELET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                    itemService.save(new Item("weapon " + i, ItemType.GLOVES, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                    itemService.save(new Item("weapon " + i, ItemType.HELMET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                    itemService.save(new Item("weapon " + i, ItemType.LEGS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                    itemService.save(new Item("weapon " + i, ItemType.NECKLACE, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                    itemService.save(new Item("weapon " + i, ItemType.OFFHAND, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                    itemService.save(new Item("weapon " + i, ItemType.RING, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));
                    itemService.save(new Item("weapon " + i, ItemType.ARMOR, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), level / 10));

                }
            }
        }
    }

    @Test
    @Transactional
    public void drawItemsForUsers() {
        objectUnderTest.drawItemsForUser();

        userService.findAll().forEach(x -> {
            if (x.getChampions().size() == 1)
                assertEquals(10, x.getShop().getItems().size());
            if (x.getChampions().size() == 2)
                assertEquals(20, x.getShop().getItems().size());
            if (x.getChampions().size() == 3)
                assertEquals(30, x.getShop().getItems().size());
        });
    }

    @Test
    @Transactional
    public void drawItemsForUser_oneUser_with_1_champion() {
        User user = userService.find("03");
        user.getShop().getItems().clear();
        userService.save(user);
        user = userService.find(user.getId());
        assertEquals(0, user.getShop().getItems().size());
        assertEquals(1, user.getChampions().size());

        objectUnderTest.drawItemsForUser(user.getId());

        Shop shop = userService.find(user.getId()).getShop();
        assertEquals(10, shop.getItems().size());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.WEAPON.getType())).count());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.ARMOR.getType())).count());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.BOOTS.getType())).count());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.BRACELET.getType())).count());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.GLOVES.getType())).count());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.HELMET.getType())).count());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.LEGS.getType())).count());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.NECKLACE.getType())).count());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.OFFHAND.getType())).count());
        assertEquals(1, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.RING.getType())).count());
    }

    @Test
    @Transactional
    public void drawItemsForUser_oneUser_with_2_champions() {
        User user = userService.find("04");
        user.addChampion(new Champion());
        userService.save(user);
        user = userService.find("04");
        user.getShop().getItems().clear();
        userService.save(user);
        user = userService.find(user.getId());
        assertEquals(0, user.getShop().getItems().size());
        assertEquals(2, user.getChampions().size());

        objectUnderTest.drawItemsForUser(user.getId());

        Shop shop = userService.find(user.getId()).getShop();
        assertEquals(20, shop.getItems().size());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.WEAPON.getType())).count());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.ARMOR.getType())).count());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.BOOTS.getType())).count());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.BRACELET.getType())).count());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.GLOVES.getType())).count());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.HELMET.getType())).count());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.LEGS.getType())).count());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.NECKLACE.getType())).count());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.OFFHAND.getType())).count());
        assertEquals(2, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.RING.getType())).count());
    }

    @Test
    @Transactional
    public void drawItemsForUser_oneUser_with_3_champions() {
        User user = userService.find("05");
        user.addChampion(new Champion());
        user.addChampion(new Champion());
        userService.save(user);
        user = userService.find("05");
        user.getShop().getItems().clear();
        userService.save(user);
        user = userService.find(user.getId());
        assertEquals(0, user.getShop().getItems().size());
        assertEquals(3, user.getChampions().size());

        objectUnderTest.drawItemsForUser(user.getId());

        Shop shop = userService.find(user.getId()).getShop();
        assertEquals(30, shop.getItems().size());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.WEAPON.getType())).count());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.ARMOR.getType())).count());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.BOOTS.getType())).count());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.BRACELET.getType())).count());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.GLOVES.getType())).count());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.HELMET.getType())).count());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.LEGS.getType())).count());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.NECKLACE.getType())).count());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.OFFHAND.getType())).count());
        assertEquals(3, shop.getItems().stream().filter(x -> x.getItemType().getType().equals(ItemType.RING.getType())).count());
    }
}
