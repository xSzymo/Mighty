package integration.data.services;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.EquipmentService;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Equipment;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.other.enums.WeaponType;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EquipmentServiceTest extends IntegrationTestsConfig {
    @Autowired
    private EquipmentService objectUnderTest;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ChampionService championService;

    private LinkedList<Equipment> equipments;
    private LinkedList<Item> items;
    private Champion champion;

    @Before
    public void beforeEachTest() throws Exception {
        equipments = new LinkedList<>();
        items = new LinkedList<>();
        addExampleDataToEquipments();
    }

    @After
    public void afterEachTest() {
        for (Item item : items) {
            itemService.delete(item);
        }
        equipments.forEach(objectUnderTest::delete);
        if (champion != null)
            championService.delete(champion);
    }

    @Test
    public void save() {
        objectUnderTest.save(equipments.getFirst());

        long counter = objectUnderTest.findAll().size();

        checkSavedItemsAreNotNull(equipments.getFirst());
        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(equipments);

        long counter = objectUnderTest.findAll().size();

        equipments.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(4, counter);
    }

    @Test
    public void findOne() {
        objectUnderTest.save(equipments.getFirst());

        checkSavedItemsAreNotNull(equipments.getFirst());
        assertNotNull(objectUnderTest.findOne(equipments.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(equipments.getFirst());

        checkSavedItemsAreNotNull(equipments.getFirst());
        assertNotNull(objectUnderTest.findOne(equipments.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(equipments);

        long counter = objectUnderTest.findAll().size();

        equipments.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(4, counter);
    }

    @Test
    public void delete() {
        objectUnderTest.save(equipments.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(equipments.getFirst());

        counter = objectUnderTest.findAll().size();

        checkSavedItemsAreNotNull(equipments.getFirst());
        assertEquals(0, counter);
    }

    @Test
    public void delete1() {
        objectUnderTest.save(equipments.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(equipments.getFirst().getId());

        counter = objectUnderTest.findAll().size();

        checkSavedItemsAreNotNull(equipments.getFirst());
        assertEquals(0, counter);
    }

    @Test
    public void delete2() {
        objectUnderTest.save(equipments);

        long counter = objectUnderTest.findAll().size();

        assertEquals(4, counter);

        objectUnderTest.delete(equipments);

        counter = objectUnderTest.findAll().size();

        equipments.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(0, counter);
    }

    @Test
    public void deleteFromChampion() {
        champion = new Champion();
        champion.setEquipment(equipments.getFirst());

        championService.save(champion);

        assertNotNull(championService.findOne(champion));
        assertNotNull(objectUnderTest.findOne(equipments.getFirst()));

        objectUnderTest.delete(equipments.getFirst());

        assertNotNull(championService.findOne(champion.getId()));
        assertNull(championService.findOne(champion).getEquipment());
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

    private void addExampleDataToEquipments() throws Exception {
        Equipment equipment;
        Statistic statistic;

        for (int a = 0, i = 3; i < 7; i++) {
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

            equipment = new Equipment();
            equipment.setWeapon(items.get(a++));
            equipment.setArmor(items.get(a++));
            equipment.setBoots(items.get(a++));
            equipment.setBracelet(items.get(a++));
            equipment.setGloves(items.get(a++));
            equipment.setHelmet(items.get(a++));
            equipment.setLegs(items.get(a++));
            equipment.setNecklace(items.get(a++));
            equipment.setOffhand(items.get(a++));
            equipment.setRing(items.get(a++));

            this.equipments.add(equipment);
        }
    }
}