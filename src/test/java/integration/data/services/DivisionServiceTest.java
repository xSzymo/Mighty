package integration.data.services;

import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.tables.Division;
import game.mightywarriors.other.enums.League;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DivisionServiceTest extends IntegrationTestsConfig {
    @Autowired
    private DivisionService objectUnderTest;

    private LinkedList<Division> divisions;

    @Before
    public void beforeEachTest() {
        divisions = new LinkedList<>();
        divisions.add(new Division(League.DIAMOND));
        divisions.add(new Division(League.GOLD));
        divisions.add(new Division(League.SILVER));
    }

    @After
    public void afterEachTest() {
        divisions.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(divisions.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(7, counter);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(divisions);

        long counter = objectUnderTest.findAll().size();

        assertEquals(9, counter);
    }

    @Test
    public void findOne() {
        objectUnderTest.save(divisions.getFirst());

        assertNotNull(objectUnderTest.find(divisions.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(divisions.getFirst());

        assertNotNull(objectUnderTest.find(divisions.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(divisions);

        long counter = objectUnderTest.findAll().size();

        assertEquals(9, counter);
    }

    @Test
    public void delete() {
        objectUnderTest.save(divisions.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(7, counter);

        objectUnderTest.delete(divisions.getFirst());

        counter = objectUnderTest.findAll().size();

        assertEquals(6, counter);
        divisions.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(divisions.getFirst());

        long counter = objectUnderTest.findAll().size();

        assertEquals(7, counter);

        objectUnderTest.delete(divisions.getFirst().getId());

        counter = objectUnderTest.findAll().size();

        assertEquals(6, counter);
        divisions.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(divisions);

        long counter = objectUnderTest.findAll().size();

        assertEquals(9, counter);

        objectUnderTest.delete(divisions);

        counter = objectUnderTest.findAll().size();

        assertEquals(6, counter);
        divisions.clear();
    }
}
