package integration.data.services;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.EquipmentService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class ChampionServiceTest extends IntegrationTestsConfig {
    @Autowired
    private ChampionService objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private EquipmentService equipmentService;

    private LinkedList<Champion> champions;
    private LinkedList<Statistic> statistics;
    private LinkedList<Image> images;
    private User user;
    private Equipment equipment;

    @Before
    public void beforeEachTest() {
        champions = new LinkedList<>();
        statistics = new LinkedList<>();
        images = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            champions.add(new Champion(statistics.get(i), images.get(i)));
        }
    }

    @After
    public void afterEachTest() {
        if (user != null)
            userService.delete(user);
        if (equipment != null)
            equipmentService.delete(equipment);
        champions.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(champions.getFirst());

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(champions);

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(3, counter);
    }

    @Test
    public void findOne() {
        objectUnderTest.save(champions.getFirst());

        assertNotNull(objectUnderTest.findOne(champions.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(champions.getFirst());

        assertNotNull(objectUnderTest.findOne(champions.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(champions);

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(3, counter);
    }

    @Test
    public void delete() {
        objectUnderTest.save(champions.getFirst());

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(champions.getFirst());

        counter = (long) objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void delete1() {
        objectUnderTest.save(champions.getFirst());

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(champions.getFirst().getId());

        counter = (long) objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void delete2() {
        objectUnderTest.save(champions);

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(3, counter);

        objectUnderTest.delete(champions);

        counter = (long) objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void deleteFromEquipment() {
        Champion champion = champions.getFirst();
        equipment = new Equipment();
        champion.setEquipment(equipment);

        objectUnderTest.save(champion);

        assertNotNull(equipmentService.findOne(equipment));
        assertNotNull(objectUnderTest.findOne(champion));

        objectUnderTest.delete(champion);

        assertNull(equipmentService.findOne(equipment));
        assertNull(objectUnderTest.findOne(champion));
    }

    @Test
    public void deleteFromUser() {
        Champion champion = champions.getFirst();
        user = new User("simple login" + System.currentTimeMillis());
        user.addChampion(champion);

        userService.save(user);

        assertNotNull(userService.findOne(user));
        assertNotNull(objectUnderTest.findOne(champion));

        objectUnderTest.delete(champion);

        assertNotNull(userService.findOne(user));
        assertNull(objectUnderTest.findOne(champion));
    }
}