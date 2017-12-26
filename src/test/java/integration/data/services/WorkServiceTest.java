package integration.data.services;

import game.mightywarriors.data.services.WorkService;
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

    private LinkedList<Work> rankings;


    @Before
    public void beforeEachTest() {
        rankings = new LinkedList<>();
        rankings.add(new Work("admin"));
        rankings.add(new Work("user"));
        rankings.add(new Work("other"));
    }

    @After
    public void afterEachTest() {
        rankings.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(rankings.getFirst());

        assertNotNull(objectUnderTest.findOne(rankings.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(rankings);

        assertNotNull(objectUnderTest.findOne(rankings.get(0)));
        assertNotNull(objectUnderTest.findOne(rankings.get(1)));
        assertNotNull(objectUnderTest.findOne(rankings.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(rankings.getFirst());

        assertNotNull(objectUnderTest.findOne(rankings.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(rankings.getFirst());

        assertNotNull(objectUnderTest.findOne(rankings.getFirst().getNickname()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(rankings);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getNickname().equals(rankings.get(0).getNickname())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getNickname().equals(rankings.get(1).getNickname())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getNickname().equals(rankings.get(2).getNickname())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(rankings.getFirst());

        Work one = objectUnderTest.findOne(rankings.getFirst());

        assertNotNull(one);

        objectUnderTest.delete(rankings.getFirst());

        one = objectUnderTest.findOne(rankings.getFirst());
        assertNull(one);
        rankings.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(rankings.getFirst());

        Work one = objectUnderTest.findOne(rankings.getFirst());

        assertNotNull(one);

        objectUnderTest.delete(rankings.getFirst().getId());

        one = objectUnderTest.findOne(rankings.getFirst());
        assertNull(one);
        rankings.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(rankings);

        assertNotNull(objectUnderTest.findOne(rankings.get(0)));
        assertNotNull(objectUnderTest.findOne(rankings.get(1)));
        assertNotNull(objectUnderTest.findOne(rankings.get(2)));

        objectUnderTest.delete(rankings);

        assertNull(objectUnderTest.findOne(rankings.get(0)));
        assertNull(objectUnderTest.findOne(rankings.get(1)));
        assertNull(objectUnderTest.findOne(rankings.get(2)));
        rankings.clear();
    }
}
