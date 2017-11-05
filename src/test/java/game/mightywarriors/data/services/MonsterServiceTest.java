package game.mightywarriors.data.services;

import game.mightywarriors.data.tables.Image;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.Statistic;
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
public class MonsterServiceTest {
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
    public void save() throws Exception {
        objectUnderTest.save(monsters.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);
    }

    @Test
    public void saveforSameUrl() throws Exception {
        objectUnderTest.save(monsters.get(2));
        Monster monster = monsters.getFirst();
        monster.getImage().setUrl(monsters.get(2).getImage().getUrl());
        objectUnderTest.save(monster);

        monster = objectUnderTest.findOne(objectUnderTest.findOne(monster));

        assertNull(monster.getImage());
    }

    @Test
    public void saveCollection() throws Exception {
        objectUnderTest.save(monsters);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void findOne() throws Exception {
        objectUnderTest.save(monsters.getFirst());

        assertNotNull(objectUnderTest.findOne(monsters.getFirst()));
    }

    @Test
    public void findOne1() throws Exception {
        objectUnderTest.save(monsters.getFirst());

        assertNotNull(objectUnderTest.findOne(monsters.getFirst().getId()));
    }

    @Test
    public void findAll() throws Exception {
        objectUnderTest.save(monsters);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void delete() throws Exception {
        objectUnderTest.save(monsters.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(monsters.getFirst());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
    }

    @Test
    public void delete1() throws Exception {
        objectUnderTest.save(monsters.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(monsters.getFirst().getId());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
    }

    @Test
    public void delete2() throws Exception {
        objectUnderTest.save(monsters);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);

        objectUnderTest.delete(monsters);

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
    }

    @Test
    public void deleteCheckRelationsAreNull() {
        objectUnderTest.save(monsters.getFirst());
        Monster monster = objectUnderTest.findOne(monsters.getFirst());

        assertNotNull(monster);

        objectUnderTest.delete(monster);

        assertNull(objectUnderTest.findOne(monster.getId()));
        assertNull(statisticService.findOne(monster.getId()));
        assertNull(imageService.findOne(monster.getId()));
    }

    @Test
    public void deleteFromMission() {
        Monster monster = monsters.getFirst();
        mission = new Mission(1, "", new BigDecimal("1"), monster);
        missionService.save(mission);
        monster = objectUnderTest.findOne(monster);

        assertNotNull(monster);

        objectUnderTest.delete(monster);

        assertNull(objectUnderTest.findOne(monster.getId()));
        assertNull(statisticService.findOne(monster.getId()));
        assertNull(imageService.findOne(monster.getId()));
        assertNull(missionService.findOne(mission.getId()).getMonster());
        assertNotNull(missionService.findOne(mission.getId()));
    }
}
