package integration.data.services;

import game.mightywarriors.data.services.DungeonService;
import game.mightywarriors.data.services.UserDungeonService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserDungeonServiceTest extends IntegrationTestsConfig {
    @Autowired
    private UserDungeonService objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private DungeonService dungeonService;

    private LinkedList<UserDungeon> userDungeons;
    private LinkedList<Dungeon> dungeons;
    private LinkedList<Floor> floors;
    private LinkedList<Statistic> statistics;
    private LinkedList<Image> images;
    private LinkedList<Monster> monsters;

    @Before
    public void beforeEachTest() {
        dungeons = new LinkedList<>();
        userDungeons = new LinkedList<>();
        floors = new LinkedList<>();
        monsters = new LinkedList<>();
        statistics = new LinkedList<>();
        images = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            monsters.add(new Monster(statistics.get(i), images.get(i)));
            floors.add(new Floor(i, i, i, new BigDecimal("1"), new HashSet<>(Collections.singleton(monsters.get(i))), new Item(), new Image()));
            dungeons.add(new Dungeon("name : " + i, i + 10, new Image(), new HashSet(Arrays.asList(floors.get(i)))));
            userDungeons.add(new UserDungeon(dungeons.get(i)));
        }
    }

    @After
    public void afterEachTest() {
        userDungeons.forEach(objectUnderTest::delete);
        dungeons.forEach(dungeonService::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(userDungeons.getFirst());

        UserDungeon mission = objectUnderTest.find(userDungeons.getFirst());

        checker(mission);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(new HashSet<>(userDungeons));

        assertNotNull(objectUnderTest.find(userDungeons.get(0)));
        assertNotNull(objectUnderTest.find(userDungeons.get(1)));
        assertNotNull(objectUnderTest.find(userDungeons.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(userDungeons.getFirst());

        assertNotNull(objectUnderTest.find(userDungeons.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(userDungeons.getFirst());

        assertNotNull(objectUnderTest.find(userDungeons.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(new HashSet<>(userDungeons));

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(userDungeons.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(userDungeons.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(userDungeons.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(userDungeons.getFirst());

        checker(objectUnderTest.find(userDungeons.getFirst()));

        objectUnderTest.delete(userDungeons.getFirst());

        checkerNulls(userDungeons.getFirst());
    }

    @Test
    public void delete1() {
        objectUnderTest.save(userDungeons.getFirst());

        checker(objectUnderTest.find(userDungeons.getFirst()));

        objectUnderTest.delete(userDungeons.getFirst().getId());

        checkerNulls(userDungeons.getFirst());
    }

    @Test
    public void delete2() {
        objectUnderTest.save(new HashSet<>(userDungeons));

        userDungeons.forEach(this::checker);

        objectUnderTest.delete(userDungeons);

        userDungeons.forEach(this::checkerNulls);
    }

    @Test
    public void deleteFromUser() {
        User user = new User("test", "test", "test");
        user.setDungeon(userDungeons.getFirst());
        userService.save(user);

        objectUnderTest.delete(userDungeons.getFirst());

        assertNull(objectUnderTest.find(userDungeons.getFirst()));
        assertNotNull(userService.find(user));
        assertNull(userService.find(user).getDungeon());
    }

    private void checker(UserDungeon dungeon) {
        assertNotNull(objectUnderTest.find(dungeon));
    }

    private void checkerNulls(UserDungeon dungeon) {
        assertNull(objectUnderTest.find(dungeon));
    }
}
