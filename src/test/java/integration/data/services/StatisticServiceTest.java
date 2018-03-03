package integration.data.services;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.MonsterService;
import game.mightywarriors.data.services.StatisticService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class StatisticServiceTest extends IntegrationTestsConfig {
    @Autowired
    private StatisticService objectUnderTest;
    @Autowired
    private ChampionService championService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private ItemService itemService;

    private HashSet<Statistic> statistics;
    private Item item;
    private Champion champion;
    private Monster monster;

    @Before
    public void beforeEachTest() {
        statistics = new HashSet<>();
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
    public void save() {
        objectUnderTest.save(statistics.iterator().next());

        assertNotNull(objectUnderTest.find(statistics.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(statistics);

        Iterator<Statistic> iterator = statistics.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(statistics.iterator().next());

        assertNotNull(objectUnderTest.find(statistics.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(statistics.iterator().next());

        assertNotNull(objectUnderTest.find(statistics.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(statistics);

        Iterator<Statistic> iterator = statistics.iterator();
        List<Statistic> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(statistics.iterator().next());

        assertNotNull(objectUnderTest.find(statistics.iterator().next()));

        objectUnderTest.delete(statistics.iterator().next());

        assertNull(objectUnderTest.find(statistics.iterator().next()));
        statistics.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(statistics.iterator().next());

        assertNotNull(objectUnderTest.find(statistics.iterator().next()));

        objectUnderTest.delete(statistics.iterator().next().getId());

        assertNull(objectUnderTest.find(statistics.iterator().next()));
        statistics.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(statistics);

        Iterator<Statistic> iterator = statistics.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(statistics);

        iterator = statistics.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        statistics.clear();
    }

    @Test
    public void deleteFromItem() {
        Statistic statistic = statistics.iterator().next();
        item = new Item("", ItemType.OFFHAND, statistic, 1);
        itemService.save(item);

        objectUnderTest.delete(statistic);

        assertNull(objectUnderTest.find(statistic.getId()));
        assertNotNull(itemService.find(item.getId()).getStatistic());
        assertNotEquals(itemService.find(item.getId()).getStatistic(), statistic);
    }

    @Test
    public void deleteFromMonster() {
        Statistic statistic = statistics.iterator().next();
        monster = new Monster(statistic, null);
        monsterService.save(monster);

        objectUnderTest.delete(statistic);

        assertNull(objectUnderTest.find(statistic.getId()));
        assertNull(monsterService.find(monster.getId()).getStatistic());
        assertNotNull(monsterService.find(monster.getId()));
    }

    @Test
    public void deleteFromChampion() {
        Statistic statistic = statistics.iterator().next();
        champion = new Champion(statistic, (Equipment) null);
        championService.save(champion);

        objectUnderTest.delete(statistic);

        champion = championService.find(champion);
        assertNull(objectUnderTest.find(statistic.getId()));
        assertNull(champion.getStatistic());
        assertNotNull(championService.find(champion.getId()));
    }
}
