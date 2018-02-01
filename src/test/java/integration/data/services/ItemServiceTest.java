package integration.data.services;


import game.mightywarriors.data.services.EquipmentService;
import game.mightywarriors.data.services.InventoryService;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.ShopService;
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

import static org.junit.Assert.*;


public class ItemServiceTest extends IntegrationTestsConfig {
    @Autowired
    private ItemService objectUnderTest;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private InventoryService inventoryService;

    private HashSet<Item> items;
    private Equipment equipment;
    private Shop shop;
    private Inventory inventory;

    @Before
    public void beforeEachTest() {
        items = new HashSet<>();
        items = new HashSet<>();
        addExampleDataToEquipments();
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

        Iterator<Item> iterator = items.iterator();
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

        Iterator<Item> iterator = items.iterator();
        List<Item> list = new ArrayList<>();
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

        Iterator<Item> iterator = items.iterator();
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
    public void deleteFromShop() {
        shop = new Shop();
        shop.setItems(items);

        shopService.save(shop);

        assertNotNull(shopService.find(shop));
        items.forEach(x -> assertNotNull(objectUnderTest.find(x)));

        objectUnderTest.delete(items);

        assertNotNull(shopService.find(shop));
        assertEquals(0, shopService.find(shop).getItems().size());

        Item item = new Item();
        items.add(item);
        shop = shopService.find(shop);
        shop.addItem(item);

        shopService.save(shop);

        Shop one = shopService.find(shop);
        assertNotNull(one);
        assertEquals(1, one.getItems().size());
        assertNotNull(objectUnderTest.find(item));
    }

    @Test
    public void deleteFromInventory() {
        inventory = new Inventory();
        inventory.setItems(items);

        inventoryService.save(inventory);

        assertNotNull(inventoryService.find(inventory));
        items.forEach(x -> assertNotNull(objectUnderTest.find(x)));

        objectUnderTest.delete(items);

        assertNotNull(inventoryService.find(inventory));
        assertEquals(0, inventoryService.find(inventory).getItems().size());

        Item item = new Item();
        items.add(item);
        inventory = inventoryService.find(inventory);
        inventory.addItem(item);

        inventoryService.save(inventory);

        Inventory one = inventoryService.find(inventory);
        assertNotNull(one);
        assertEquals(1, inventory.getItems().size());
        assertNotNull(objectUnderTest.find(item));
    }

    @Test
    public void deleteFromEquipment() throws Exception {
        equipment = new Equipment();
        equipment.setRing(items.stream().filter(x -> x.getItemType().equals(ItemType.RING)).findFirst().get());
        equipment.setArmor(items.stream().filter(x -> x.getItemType().equals(ItemType.ARMOR)).findFirst().get());
        equipment.setBoots(items.stream().filter(x -> x.getItemType().equals(ItemType.BOOTS)).findFirst().get());
        equipment.setBracelet(items.stream().filter(x -> x.getItemType().equals(ItemType.BRACELET)).findFirst().get());
        equipment.setGloves(items.stream().filter(x -> x.getItemType().equals(ItemType.GLOVES)).findFirst().get());
        equipment.setHelmet(items.stream().filter(x -> x.getItemType().equals(ItemType.HELMET)).findFirst().get());
        equipment.setLegs(items.stream().filter(x -> x.getItemType().equals(ItemType.LEGS)).findFirst().get());
        equipment.setNecklace(items.stream().filter(x -> x.getItemType().equals(ItemType.NECKLACE)).findFirst().get());
        equipment.setOffhand(items.stream().filter(x -> x.getItemType().equals(ItemType.OFFHAND)).findFirst().get());
        equipment.setWeapon(items.stream().filter(x -> x.getItemType().equals(ItemType.WEAPON)).findFirst().get());

        equipmentService.save(equipment);

        assertNotNull(equipmentService.find(equipment));
        items.forEach(objectUnderTest::find);

        objectUnderTest.delete(items);

        assertNotNull(equipmentService.find(equipment));
        assertNull(equipmentService.find(equipment).getArmor());
        assertNull(equipmentService.find(equipment).getRing());
        assertNull(equipmentService.find(equipment).getBoots());
        assertNull(equipmentService.find(equipment).getBracelet());
        assertNull(equipmentService.find(equipment).getGloves());
        assertNull(equipmentService.find(equipment).getHelmet());
        assertNull(equipmentService.find(equipment).getLegs());
        assertNull(equipmentService.find(equipment).getNecklace());
        assertNull(equipmentService.find(equipment).getOffhand());
        assertNull(equipmentService.find(equipment).getWeapon());
    }

    private void addExampleDataToEquipments() {
        Statistic statistic;
        int i = 3;
        statistic = new Statistic(i * i, i * i, i * i, i * i, i * i, i * i);

        items.add(new Item("name" + i, ItemType.WEAPON, statistic, i));
        items.add(new Item("name" + i, ItemType.ARMOR, statistic, i));
        items.add(new Item("name" + i, ItemType.BOOTS, statistic, i));
        items.add(new Item("name" + i, ItemType.BRACELET, statistic, i));
        items.add(new Item("name" + i, ItemType.GLOVES, statistic, i));
        items.add(new Item("name" + i, ItemType.HELMET, statistic, i));
        items.add(new Item("name" + i, ItemType.LEGS, statistic, i));
        items.add(new Item("name" + i, ItemType.NECKLACE, statistic, i));
        items.add(new Item("name" + i, ItemType.OFFHAND, statistic, i));
        items.add(new Item("name" + i, ItemType.RING, statistic, i));
    }
}
