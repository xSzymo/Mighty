package integration.data.services;

import game.mightywarriors.data.services.ImageService;
import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.services.MonsterService;
import game.mightywarriors.data.services.StatisticService;
import game.mightywarriors.data.tables.Image;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.Statistic;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class MonsterServiceTest extends IntegrationTestsConfig {
    @Autowired
    private MonsterService objectUnderTest;
    @Autowired
    private ImageService imageService;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private MissionService missionService;

    private LinkedList<Statistic> statistics;
    private LinkedList<Image> images;
    private LinkedList<Monster> monsters;
    private Mission mission;

    @Before
    public void beforeEachTest() {
        monsters = new LinkedList<>();
        statistics = new LinkedList<>();
        images = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            monsters.add(new Monster(statistics.get(i), images.get(i)));
        }
    }

    @After
    public void afterEachTest() {
        if (mission != null)
            missionService.delete(mission);
        monsters.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(monsters.getFirst());

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(monsters);

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(3, counter);
    }

    @Test
    public void findOne() {
        objectUnderTest.save(monsters.getFirst());

        assertNotNull(objectUnderTest.findOne(monsters.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(monsters.getFirst());

        assertNotNull(objectUnderTest.findOne(monsters.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(monsters);

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(3, counter);
    }

    @Test
    public void delete() {
        objectUnderTest.save(monsters.getFirst());

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(monsters.getFirst());

        counter = (long) objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void delete1() {
        objectUnderTest.save(monsters.getFirst());

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(monsters.getFirst().getId());

        counter = (long) objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void delete2() {
        objectUnderTest.save(monsters);

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(3, counter);

        objectUnderTest.delete(monsters);

        counter = (long) objectUnderTest.findAll().size();

        assertEquals(0, counter);
    }

    @Test
    public void deleteCheckRelationsAreNull() {
        objectUnderTest.save(monsters.getFirst());
        Monster monster = objectUnderTest.findOne(monsters.getFirst());

        assertNotNull(monster);

        objectUnderTest.delete(monster);

        assertNull(objectUnderTest.findOne(monster.getId()));
        assertNull(statisticService.findOne(monster.getStatistic().getId()));
        assertNull(imageService.findOne(monster.getImage()));
    }

    @Test
    public void deleteFromMission() {
        Monster monster = monsters.getFirst();
        mission = new Mission(1, "", new BigDecimal("1"), monster);
        missionService.save(mission);
        monster = objectUnderTest.findOne(monster);

        assertNotNull(monster);

        objectUnderTest.delete(monster);

        assertNull(objectUnderTest.findOne(monster));
        assertNull(statisticService.findOne(monster.getStatistic()));
        assertNull(imageService.findOne(monster.getImage()));
        assertNull(missionService.findOne(mission.getId()).getMonster());
        assertNotNull(missionService.findOne(mission.getId()));
    }
}