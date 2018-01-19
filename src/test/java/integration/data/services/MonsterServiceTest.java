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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MonsterServiceTest extends IntegrationTestsConfig {
    @Autowired
    private MonsterService objectUnderTest;
    @Autowired
    private ImageService imageService;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private MissionService missionService;

    private HashSet<Statistic> statistics;
    private HashSet<Image> images;
    private HashSet<Monster> monsters;
    private Mission mission;

    @Before
    public void beforeEachTest() {
        monsters = new HashSet<>();
        statistics = new HashSet<>();
        images = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            ArrayList<Statistic> statistics = new ArrayList<>(this.statistics);
            ArrayList<Image> images = new ArrayList<>(this.images);
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
        objectUnderTest.save(monsters.iterator().next());

        assertNotNull(objectUnderTest.find(monsters.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(monsters);

        Iterator<Monster> iterator = monsters.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(monsters.iterator().next());

        assertNotNull(objectUnderTest.find(monsters.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(monsters.iterator().next());

        assertNotNull(objectUnderTest.find(monsters.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(monsters);

        Iterator<Monster> iterator = monsters.iterator();
        List<Monster> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(monsters.iterator().next());

        Monster one = objectUnderTest.find(monsters.iterator().next());

        assertNotNull(one);

        objectUnderTest.delete(monsters.iterator().next());
        one = objectUnderTest.find(monsters.iterator().next());

        assertNull(one);
    }

    @Test
    public void delete1() {
        objectUnderTest.save(monsters.iterator().next());

        Monster one = objectUnderTest.find(monsters.iterator().next());

        assertNotNull(one);

        objectUnderTest.delete(one);

        assertNull(objectUnderTest.find(one));
    }

    @Test
    public void delete2() {
        objectUnderTest.save(monsters);

        Iterator<Monster> iterator = monsters.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(monsters);

        iterator = monsters.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void deleteCheckRelationsAreNull() {
        objectUnderTest.save(monsters.iterator().next());
        Monster monster = objectUnderTest.find(monsters.iterator().next());

        assertNotNull(monster);

        objectUnderTest.delete(monster);

        assertNull(objectUnderTest.find(monster.getId()));
        assertNull(statisticService.find(monster.getStatistic().getId()));
        assertNull(imageService.find(monster.getImage()));
    }

    @Test
    public void deleteFromMission() {
        Monster monster = monsters.iterator().next();
        mission = new Mission(1, "", new BigDecimal("1"), monster);
        missionService.save(mission);
        monster = objectUnderTest.find(monster);
        assertNotNull(monster);

        objectUnderTest.delete(monster);

        assertNull(objectUnderTest.find(monster));
        assertNull(statisticService.find(monster.getStatistic()));
        assertNull(imageService.find(monster.getImage()));
        assertNotNull(missionService.find(mission.getId()));
        assertNull(missionService.find(mission.getId()).getMonster());
    }
}
