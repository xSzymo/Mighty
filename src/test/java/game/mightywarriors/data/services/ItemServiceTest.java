package game.mightywarriors.data.services;

import game.mightywarriors.data.enums.WeaponType;
import game.mightywarriors.data.tables.Equipment;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.Statistic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {
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
    public void beforeEachTest() throws Exception {
        items = new LinkedList<>();
        items = new LinkedList<>();
        addExampleDataToEquipments();
    }

    @After
    public void afterEachTest() throws Exception {
        items.forEach(objectUnderTest::delete);
        if (shop != null)
            shopService.delete(shop);
        if (equipment != null)
            equipmentService.delete(equipment);
    }

    @Test
    public void save() throws Exception {
        objectUnderTest.save(items.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() throws Exception {
        objectUnderTest.save(items);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(10, counter);
    }

    @Test
    public void findOne() throws Exception {
        objectUnderTest.save(items.getFirst());

        assertNotNull(objectUnderTest.findOne(items.getFirst()));
    }

    @Test
    public void findOne1() throws Exception {
        objectUnderTest.save(items.getFirst());

        assertNotNull(objectUnderTest.findOne(items.getFirst().getId()));
    }

    @Test
    public void findAll() throws Exception {
        objectUnderTest.save(items);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(10, counter);
    }

    @Test
    public void delete() throws Exception {
        objectUnderTest.save(items.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(items.getFirst());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
    }

    @Test
    public void delete1() throws Exception {
        objectUnderTest.save(items.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(items.getFirst().getId());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
    }

    @Test
    public void delete2() throws Exception {
        objectUnderTest.save(items);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(10, counter);

        objectUnderTest.delete(items);

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
    }

    @Test
    public void deleteFromShop() throws Exception {
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

    private void addExampleDataToEquipments() throws Exception {
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
