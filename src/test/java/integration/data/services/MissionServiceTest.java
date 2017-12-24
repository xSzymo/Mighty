package integration.data.services;

import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.LinkedList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MissionServiceTest extends IntegrationTestsConfig {
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
    }

    @Test
    public void save() {
        objectUnderTest.save(missions.getFirst());

        Mission mission = objectUnderTest.findOne(missions.getFirst());

        checker(mission);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(missions);

        assertNotNull(objectUnderTest.findOne(missions.get(0)));
        assertNotNull(objectUnderTest.findOne(missions.get(1)));
        assertNotNull(objectUnderTest.findOne(missions.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(missions.getFirst());

        assertNotNull(objectUnderTest.findOne(missions.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(missions.getFirst());

        assertNotNull(objectUnderTest.findOne(missions.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(missions);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(missions.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(missions.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(missions.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(missions.getFirst());

        checker(objectUnderTest.findOne(missions.getFirst()));

        objectUnderTest.delete(missions.getFirst());

        checkerNulls(missions.getFirst());
    }

    @Test
    public void delete1() {
        objectUnderTest.save(missions.getFirst());

        checker(objectUnderTest.findOne(missions.getFirst()));

        objectUnderTest.delete(missions.getFirst().getId());

        checkerNulls(missions.getFirst());
    }

    @Test
    public void delete2() {
        objectUnderTest.save(missions);

        missions.forEach(this::checker);

        objectUnderTest.delete(missions);

        missions.forEach(this::checkerNulls);
    }

    @Test
    public void deleteFromUser() {
        user = new User();
        user.setLogin("");
        user.setPassword("");
        user.seteMail("");
        user.getMissions().add(missions.getFirst());
        user.getMissions().add(missions.getFirst());
        user.getMissions().add(missions.getFirst());

        userService.save(user);

        Mission mission = objectUnderTest.findOne(missions.getFirst());
        user = userService.findOne(user);

        assertNotNull(mission);
        assertNotNull(user);

        objectUnderTest.delete(missions.getFirst());

        assertNotNull(user);
        assertNull(objectUnderTest.findOne(mission));
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
