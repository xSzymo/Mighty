package integration.data.services;

import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.ShopService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.WeaponType;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.*;


public class ShopServiceTest extends IntegrationTestsConfig {

    @Autowired
    private ShopService objectUnderTest;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    private LinkedList<Shop> shops;
    private LinkedList<Item> items;
    private User user;

    @Before
    public void beforeEachTest() {
        shops = new LinkedList<>();
        items = new LinkedList<>();
        addExampleDataToEquipments();
    }

    @After
    public void afterEachTest() {
        for (Item item : items) {
            itemService.delete(item);
        }
        shops.forEach(objectUnderTest::delete);
        if (user != null)
            userService.delete(user);
    }

    @Test
    public void save() {
        objectUnderTest.save(shops.getFirst());


        checkSavedItemsAreNotNull(shops.getFirst());
        assertNotNull(objectUnderTest.findOne(shops.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(shops);

        shops.forEach(this::checkSavedItemsAreNotNull);
        assertNotNull(objectUnderTest.findOne(shops.get(0)));
        assertNotNull(objectUnderTest.findOne(shops.get(1)));
        assertNotNull(objectUnderTest.findOne(shops.get(2)));
        assertNotNull(objectUnderTest.findOne(shops.get(3)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(shops.getFirst());

        checkSavedItemsAreNotNull(shops.getFirst());
        assertNotNull(objectUnderTest.findOne(shops.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(shops.getFirst());

        checkSavedItemsAreNotNull(shops.getFirst());
        assertNotNull(objectUnderTest.findOne(shops.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(shops);

        shops.forEach(this::checkSavedItemsAreNotNull);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(shops.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(shops.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(shops.get(2).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(shops.get(3).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(shops.getFirst());

        assertNotNull(objectUnderTest.findOne(shops.getFirst()));

        objectUnderTest.delete(shops.getFirst());

        checkSavedItemsAreNotNull(shops.getFirst());
        assertNull(objectUnderTest.findOne(shops.getFirst()));
    }

    @Test
    public void delete1() {
        objectUnderTest.save(shops.getFirst());

        assertNotNull(objectUnderTest.findOne(shops.getFirst()));

        objectUnderTest.delete(shops.getFirst().getId());


        checkSavedItemsAreNotNull(shops.getFirst());
        assertNull(objectUnderTest.findOne(shops.getFirst()));
    }

    @Test
    public void delete2() {
        objectUnderTest.save(shops);

        assertNotNull(objectUnderTest.findOne(shops.get(0)));
        assertNotNull(objectUnderTest.findOne(shops.get(1)));
        assertNotNull(objectUnderTest.findOne(shops.get(2)));

        objectUnderTest.delete(shops);

        assertNull(objectUnderTest.findOne(shops.get(0)));
        assertNull(objectUnderTest.findOne(shops.get(1)));
        assertNull(objectUnderTest.findOne(shops.get(2)));
        shops.forEach(this::checkSavedItemsAreNotNull);
    }

    @Test
    public void deleteFromUser() {
        user = new User("simple login");
        user.setShop(shops.getFirst());

        userService.save(user);

        assertNotNull(userService.findOne(user));
        assertNotNull(objectUnderTest.findOne(shops.getFirst()));

        objectUnderTest.delete(shops.getFirst());

        assertNotNull(userService.findOne(user.getId()));
        assertNotEquals(shops.getFirst(), userService.findOne(user.getId()).getShop());
        assertNull(objectUnderTest.findOne(shops.getFirst()));
    }

    private void checkSavedItemsAreNotNull(Shop shop) {
        shop.getItems().forEach(Assert::assertNotNull);
    }

    private void addExampleDataToEquipments() {
        Statistic statistic;
        Shop shop;
        LinkedList<Item> myItems = new LinkedList<>();

        for (int a = 0, i = 3; i < 7; i++) {
            myItems.clear();
            shop = new Shop();
            statistic = new Statistic(i * i, i * i, i * i, i * i, i * i, i * i);

            myItems.add(new Item("name" + i, WeaponType.WEAPON, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.ARMOR, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.BOOTS, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.BRACELET, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.GLOVES, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.HELMET, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.LEGS, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.NECKLACE, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.OFFHAND, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.RING, statistic, i));

            items.addAll(myItems);
            shop.setItems(myItems);
            shops.add(shop);
        }
    }
}
