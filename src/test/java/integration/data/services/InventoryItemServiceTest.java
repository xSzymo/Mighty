package integration.data.services;


import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class InventoryItemServiceTest extends IntegrationTestsConfig {
    @Autowired
    private InventoryItemService objectUnderTest;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ItemService itemService;

    private HashSet<InventoryItem> items;
    private Equipment equipment;
    private Shop shop;
    private Inventory inventory;

    @Before
    public void beforeEachTest() {
        items = new HashSet<>();
        items = new HashSet<>();
        addExampleDataToEquipments();
        items.forEach(x -> itemService.save(x.getItem()));
    }

    @After
    public void afterEachTest() {
        items.forEach(objectUnderTest::delete);
        if (shop != null)
            shopService.delete(shop);
        if (inventory != null)
            inventoryService.delete(inventory);
        if (equipment != null)
            equipmentService.delete(equipment);
    }

    @Test
    public void save() {
        objectUnderTest.save(items.iterator().next());

        assertNotNull(objectUnderTest.find(items.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(items);

        Iterator<InventoryItem> iterator = items.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(items.iterator().next());

        assertNotNull(objectUnderTest.find(items.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(items.iterator().next());

        assertNotNull(objectUnderTest.find(items.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(items);

        Iterator<InventoryItem> iterator = items.iterator();
        List<InventoryItem> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(items.iterator().next());

        assertNotNull(objectUnderTest.find(items.iterator().next()));

        objectUnderTest.delete(items.iterator().next());

        assertNull(objectUnderTest.find(items.iterator().next()));
    }

    @Test
    public void delete1() {
        objectUnderTest.save(items.iterator().next());

        assertNotNull(objectUnderTest.find(items.iterator().next()));

        objectUnderTest.delete(items.iterator().next().getId());

        assertNull(objectUnderTest.find(items.iterator().next()));
    }

    @Test
    public void delete2() {
        objectUnderTest.save(items);

        Iterator<InventoryItem> iterator = items.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(items);

        iterator = items.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void deleteFromItem() {
        InventoryItem item = items.iterator().next();
        objectUnderTest.save(item);
        InventoryItem inventoryItem = objectUnderTest.find(item);
        long id = inventoryItem.getItem().getId();
        assertNotNull(inventoryItem);
        assertNotNull(itemService.find(id));

        objectUnderTest.delete(inventoryItem);

        assertNull(objectUnderTest.find(inventoryItem));
        assertNotNull(itemService.find(id));
    }

    private void addExampleDataToEquipments() {
        int i = 3;

        items.add(new InventoryItem(new Item("name" + i, ItemType.WEAPON, i)));
        items.add(new InventoryItem(new Item("name" + i, ItemType.ARMOR, i)));
        items.add(new InventoryItem(new Item("name" + i, ItemType.BOOTS, i)));
        items.add(new InventoryItem(new Item("name" + i, ItemType.BRACELET, i)));
        items.add(new InventoryItem(new Item("name" + i, ItemType.GLOVES, i)));
        items.add(new InventoryItem(new Item("name" + i, ItemType.HELMET, i)));
        items.add(new InventoryItem(new Item("name" + i, ItemType.LEGS, i)));
        items.add(new InventoryItem(new Item("name" + i, ItemType.NECKLACE, i)));
        items.add(new InventoryItem(new Item("name" + i, ItemType.OFFHAND, i)));
        items.add(new InventoryItem(new Item("name" + i, ItemType.RING, i)));
    }
}
