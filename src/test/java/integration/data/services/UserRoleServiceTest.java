package integration.data.services;

import game.mightywarriors.data.services.UserRoleService;
import game.mightywarriors.data.tables.UserRole;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserRoleServiceTest extends IntegrationTestsConfig {
    @Autowired
    private UserRoleService objectUnderTest;

    private LinkedList<UserRole> userRoles;

    @Before
    public void beforeEachTest() {
        userRoles = new LinkedList<>();
        userRoles.add(new UserRole("admin"));
        userRoles.add(new UserRole("user"));
        userRoles.add(new UserRole("other"));
    }

    @After
    public void afterEachTest() {
        userRoles.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(userRoles.getFirst());

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(userRoles);

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(3, counter);
    }

    @Test
    public void findOne() {
        objectUnderTest.save(userRoles.getFirst());

        assertNotNull(objectUnderTest.findOne(userRoles.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(userRoles.getFirst());

        assertNotNull(objectUnderTest.findOne(userRoles.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(userRoles);

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(3, counter);
    }

    @Test
    public void delete() {
        objectUnderTest.save(userRoles.getFirst());

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(userRoles.getFirst());

        counter = (long) objectUnderTest.findAll().size();

        assertEquals(0, counter);
        userRoles.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(userRoles.getFirst());

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(1, counter);

        objectUnderTest.delete(userRoles.getFirst().getId());

        counter = (long) objectUnderTest.findAll().size();

        assertEquals(0, counter);
        userRoles.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(userRoles);

        long counter = (long) objectUnderTest.findAll().size();

        assertEquals(3, counter);

        objectUnderTest.delete(userRoles);

        counter = (long) objectUnderTest.findAll().size();

        assertEquals(0, counter);
        userRoles.clear();
    }
}
