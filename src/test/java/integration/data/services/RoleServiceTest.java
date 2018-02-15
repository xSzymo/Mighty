package integration.data.services;

import game.mightywarriors.data.services.RoleService;
import game.mightywarriors.data.tables.Role;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RoleServiceTest extends IntegrationTestsConfig {
    @Autowired
    private RoleService objectUnderTest;

    private LinkedList<Role> roles;

    @Before
    public void beforeEachTest() {
        roles = new LinkedList<>();
        roles.add(new Role("admin"));
        roles.add(new Role("user"));
        roles.add(new Role("other"));
    }

    @After
    public void afterEachTest() {
        roles.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(roles.getFirst());

        assertNotNull(objectUnderTest.find(roles.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(roles);

        assertNotNull(objectUnderTest.find(roles.get(0)));
        assertNotNull(objectUnderTest.find(roles.get(1)));
        assertNotNull(objectUnderTest.find(roles.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(roles.getFirst());

        assertNotNull(objectUnderTest.find(roles.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(roles.getFirst());

        assertNotNull(objectUnderTest.find(roles.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(roles);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(roles.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(roles.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(roles.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(roles.getFirst());

        assertNotNull(objectUnderTest.find(roles.getFirst()));

        objectUnderTest.delete(roles.getFirst());

        assertNull(objectUnderTest.find(roles.getFirst()));
        roles.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(roles.getFirst());

        assertNotNull(objectUnderTest.find(roles.getFirst()));

        objectUnderTest.delete(roles.getFirst().getId());

        assertNull(objectUnderTest.find(roles.getFirst()));
        roles.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(roles);

        assertNotNull(objectUnderTest.find(roles.get(0)));
        assertNotNull(objectUnderTest.find(roles.get(1)));
        assertNotNull(objectUnderTest.find(roles.get(2)));

        objectUnderTest.delete(roles);

        assertNull(objectUnderTest.find(roles.get(0)));
        assertNull(objectUnderTest.find(roles.get(1)));
        assertNull(objectUnderTest.find(roles.get(2)));
        roles.clear();
    }
}
