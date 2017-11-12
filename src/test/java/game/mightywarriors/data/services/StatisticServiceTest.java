package game.mightywarriors.data.services;

import game.mightywarriors.data.enums.WeaponType;
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
public class StatisticServiceTest {
    @Autowired
    private StatisticService objectUnderTest;
    @Autowired
    private ChampionService championService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private ItemService itemService;

    private LinkedList<Statistic> statistics;
    private Item item;
    private Champion champion;
    private Monster monster;

    @Before
    public void beforeEachTest() {
        statistics = new LinkedList<>();
        statistics.add(new Statistic());
        statistics.add(new Statistic());
        statistics.add(new Statistic());
    }

    @After
    public void afterEachTest() {
        itemService.delete(item);
        monsterService.delete(monster);
        championService.delete(champion);
        statistics.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() throws Exception {
        objectUnderTest.save(statistics.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() throws Exception {
        objectUnderTest.save(statistics);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void findOne() throws Exception {
        objectUnderTest.save(statistics.getFirst());

        assertNotNull(objectUnderTest.findOne(statistics.getFirst()));
    }

    @Test
    public void findOne1() throws Exception {
        objectUnderTest.save(statistics.getFirst());

        assertNotNull(objectUnderTest.findOne(statistics.getFirst().getId()));
    }

    @Test
    public void findAll() throws Exception {
        objectUnderTest.save(statistics);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void delete() throws Exception {
        objectUnderTest.save(statistics.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(statistics.getFirst());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        statistics.clear();
    }

    @Test
    public void delete1() throws Exception {
        objectUnderTest.save(statistics.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(statistics.getFirst().getId());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        statistics.clear();
    }

    @Test
    public void delete2() throws Exception {
        objectUnderTest.save(statistics);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);

        objectUnderTest.delete(statistics);

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        statistics.clear();
    }

    @Test
    public void deleteFromItem() {
        Statistic statistic = statistics.getFirst();
        item = new Item("", WeaponType.OFFHAND, statistic, 1);
        itemService.save(item);

        objectUnderTest.delete(statistic);

        assertNull(objectUnderTest.findOne(statistic.getId()));
        assertNull(itemService.findOne(item.getId()).getStatistic());
        assertNotNull(itemService.findOne(item.getId()));
    }

    @Test
    public void deleteFromMonster() {
        Statistic statistic = statistics.getFirst();
        monster = new Monster(statistic, null);
        monsterService.save(monster);

        objectUnderTest.delete(statistic);

        assertNull(objectUnderTest.findOne(statistic.getId()));
        assertNull(monsterService.findOne(monster.getId()).getStatistic());
        assertNotNull(monsterService.findOne(monster.getId()));
    }

    @Test
    public void deleteFromChampion() {
        Statistic statistic = statistics.getFirst();
        champion = new Champion(statistic, (Equipment) null);
        championService.save(champion);

        objectUnderTest.delete(statistic);

        assertNull(objectUnderTest.findOne(statistic.getId()));
        assertNull(championService.findOne(champion.getId()).getStatistic());
        assertNotNull(championService.findOne(champion.getId()));
    }
}