package integration.data.services;

import game.mightywarriors.data.services.DungeonFightService;
import game.mightywarriors.data.tables.DungeonFight;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DungeonFightServiceTest extends IntegrationTestsConfig {
    @Autowired
    private DungeonFightService objectUnderTest;
    private HashSet<DungeonFight> missionFights;

    @Before
    public void beforeEachTest() {
        missionFights = new HashSet<>();

        setUp();
    }

    @After
    public void afterEachTest() {
        objectUnderTest.delete(missionFights);
    }

    @Test
    public void save() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(missionFights);

        Iterator<DungeonFight> iterator = missionFights.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(missionFights);

        Iterator<DungeonFight> iterator = missionFights.iterator();
        List<DungeonFight> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next()));

        objectUnderTest.delete(missionFights.iterator().next());

        assertNull(objectUnderTest.find(missionFights.iterator().next()));
    }

    @Test
    public void delete1() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next()));

        objectUnderTest.delete(missionFights.iterator().next().getId());

        assertNull(objectUnderTest.find(missionFights.iterator().next()));
    }

    @Test
    public void delete2() {
        objectUnderTest.save(missionFights);

        Iterator<DungeonFight> iterator = missionFights.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(missionFights);

        iterator = missionFights.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void deleteFromUser() {
        DungeonFight dungeonFight = missionFights.iterator().next();

    }

    private void setUp() {
        DungeonFight dungeonFight;

        for (int i = 0; i < 3; i++) {
            dungeonFight = new DungeonFight();
            dungeonFight.setBlockUntil(new Timestamp(System.currentTimeMillis() + (i * 1000)));

            missionFights.add(dungeonFight);
        }
    }
}