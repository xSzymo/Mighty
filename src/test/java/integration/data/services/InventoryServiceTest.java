package integration.data.services;

import game.mightywarriors.data.services.InventoryService;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class InventoryServiceTest extends IntegrationTestsConfig {

    @Autowired
    private InventoryService objectUnderTest;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    private HashSet<Inventory> inventories;
    private HashSet<Item> items;
    private User user;

    @Before
    public void beforeEachTest() throws Exception {
        inventories = new HashSet<>();
        items = new HashSet<>();
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
        objectUnderTest.save(inventories.iterator().next());

        checkSavedItemsAreNotNull(inventories.iterator().next());
        assertNotNull(objectUnderTest.find(inventories.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(inventories);

        Iterator<Inventory> iterator = inventories.iterator();
        inventories.forEach(this::checkSavedItemsAreNotNull);
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(inventories.iterator().next());

        checkSavedItemsAreNotNull(inventories.iterator().next());
        assertNotNull(objectUnderTest.find(inventories.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(inventories.iterator().next());

        checkSavedItemsAreNotNull(inventories.iterator().next());
        assertNotNull(objectUnderTest.find(inventories.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(inventories);

        Iterator<Inventory> iterator = inventories.iterator();
        List<Inventory> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        list.forEach(this::checkSavedItemsAreNotNull);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(3).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(inventories.iterator().next());

        assertNotNull(objectUnderTest.find(inventories.iterator().next()));

        objectUnderTest.delete(inventories.iterator().next());

        assertNull(objectUnderTest.find(inventories.iterator().next()));
        checkSavedItemsAreNotNull(inventories.iterator().next());
    }

    @Test
    public void delete1() {
        objectUnderTest.save(inventories.iterator().next());

        assertNotNull(objectUnderTest.find(inventories.iterator().next()));

        objectUnderTest.delete(inventories.iterator().next().getId());

        assertNull(objectUnderTest.find(inventories.iterator().next()));
        checkSavedItemsAreNotNull(inventories.iterator().next());
    }

    @Test
    public void delete2() {
        objectUnderTest.save(inventories);

        Iterator<Inventory> iterator = inventories.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(inventories);

        iterator = inventories.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        inventories.forEach(this::checkSavedItemsAreNotNull);
    }


    @Test
    public void deleteFromUser() {
        user = new User("simple login" + System.currentTimeMillis());
        user.setInventory(inventories.iterator().next());

        userService.save(user);

        assertNotNull(userService.find(user));
        assertNotNull(objectUnderTest.find(inventories.iterator().next()));

        objectUnderTest.delete(inventories.iterator().next());

        user = userService.find(user);
        assertNotNull(user);
        assertNotNull(user.getInventory());
        assertNull(objectUnderTest.find(inventories.iterator().next()));
    }

    private void checkSavedItemsAreNotNull(Inventory inventory) {
        inventory.getItems().forEach(Assert::assertNotNull);
    }

    private void addExampleDataToEquipments() throws Exception {
        Statistic statistic;
        Inventory inventory;
        HashSet<Item> myItems = new HashSet<>();

        for (int i = 3; i < 7; i++) {
            myItems.clear();
            inventory = new Inventory();
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
            HashSet<InventoryItem> inventoryItems = new HashSet<>();
            myItems.forEach(x -> inventoryItems.add(new InventoryItem(x)));
            inventory.setItems(inventoryItems);
            inventories.add(inventory);
        }
    }
}
