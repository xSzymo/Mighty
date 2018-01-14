package integration.data.services;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.WorkService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Work;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class WorkServiceTest extends IntegrationTestsConfig {
    @Autowired
    private WorkService objectUnderTest;
    @Autowired
    private ChampionService championService;

    private LinkedList<Work> works;


    @Before
    public void beforeEachTest() {
        works = new LinkedList<>();
        works.add(new Work("admin"));
        works.add(new Work("user"));
        works.add(new Work("other"));
    }

    @After
    public void afterEachTest() {
        works.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(works.getFirst());

        assertNotNull(objectUnderTest.findOne(works.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(works);

        assertNotNull(objectUnderTest.findOne(works.get(0)));
        assertNotNull(objectUnderTest.findOne(works.get(1)));
        assertNotNull(objectUnderTest.findOne(works.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(works.getFirst());

        assertNotNull(objectUnderTest.findOne(works.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(works.getFirst());

        assertNotNull(objectUnderTest.findOne(works.getFirst().getNickname()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(works);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getNickname().equals(works.get(0).getNickname())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getNickname().equals(works.get(1).getNickname())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getNickname().equals(works.get(2).getNickname())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(works.getFirst());

        Work one = objectUnderTest.findOne(works.getFirst());

        assertNotNull(one);

        objectUnderTest.delete(works.getFirst());

        one = objectUnderTest.findOne(works.getFirst());
        assertNull(one);
        works.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(works.getFirst());

        Work one = objectUnderTest.findOne(works.getFirst());

        assertNotNull(one);

        objectUnderTest.delete(works.getFirst().getId());

        one = objectUnderTest.findOne(works.getFirst());
        assertNull(one);
        works.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(works);

        assertNotNull(objectUnderTest.findOne(works.get(0)));
        assertNotNull(objectUnderTest.findOne(works.get(1)));
        assertNotNull(objectUnderTest.findOne(works.get(2)));

        objectUnderTest.delete(works);

        assertNull(objectUnderTest.findOne(works.get(0)));
        assertNull(objectUnderTest.findOne(works.get(1)));
        assertNull(objectUnderTest.findOne(works.get(2)));
        works.clear();
    }

    @Test
    public void deleteFromChampion() {
        Work first = works.getFirst();
        Champion champion = new Champion();
        first.setChampion(champion);

        championService.save(champion);
        objectUnderTest.save(works);

        assertNotNull(objectUnderTest.findOne(works.getFirst()));
        assertNotNull(objectUnderTest.findOne(works.getFirst()).getChampion());
        assertNotNull(championService.findOne(champion.getId()));

        objectUnderTest.delete(works.getFirst());

        assertNull(objectUnderTest.findOne(works.getFirst()));
        assertNull(objectUnderTest.findOne(works.getFirst()));
        assertNotNull(championService.findOne(champion.getId()));
        works.clear();
    }
}
