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
import static org.junit.Assert.assertNull;

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

        assertNotNull(objectUnderTest.findOne(userRoles.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(userRoles);

        assertNotNull(objectUnderTest.findOne(userRoles.get(0)));
        assertNotNull(objectUnderTest.findOne(userRoles.get(1)));
        assertNotNull(objectUnderTest.findOne(userRoles.get(2)));
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

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(userRoles.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(userRoles.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(userRoles.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(userRoles.getFirst());

        assertNotNull(objectUnderTest.findOne(userRoles.getFirst()));

        objectUnderTest.delete(userRoles.getFirst());

        assertNull(objectUnderTest.findOne(userRoles.getFirst()));
        userRoles.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(userRoles.getFirst());

        assertNotNull(objectUnderTest.findOne(userRoles.getFirst()));

        objectUnderTest.delete(userRoles.getFirst().getId());

        assertNull(objectUnderTest.findOne(userRoles.getFirst()));
        userRoles.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(userRoles);

        assertNotNull(objectUnderTest.findOne(userRoles.get(0)));
        assertNotNull(objectUnderTest.findOne(userRoles.get(1)));
        assertNotNull(objectUnderTest.findOne(userRoles.get(2)));

        objectUnderTest.delete(userRoles);

        assertNull(objectUnderTest.findOne(userRoles.get(0)));
        assertNull(objectUnderTest.findOne(userRoles.get(1)));
        assertNull(objectUnderTest.findOne(userRoles.get(2)));
        userRoles.clear();
    }
}
