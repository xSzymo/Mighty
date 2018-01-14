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

import java.util.*;

import static org.junit.Assert.*;


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
    public void beforeEachTest() {
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
        assertNotNull(objectUnderTest.findOne(inventories.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(inventories);

        Iterator<Inventory> iterator = inventories.iterator();
        inventories.forEach(this::checkSavedItemsAreNotNull);
        assertNotNull(objectUnderTest.findOne(iterator.next()));
        assertNotNull(objectUnderTest.findOne(iterator.next()));
        assertNotNull(objectUnderTest.findOne(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(inventories.iterator().next());

        checkSavedItemsAreNotNull(inventories.iterator().next());
        assertNotNull(objectUnderTest.findOne(inventories.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(inventories.iterator().next());

        checkSavedItemsAreNotNull(inventories.iterator().next());
        assertNotNull(objectUnderTest.findOne(inventories.iterator().next().getId()));
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

        assertNotNull(objectUnderTest.findOne(inventories.iterator().next()));

        objectUnderTest.delete(inventories.iterator().next());

        assertNull(objectUnderTest.findOne(inventories.iterator().next()));
        checkSavedItemsAreNotNull(inventories.iterator().next());
    }

    @Test
    public void delete1() {
        objectUnderTest.save(inventories.iterator().next());

        assertNotNull(objectUnderTest.findOne(inventories.iterator().next()));

        objectUnderTest.delete(inventories.iterator().next().getId());

        assertNull(objectUnderTest.findOne(inventories.iterator().next()));
        checkSavedItemsAreNotNull(inventories.iterator().next());
    }

    @Test
    public void delete2() {
        objectUnderTest.save(inventories);

        Iterator<Inventory> iterator = inventories.iterator();
        assertNotNull(objectUnderTest.findOne(iterator.next()));
        assertNotNull(objectUnderTest.findOne(iterator.next()));
        assertNotNull(objectUnderTest.findOne(iterator.next()));

        objectUnderTest.delete(inventories);

        iterator = inventories.iterator();
        assertNull(objectUnderTest.findOne(iterator.next()));
        assertNull(objectUnderTest.findOne(iterator.next()));
        assertNull(objectUnderTest.findOne(iterator.next()));
        inventories.forEach(this::checkSavedItemsAreNotNull);
    }


    @Test
    public void deleteFromUser() {
        user = new User("simple login" + System.currentTimeMillis());
        user.setInventory(inventories.iterator().next());

        userService.save(user);

        assertNotNull(userService.findOne(user));
        assertNotNull(objectUnderTest.findOne(inventories.iterator().next()));

        objectUnderTest.delete(inventories.iterator().next());

        assertNotNull(userService.findOne(user.getId()));
        assertNull(userService.findOne(user.getId()).getInventory());
        assertNull(objectUnderTest.findOne(inventories.iterator().next()));
    }

    private void checkSavedItemsAreNotNull(Inventory inventory) {
        inventory.getItems().forEach(Assert::assertNotNull);
    }

    private void addExampleDataToEquipments() {
        Statistic statistic;
        Inventory inventory;
        HashSet<Item> myItems = new HashSet<>();

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
