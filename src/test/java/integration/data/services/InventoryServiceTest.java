package integration.data.services;

import game.mightywarriors.data.services.InventoryService;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Inventory;
import game.mightywarriors.data.tables.Item;
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


public class InventoryServiceTest extends IntegrationTestsConfig {

    @Autowired
    private InventoryService objectUnderTest;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    private LinkedList<Inventory> inventories;
    private LinkedList<Item> items;
    private User user;

    @Before
    public void beforeEachTest() {
        inventories = new LinkedList<>();
        items = new LinkedList<>();
        addExampleDataToEquipments();
    }

    @After
    public void afterEachTest() {
        for (Item item : items) {
            itemService.delete(item);
        }

        inventories.forEach(objectUnderTest::delete);

        if (user != null)
            userService.delete(user);
    }

    @Test
    public void save() {
        objectUnderTest.save(inventories.getFirst());

        checkSavedItemsAreNotNull(inventories.getFirst());
        assertNotNull(objectUnderTest.findOne(inventories.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(inventories);

        inventories.forEach(this::checkSavedItemsAreNotNull);
        assertNotNull(objectUnderTest.findOne(inventories.get(0)));
        assertNotNull(objectUnderTest.findOne(inventories.get(1)));
        assertNotNull(objectUnderTest.findOne(inventories.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(inventories.getFirst());

        checkSavedItemsAreNotNull(inventories.getFirst());
        assertNotNull(objectUnderTest.findOne(inventories.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(inventories.getFirst());

        checkSavedItemsAreNotNull(inventories.getFirst());
        assertNotNull(objectUnderTest.findOne(inventories.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(inventories);

        inventories.forEach(this::checkSavedItemsAreNotNull);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(inventories.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(inventories.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(inventories.get(2).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(inventories.get(3).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(inventories.getFirst());

        assertNotNull(objectUnderTest.findOne(inventories.getFirst()));

        objectUnderTest.delete(inventories.getFirst());

        assertNull(objectUnderTest.findOne(inventories.getFirst()));
        checkSavedItemsAreNotNull(inventories.getFirst());
    }

    @Test
    public void delete1() {
        objectUnderTest.save(inventories.getFirst());

        assertNotNull(objectUnderTest.findOne(inventories.getFirst()));

        objectUnderTest.delete(inventories.getFirst().getId());

        assertNull(objectUnderTest.findOne(inventories.getFirst()));
        checkSavedItemsAreNotNull(inventories.getFirst());
    }

    @Test
    public void delete2() {
        objectUnderTest.save(inventories);

        assertNotNull(objectUnderTest.findOne(inventories.get(0)));
        assertNotNull(objectUnderTest.findOne(inventories.get(1)));
        assertNotNull(objectUnderTest.findOne(inventories.get(2)));

        objectUnderTest.delete(inventories);

        assertNull(objectUnderTest.findOne(inventories.get(0)));
        assertNull(objectUnderTest.findOne(inventories.get(1)));
        assertNull(objectUnderTest.findOne(inventories.get(2)));
        inventories.forEach(this::checkSavedItemsAreNotNull);
    }


    @Test
    public void deleteFromUser() {
        user = new User("simple login" + System.currentTimeMillis());
        user.setInventory(inventories.getFirst());

        userService.save(user);

        assertNotNull(userService.findOne(user));
        assertNotNull(objectUnderTest.findOne(inventories.getFirst()));

        objectUnderTest.delete(inventories.getFirst());

        assertNotNull(userService.findOne(user.getId()));
        assertNull(userService.findOne(user.getId()).getInventory());
        assertNull(objectUnderTest.findOne(inventories.getFirst()));
    }

    private void checkSavedItemsAreNotNull(Inventory inventory) {
        inventory.getItems().forEach(Assert::assertNotNull);
    }

    private void addExampleDataToEquipments() {
        Statistic statistic;
        Inventory inventory;
        LinkedList<Item> myItems = new LinkedList<>();

        for (int i = 3; i < 7; i++) {
            myItems.clear();
            inventory = new Inventory();
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
            inventory.setItems(myItems);
            inventories.add(inventory);
        }
    }
}
