package game.mightywarriors.data.services;

import game.mightywarriors.data.tables.*;
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
public class ChampionServiceTest {
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
    public void save() throws Exception {
        objectUnderTest.save(champions.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() throws Exception {
        objectUnderTest.save(champions);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void findOne() throws Exception {
        objectUnderTest.save(champions.getFirst());

        assertNotNull(objectUnderTest.findOne(champions.getFirst()));
    }

    @Test
    public void findOne1() throws Exception {
        objectUnderTest.save(champions.getFirst());

        assertNotNull(objectUnderTest.findOne(champions.getFirst().getId()));
    }

    @Test
    public void findAll() throws Exception {
        objectUnderTest.save(champions);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void delete() throws Exception {
        objectUnderTest.save(champions.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(champions.getFirst());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
    }

    @Test
    public void delete1() throws Exception {
        objectUnderTest.save(champions.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(champions.getFirst().getId());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
    }

    @Test
    public void delete2() throws Exception {
        objectUnderTest.save(champions);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);

        objectUnderTest.delete(champions);

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
    }

    @Test
    public void deleteFromEquipment() throws Exception {
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
    public void deleteFromUser() throws Exception {
        Champion champion = champions.getFirst();
        user = new User();
        user.addChampion(champion);

        userService.save(user);

        assertNotNull(userService.findOne(user));
        assertNotNull(objectUnderTest.findOne(champion));

        objectUnderTest.delete(champion);

        assertNotNull(userService.findOne(user));
        assertNull(objectUnderTest.findOne(champion));
    }
}