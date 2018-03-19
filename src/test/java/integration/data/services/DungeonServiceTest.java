package integration.data.services;

import game.mightywarriors.data.services.DungeonService;
import game.mightywarriors.data.services.FloorService;
import game.mightywarriors.data.services.ImageService;
import game.mightywarriors.data.services.UserDungeonService;
import game.mightywarriors.data.tables.*;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DungeonServiceTest extends IntegrationTestsConfig {
    @Autowired
    private DungeonService objectUnderTest;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private UserDungeonService userDungeonService;

    private LinkedList<Dungeon> dungeons;
    private LinkedList<Floor> floors;
    private LinkedList<Statistic> statistics;
    private LinkedList<Image> images;
    private LinkedList<Monster> monsters;

    @Before
    public void beforeEachTest() {
        dungeons = new LinkedList<>();
        floors = new LinkedList<>();
        monsters = new LinkedList<>();
        statistics = new LinkedList<>();
        images = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            monsters.add(new Monster(statistics.get(i), images.get(i)));
            floors.add(new Floor(i, i, i, new BigDecimal("1"), new HashSet<>(Collections.singleton(monsters.get(i))), new Item(), new Image()));
            dungeons.add(new Dungeon("name : " + i + 5, i + 5, new Image(), new HashSet(Collections.singletonList(floors.get(i)))));
        }
    }

    @After
    public void afterEachTest() {
        dungeons.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(dungeons.getFirst());

        Dungeon mission = objectUnderTest.find(dungeons.getFirst());

        checker(mission);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(new HashSet<>(dungeons));

        assertNotNull(objectUnderTest.find(dungeons.get(0)));
        assertNotNull(objectUnderTest.find(dungeons.get(1)));
        assertNotNull(objectUnderTest.find(dungeons.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(dungeons.getFirst());

        assertNotNull(objectUnderTest.find(dungeons.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(dungeons.getFirst());

        assertNotNull(objectUnderTest.find(dungeons.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(new HashSet<>(dungeons));

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(dungeons.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(dungeons.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(dungeons.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(dungeons.getFirst());

        checker(objectUnderTest.find(dungeons.getFirst()));

        objectUnderTest.delete(dungeons.getFirst());

        checkerNulls(dungeons.getFirst());
    }

    @Test
    public void delete1() {
        objectUnderTest.save(dungeons.getFirst());

        checker(objectUnderTest.find(dungeons.getFirst()));

        objectUnderTest.delete(dungeons.getFirst().getId());

        checkerNulls(dungeons.getFirst());
    }

    @Test
    public void delete2() {
        objectUnderTest.save(new HashSet<>(dungeons));

        dungeons.forEach(this::checker);

        objectUnderTest.delete(dungeons);

        dungeons.forEach(this::checkerNulls);
    }

    @Test
    public void deleteFromUserDungeon() {
        objectUnderTest.save(dungeons.getFirst());

        UserDungeon userDungeon = new UserDungeon();
        userDungeon.setDungeon(dungeons.getFirst());
        userDungeonService.save(userDungeon);

        objectUnderTest.delete(dungeons.getFirst());

        assertNotNull(userDungeonService.find(userDungeon));
        assertNull(userDungeonService.find(userDungeon).getDungeon());
        assertNull(objectUnderTest.find(dungeons.getFirst()));
    }

    private void checker(Dungeon dungeon) {
        assertNotNull(objectUnderTest.find(dungeon));

        dungeon.getFloors().forEach(x -> assertNotNull(floorService.find(x)));
        assertNotNull(imageService.find(dungeon.getImage()));
    }

    private void checkerNulls(Dungeon dungeon) {
        assertNull(objectUnderTest.find(dungeon));
        dungeon.getFloors().forEach(x -> assertNull(floorService.find(x)));
    }
}
