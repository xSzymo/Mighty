package integration.data.services;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.MonsterService;
import game.mightywarriors.data.services.StatisticService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.WeaponType;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

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
    public void save() {
        objectUnderTest.save(statistics.getFirst());

        assertNotNull(objectUnderTest.findOne(statistics.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(statistics);

        assertNotNull(objectUnderTest.findOne(statistics.get(0)));
        assertNotNull(objectUnderTest.findOne(statistics.get(1)));
        assertNotNull(objectUnderTest.findOne(statistics.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(statistics.getFirst());

        assertNotNull(objectUnderTest.findOne(statistics.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(statistics.getFirst());

        assertNotNull(objectUnderTest.findOne(statistics.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(statistics);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(statistics.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(statistics.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(statistics.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(statistics.getFirst());

        assertNotNull(objectUnderTest.findOne(statistics.getFirst()));

        objectUnderTest.delete(statistics.getFirst());

        assertNull(objectUnderTest.findOne(statistics.getFirst()));
        statistics.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(statistics.getFirst());

        assertNotNull(objectUnderTest.findOne(statistics.getFirst()));

        objectUnderTest.delete(statistics.getFirst().getId());

        assertNull(objectUnderTest.findOne(statistics.getFirst()));
        statistics.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(statistics);

        assertNotNull(objectUnderTest.findOne(statistics.get(0)));
        assertNotNull(objectUnderTest.findOne(statistics.get(1)));
        assertNotNull(objectUnderTest.findOne(statistics.get(2)));

        objectUnderTest.delete(statistics);

        assertNull(objectUnderTest.findOne(statistics.get(0)));
        assertNull(objectUnderTest.findOne(statistics.get(1)));
        assertNull(objectUnderTest.findOne(statistics.get(2)));
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