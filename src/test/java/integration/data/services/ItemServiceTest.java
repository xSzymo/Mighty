package integration.data.services;


import game.mightywarriors.data.services.EquipmentService;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.ShopService;
import game.mightywarriors.data.tables.Equipment;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.other.enums.WeaponType;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.*;


public class ItemServiceTest extends IntegrationTestsConfig {
    @Autowired
    private ItemService objectUnderTest;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ShopService shopService;

    private LinkedList<Item> items;
    private Equipment equipment;
    private Shop shop;

    @Before
    public void beforeEachTest() {
        items = new LinkedList<>();
        items = new LinkedList<>();
        addExampleDataToEquipments();
    }

    @After
    public void afterEachTest() {
        items.forEach(objectUnderTest::delete);
        if (shop != null)
            shopService.delete(shop);
        if (equipment != null)
            equipmentService.delete(equipment);
    }

    @Test
    public void save() {
        objectUnderTest.save(items.getFirst());

        assertNotNull(objectUnderTest.findOne(items.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(items);

        assertNotNull(objectUnderTest.findOne(items.get(0)));
        assertNotNull(objectUnderTest.findOne(items.get(1)));
        assertNotNull(objectUnderTest.findOne(items.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(items.getFirst());

        assertNotNull(objectUnderTest.findOne(items.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(items.getFirst());

        assertNotNull(objectUnderTest.findOne(items.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(items);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(items.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(items.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(items.get(2).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(items.get(3).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(items.getFirst());

        assertNotNull(objectUnderTest.findOne(items.getFirst()));

        objectUnderTest.delete(items.getFirst());

        assertNull(objectUnderTest.findOne(items.getFirst()));
    }

    @Test
    public void delete1() {
        objectUnderTest.save(items.getFirst());

        assertNotNull(objectUnderTest.findOne(items.getFirst()));

        objectUnderTest.delete(items.getFirst().getId());

        assertNull(objectUnderTest.findOne(items.getFirst()));
    }

    @Test
    public void delete2() {
        objectUnderTest.save(items);

        assertNotNull(objectUnderTest.findOne(items.get(0)));
        assertNotNull(objectUnderTest.findOne(items.get(1)));
        assertNotNull(objectUnderTest.findOne(items.get(2)));

        objectUnderTest.delete(items);

        assertNull(objectUnderTest.findOne(items.get(0)));
        assertNull(objectUnderTest.findOne(items.get(1)));
        assertNull(objectUnderTest.findOne(items.get(2)));
    }

    @Test
    public void deleteFromShop() {
        shop = new Shop();
        shop.setItems(items);

        shopService.save(shop);

        assertNotNull(shopService.findOne(shop));
        items.forEach(x -> assertNotNull(objectUnderTest.findOne(x)));

        objectUnderTest.delete(items);

        assertNotNull(shopService.findOne(shop));
        assertEquals(0, shopService.findOne(shop).getItems().size());

        Item item = new Item();
        items.add(item);
        shop = shopService.findOne(shop);
        shop.addItem(item);

        shopService.save(shop);

        assertNotNull(shopService.findOne(shop));
        assertNotNull(objectUnderTest.findOne(item));
    }

    @Test
    public void deleteFromEquipment() throws Exception {
        equipment = new Equipment();
        equipment.setRing(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.RING)).findFirst().get());
        equipment.setArmor(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.ARMOR)).findFirst().get());
        equipment.setBoots(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.BOOTS)).findFirst().get());
        equipment.setBracelet(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.BRACELET)).findFirst().get());
        equipment.setGloves(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.GLOVES)).findFirst().get());
        equipment.setHelmet(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.HELMET)).findFirst().get());
        equipment.setLegs(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.LEGS)).findFirst().get());
        equipment.setNecklace(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.NECKLACE)).findFirst().get());
        equipment.setOffhand(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.OFFHAND)).findFirst().get());
        equipment.setWeapon(items.stream().filter(x -> x.getTypeOfWeapon().equals(WeaponType.WEAPON)).findFirst().get());

        equipmentService.save(equipment);

        assertNotNull(equipmentService.findOne(equipment));
        items.forEach(objectUnderTest::findOne);

        objectUnderTest.delete(items);

        assertNotNull(equipmentService.findOne(equipment));
        assertNull(equipmentService.findOne(equipment).getArmor());
        assertNull(equipmentService.findOne(equipment).getRing());
        assertNull(equipmentService.findOne(equipment).getBoots());
        assertNull(equipmentService.findOne(equipment).getBracelet());
        assertNull(equipmentService.findOne(equipment).getGloves());
        assertNull(equipmentService.findOne(equipment).getHelmet());
        assertNull(equipmentService.findOne(equipment).getLegs());
        assertNull(equipmentService.findOne(equipment).getNecklace());
        assertNull(equipmentService.findOne(equipment).getOffhand());
        assertNull(equipmentService.findOne(equipment).getWeapon());
    }

    private void addExampleDataToEquipments() {
        Statistic statistic;
        int i = 3;
        statistic = new Statistic(i * i, i * i, i * i, i * i, i * i, i * i);

        items.add(new Item("name" + i, WeaponType.WEAPON, statistic, i));
        items.add(new Item("name" + i, WeaponType.ARMOR, statistic, i));
        items.add(new Item("name" + i, WeaponType.BOOTS, statistic, i));
        items.add(new Item("name" + i, WeaponType.BRACELET, statistic, i));
        items.add(new Item("name" + i, WeaponType.GLOVES, statistic, i));
        items.add(new Item("name" + i, WeaponType.HELMET, statistic, i));
        items.add(new Item("name" + i, WeaponType.LEGS, statistic, i));
        items.add(new Item("name" + i, WeaponType.NECKLACE, statistic, i));
        items.add(new Item("name" + i, WeaponType.OFFHAND, statistic, i));
        items.add(new Item("name" + i, WeaponType.RING, statistic, i));
    }
}
