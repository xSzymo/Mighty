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

        long counter = objectUnderTest.findAll().size();

        checkSavedItemsAreNotNull(inventories.getFirst());
        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(inventories);
        long counter = objectUnderTest.findAll().size();

        inventories.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(4, counter);
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

        long counter = objectUnderTest.findAll().size();

        inventories.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(4, counter);
    }

    @Test
    public void delete() {
        objectUnderTest.save(inventories.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(inventories.getFirst());

        counter = objectUnderTest.findAll().size();

        checkSavedItemsAreNotNull(inventories.getFirst());
        assertEquals(0, counter);
    }

    @Test
    public void delete1() {
        objectUnderTest.save(inventories.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(inventories.getFirst().getId());

        counter = objectUnderTest.findAll().size();

        checkSavedItemsAreNotNull(inventories.getFirst());
        assertEquals(0, counter);
    }

    @Test
    public void delete2() {
        objectUnderTest.save(inventories);

        long counter = objectUnderTest.findAll().size();

        assertEquals(4, counter);

        objectUnderTest.delete(inventories);

        counter = objectUnderTest.findAll().size();

        inventories.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(0, counter);
    }


    @Test
    public void deleteFromUser() {
        user = new User();
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

        for (int a = 0, i = 3; i < 7; i++) {
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
