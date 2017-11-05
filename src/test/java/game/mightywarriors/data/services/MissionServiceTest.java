package game.mightywarriors.data.services;

import game.mightywarriors.data.tables.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.LinkedList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MissionServiceTest {
    @Autowired
    private MissionService objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private StatisticService statisticService;

    private LinkedList<Mission> missions;
    private LinkedList<Statistic> statistics;
    private LinkedList<Image> images;
    private LinkedList<Monster> monsters;
    private User user;

    @Before
    public void beforeEachTest() {
        missions = new LinkedList<>();
        monsters = new LinkedList<>();
        statistics = new LinkedList<>();
        images = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            monsters.add(new Monster(statistics.get(i), images.get(i)));
            missions.add(new Mission(1, "", new BigDecimal("1"), monsters.get(i)));
        }
    }

    @After
    public void afterEachTest() {
        if (user != null)
            userService.delete(user);
        missions.forEach(objectUnderTest::delete);
        System.out.println("");
    }

    @Test
    public void save() throws Exception {
        objectUnderTest.save(missions.getFirst());

        Mission mission = objectUnderTest.findOne(missions.getFirst());

        checker(mission);
    }

    @Test
    public void saveCollection() throws Exception {
        objectUnderTest.save(missions);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);

        objectUnderTest.findAll().forEach(this::checker);
    }

    @Test
    public void findOne() throws Exception {
        objectUnderTest.save(missions.getFirst());

        assertNotNull(objectUnderTest.findOne(missions.getFirst()));
    }

    @Test
    public void findOne1() throws Exception {
        objectUnderTest.save(missions.getFirst());

        assertNotNull(objectUnderTest.findOne(missions.getFirst().getId()));
    }

    @Test
    public void findAll() throws Exception {
        objectUnderTest.save(missions);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void delete() throws Exception {
        objectUnderTest.save(missions.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);
        checker(objectUnderTest.findOne(missions.getFirst()));

        objectUnderTest.delete(missions.getFirst());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        checkerNulls(missions.getFirst());
    }

    @Test
    public void delete1() throws Exception {
        objectUnderTest.save(missions.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);
        checker(objectUnderTest.findOne(missions.getFirst()));

        objectUnderTest.delete(missions.getFirst().getId());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        checkerNulls(missions.getFirst());
    }

    @Test
    public void delete2() throws Exception {
        objectUnderTest.save(missions);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
        missions.forEach(this::checker);

        objectUnderTest.delete(missions);

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        missions.forEach(this::checkerNulls);
    }

    @Test
    public void deleteFromUser() {
        user = new User();
        user.setLogin("");
        user.setPassword("");
        user.seteMail("");
        user.getMissions().add(missions.getFirst());

        userService.save(user);

        Mission mission = objectUnderTest.findOne(missions.getFirst());
        user = userService.findOne(user);

        assertNotNull(mission);
        assertNotNull(user);

        objectUnderTest.delete(mission);

        assertNotNull(user);
        checkerNulls(user.getMissions().get(0));
    }

    private void checker(Mission mission) {
        Monster monster = monsterService.findOne(mission.getMonster());

        assertNotNull(monster);
        assertNotNull(objectUnderTest.findOne(mission));
        assertNotNull(imageService.findOne(monster.getImage()));
        assertNotNull(statisticService.findOne(monster.getStatistic()));
    }

    private void checkerNulls(Mission mission) {
        assertNull(objectUnderTest.findOne(mission));
        assertNull(monsterService.findOne(mission.getMonster()));
    }
}
