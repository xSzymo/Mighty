package integration.game.mightywarriors.data.services;

import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.tables.Division;
import game.mightywarriors.other.enums.League;
import integration.game.mightywarriors.config.IntegrationTestsConfig;
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

    private LinkedList<Division> userRoles;

    @Before
    public void beforeEachTest() {
        userRoles = new LinkedList<>();
        userRoles.add(new Division(League.DIAMOND));
        userRoles.add(new Division(League.GOLD));
        userRoles.add(new Division(League.SILVER));
    }

    @After
    public void afterEachTest() {
        userRoles.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() throws Exception {
        objectUnderTest.save(userRoles.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() throws Exception {
        objectUnderTest.save(userRoles);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void findOne() throws Exception {
        objectUnderTest.save(userRoles.getFirst());

        assertNotNull(objectUnderTest.findOne(userRoles.getFirst()));
    }

    @Test
    public void findOne1() throws Exception {
        objectUnderTest.save(userRoles.getFirst());

        assertNotNull(objectUnderTest.findOne(userRoles.getFirst().getId()));
    }

    @Test
    public void findAll() throws Exception {
        objectUnderTest.save(userRoles);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void delete() throws Exception {
        objectUnderTest.save(userRoles.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(userRoles.getFirst());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        userRoles.clear();
    }

    @Test
    public void delete1() throws Exception {
        objectUnderTest.save(userRoles.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(userRoles.getFirst().getId());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        userRoles.clear();
    }

    @Test
    public void delete2() throws Exception {
        objectUnderTest.save(userRoles);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);

        objectUnderTest.delete(userRoles);

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        userRoles.clear();
    }
}
