package game.mightywarriors.data.services;

import game.mightywarriors.data.enums.WeaponType;
import game.mightywarriors.data.tables.Equipment;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Statistic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EquipmentServiceTest {
    @Autowired
    private EquipmentService objectUnderTest;
    @Autowired
    private ItemService itemService;

    private LinkedList<Equipment> equipments;

    @Before
    public void beforeEachTest() throws Exception {
        equipments = new LinkedList<>();
        addExampleDataToEquipments();
    }

    @After
    public void afterEachTest() {
        equipments.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() throws Exception {
        objectUnderTest.save(equipments.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        checkSavedItemsAreNotNull(equipments.getFirst());
        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() throws Exception {
        objectUnderTest.save(equipments);

        long counter = objectUnderTest.findAll().stream().count();

        equipments.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(4, counter);
    }

    @Test
    public void findOne() throws Exception {
        objectUnderTest.save(equipments.getFirst());

        checkSavedItemsAreNotNull(equipments.getFirst());
        assertNotNull(objectUnderTest.findOne(equipments.getFirst()));
    }

    @Test
    public void findOne1() throws Exception {
        objectUnderTest.save(equipments.getFirst());

        checkSavedItemsAreNotNull(equipments.getFirst());
        assertNotNull(objectUnderTest.findOne(equipments.getFirst().getId()));
    }

    @Test
    public void findAll() throws Exception {
        objectUnderTest.save(equipments);

        long counter = objectUnderTest.findAll().stream().count();

        equipments.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(4, counter);
    }

    @Test
    public void delete() throws Exception {
        objectUnderTest.save(equipments.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(equipments.getFirst());

        counter = objectUnderTest.findAll().stream().count();

        checkSavedItemsAreNull(equipments.getFirst());
        assertEquals(0, counter);
        equipments.clear();
    }

    @Test
    public void delete1() throws Exception {
        objectUnderTest.save(equipments.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(equipments.getFirst().getId());

        counter = objectUnderTest.findAll().stream().count();

        checkSavedItemsAreNull(equipments.getFirst());
        assertEquals(0, counter);
        equipments.clear();
    }

    @Test
    public void delete2() throws Exception {
        objectUnderTest.save(equipments);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(4, counter);

        objectUnderTest.delete(equipments);

        counter = objectUnderTest.findAll().stream().count();

        equipments.forEach( this::checkSavedItemsAreNull);
        assertEquals(0, counter);
        equipments.clear();
    }

    private void checkSavedItemsAreNotNull(Equipment equipment) {
        assertNotNull(itemService.findOne(equipment.getArmor().getId()));
        assertNotNull(itemService.findOne(equipment.getBoots().getId()));
        assertNotNull(itemService.findOne(equipment.getBracelet().getId()));
        assertNotNull(itemService.findOne(equipment.getGloves().getId()));
        assertNotNull(itemService.findOne(equipment.getHelmet().getId()));
        assertNotNull(itemService.findOne(equipment.getLegs().getId()));
        assertNotNull(itemService.findOne(equipment.getNecklace().getId()));
        assertNotNull(itemService.findOne(equipment.getOffhand().getId()));
        assertNotNull(itemService.findOne(equipment.getRing().getId()));
        assertNotNull(itemService.findOne(equipment.getWeapon().getId()));
    }

    private void checkSavedItemsAreNull(Equipment equipment) {
        assertNull(itemService.findOne(equipment.getArmor().getId()));
        assertNull(itemService.findOne(equipment.getBoots().getId()));
        assertNull(itemService.findOne(equipment.getBracelet().getId()));
        assertNull(itemService.findOne(equipment.getGloves().getId()));
        assertNull(itemService.findOne(equipment.getHelmet().getId()));
        assertNull(itemService.findOne(equipment.getLegs().getId()));
        assertNull(itemService.findOne(equipment.getNecklace().getId()));
        assertNull(itemService.findOne(equipment.getOffhand().getId()));
        assertNull(itemService.findOne(equipment.getRing().getId()));
        assertNull(itemService.findOne(equipment.getWeapon().getId()));
    }

    private void addExampleDataToEquipments() throws Exception {
        Equipment equipment;
        Statistic statistic;

        for (int i = 3; i < 7; i++) {
            statistic = new Statistic(i*i, i*i, i*i, i*i, i*i, i*i);

            equipment = new Equipment();
            equipment.setWeapon(new Item("name" + i, WeaponType.WEAPON, statistic, i));
            equipment.setArmor(new Item("name" + i, WeaponType.ARMOR, statistic, i));
            equipment.setBoots(new Item("name" + i, WeaponType.BOOTS, statistic, i));
            equipment.setBracelet(new Item("name" + i, WeaponType.BRACELET, statistic, i));
            equipment.setGloves(new Item("name" + i, WeaponType.GLOVES, statistic, i));
            equipment.setHelmet(new Item("name" + i, WeaponType.HELMET, statistic, i));
            equipment.setLegs(new Item("name" + i, WeaponType.LEGS, statistic, i));
            equipment.setNecklace(new Item("name" + i, WeaponType.NECKLACE, statistic, i));
            equipment.setOffhand(new Item("name" + i, WeaponType.OFFHAND, statistic, i));
            equipment.setRing(new Item("name" + i, WeaponType.RING, statistic, i));

            this.equipments.add(equipment);
        }
    }
}