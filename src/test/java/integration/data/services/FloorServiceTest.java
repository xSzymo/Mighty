package integration.data.services;

import game.mightywarriors.data.services.DungeonService;
import game.mightywarriors.data.services.FloorService;
import game.mightywarriors.data.services.ImageService;
import game.mightywarriors.data.services.MonsterService;
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

import static org.junit.Assert.*;

public class FloorServiceTest extends IntegrationTestsConfig {
    @Autowired
    private FloorService objectUnderTest;
    @Autowired
    private ImageService imageService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private DungeonService dungeonService;

    private LinkedList<Floor> floors;
    private LinkedList<Statistic> statistics;
    private LinkedList<Image> images;
    private LinkedList<Monster> monsters;

    @Before
    public void beforeEachTest() {
        floors = new LinkedList<>();
        monsters = new LinkedList<>();
        statistics = new LinkedList<>();
        images = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            monsters.add(new Monster(statistics.get(i), images.get(i)));
            floors.add(new Floor(i, i, i, new BigDecimal("1"), new HashSet<>(Collections.singleton(monsters.get(i))), new Item(), new Image()));
        }
    }

    @After
    public void afterEachTest() {
        floors.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(floors.getFirst());

        Floor mission = objectUnderTest.find(floors.getFirst());

        checker(mission);
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(new HashSet<>(floors));

        assertNotNull(objectUnderTest.find(floors.get(0)));
        assertNotNull(objectUnderTest.find(floors.get(1)));
        assertNotNull(objectUnderTest.find(floors.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(floors.getFirst());

        assertNotNull(objectUnderTest.find(floors.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(floors.getFirst());

        assertNotNull(objectUnderTest.find(floors.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(new HashSet<>(floors));

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(floors.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(floors.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(floors.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(floors.getFirst());

        checker(objectUnderTest.find(floors.getFirst()));

        objectUnderTest.delete(floors.getFirst());

        checkerNulls(floors.getFirst());
    }

    @Test
    public void delete1() {
        objectUnderTest.save(floors.getFirst());

        checker(objectUnderTest.find(floors.getFirst()));

        objectUnderTest.delete(floors.getFirst().getId());

        checkerNulls(floors.getFirst());
    }

    @Test
    public void delete2() {
        objectUnderTest.save(new HashSet<>(floors));

        floors.forEach(this::checker);

        objectUnderTest.delete(floors);

        floors.forEach(this::checkerNulls);
    }

    @Test
    public void deleteFromDungeon() {
        objectUnderTest.save(floors.getFirst());

        Dungeon dungeon = new Dungeon();
        dungeon.getFloors().add(floors.getFirst());
        dungeonService.save(dungeon);

        objectUnderTest.delete(floors.getFirst());

        assertNotNull(dungeonService.find(dungeon));
        assertEquals(0, dungeonService.find(dungeon).getFloors().size());
    }

    private void checker(Floor floor) {
        assertNotNull(objectUnderTest.find(floor));

        floor.getMonsters().forEach(x -> assertNotNull(monsterService.find(x)));
        assertNotNull(imageService.find(floor.getImage()));
    }

    private void checkerNulls(Floor floor) {
        assertNull(objectUnderTest.find(floor));
        floor.getMonsters().forEach(x -> assertNotNull(monsterService.find(x)));
    }
}
