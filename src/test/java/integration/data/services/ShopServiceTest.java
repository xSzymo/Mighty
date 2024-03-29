package integration.data.services;

import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.ShopService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ItemType;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;


public class ShopServiceTest extends IntegrationTestsConfig {

    @Autowired
    private ShopService objectUnderTest;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    private HashSet<Shop> shops;
    private HashSet<Item> items;
    private User user;

    @Before
    public void beforeEachTest() {
        shops = new HashSet<>();
        items = new HashSet<>();
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
        objectUnderTest.save(shops.iterator().next());


        checkSavedItemsAreNotNull(shops.iterator().next());
        assertNotNull(objectUnderTest.find(shops.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(shops);

        shops.forEach(this::checkSavedItemsAreNotNull);
        Iterator<Shop> iterator = shops.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(shops.iterator().next());

        checkSavedItemsAreNotNull(shops.iterator().next());
        assertNotNull(objectUnderTest.find(shops.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(shops.iterator().next());

        checkSavedItemsAreNotNull(shops.iterator().next());
        assertNotNull(objectUnderTest.find(shops.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(shops);

        shops.forEach(this::checkSavedItemsAreNotNull);

        Iterator<Shop> iterator = shops.iterator();
        List<Shop> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(shops.iterator().next());

        assertNotNull(objectUnderTest.find(shops.iterator().next()));

        objectUnderTest.delete(shops.iterator().next());

        checkSavedItemsAreNotNull(shops.iterator().next());
        assertNull(objectUnderTest.find(shops.iterator().next()));
    }

    @Test
    public void delete1() {
        objectUnderTest.save(shops.iterator().next());

        assertNotNull(objectUnderTest.find(shops.iterator().next()));

        objectUnderTest.delete(shops.iterator().next().getId());


        checkSavedItemsAreNotNull(shops.iterator().next());
        assertNull(objectUnderTest.find(shops.iterator().next()));
    }

    @Test
    public void delete2() {
        objectUnderTest.save(shops);

        Iterator<Shop> iterator = shops.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(shops);

        iterator = shops.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        shops.forEach(this::checkSavedItemsAreNotNull);
    }

    @Test
    public void deleteFromUser() {
        user = new User("simple login");
        user.setShop(shops.iterator().next());

        userService.save(user);

        assertNotNull(userService.find(user));
        assertNotNull(objectUnderTest.find(shops.iterator().next()));

        objectUnderTest.delete(shops.iterator().next());

        user = userService.find(user);
        assertNotNull(userService.find(user.getId()));
        assertNotEquals(shops.iterator().next(), userService.find(user.getId()).getShop());
        assertNull(objectUnderTest.find(shops.iterator().next()));
    }

    private void checkSavedItemsAreNotNull(Shop shop) {
        shop.getItems().forEach(Assert::assertNotNull);
    }

    private void addExampleDataToEquipments() {
        Statistic statistic;
        Shop shop;
        HashSet<Item> myItems = new HashSet<>();

        for (int a = 0, i = 3; i < 7; i++) {
            myItems.clear();
            shop = new Shop();
            statistic = new Statistic(i * i, i * i, i * i, i * i, i * i, i * i);

            myItems.add(new Item("name" + i, ItemType.WEAPON, statistic, i));
            myItems.add(new Item("name" + i, ItemType.ARMOR, statistic, i));
            myItems.add(new Item("name" + i, ItemType.BOOTS, statistic, i));
            myItems.add(new Item("name" + i, ItemType.BRACELET, statistic, i));
            myItems.add(new Item("name" + i, ItemType.GLOVES, statistic, i));
            myItems.add(new Item("name" + i, ItemType.HELMET, statistic, i));
            myItems.add(new Item("name" + i, ItemType.LEGS, statistic, i));
            myItems.add(new Item("name" + i, ItemType.NECKLACE, statistic, i));
            myItems.add(new Item("name" + i, ItemType.OFFHAND, statistic, i));
            myItems.add(new Item("name" + i, ItemType.RING, statistic, i));

            items.addAll(myItems);
            shop.setItems(myItems);
            shops.add(shop);
        }
    }
}
