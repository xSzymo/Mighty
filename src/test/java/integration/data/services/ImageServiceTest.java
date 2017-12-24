package integration.data.services;


import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.ImageService;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.MonsterService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Image;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Monster;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class ImageServiceTest extends IntegrationTestsConfig {
    @Autowired
    private ImageService objectUnderTest;
    @Autowired
    private ItemService itemService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private ChampionService championService;

    private LinkedList<Image> images;
    private String myWeirdURL = "https://avatars1.githubusercontent.com/u/15995737?s=460&v=4";
    private Item item;
    private Champion champion;
    private Monster monster;

    @Before
    public void beforeEachTest() {
        images = new LinkedList<>();
        images.add(new Image(myWeirdURL));
        images.add(new Image(myWeirdURL + "1"));
        images.add(new Image(myWeirdURL + "2"));
    }

    @After
    public void afterEachTest() {
        if (item != null)
            itemService.delete(item);
        if (champion != null)
            championService.delete(champion);
        if (monster != null)
            monsterService.delete(monster);

        images.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(images.getFirst());

        assertNotNull(objectUnderTest.findOne(images.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(images);

        assertNotNull(objectUnderTest.findOne(images.get(0)));
        assertNotNull(objectUnderTest.findOne(images.get(1)));
        assertNotNull(objectUnderTest.findOne(images.get(2)));
    }

//    @Test(expected = Exception.class)
//    public void saveWithSameURL() throws Exception {
//        objectUnderTest.save(images.getFirst());
//
//        long counter = objectUnderTest.findAll().stream().count();
//
//        assertEquals(1, counter);
//
//        objectUnderTest.save(new Image(myWeirdURL));
//    }
//
//    @Test(expected = Exception.class)
//    public void saveCollectionWithSameURL() throws Exception {
//        images.add(new Image(myWeirdURL));
//
//        objectUnderTest.save(images);
//    }

    @Test
    public void findOne() {
        objectUnderTest.save(images.getFirst());

        assertNotNull(objectUnderTest.findOne(images.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(images.getFirst());

        assertNotNull(objectUnderTest.findOne(images.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(images);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(images.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(images.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(images.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(images.getFirst());

        assertNotNull(objectUnderTest.findOne(images.getFirst()));

        objectUnderTest.delete(images.getFirst());

        assertNull(objectUnderTest.findOne(images.getFirst()));
        images.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(images.getFirst());

        assertNotNull(objectUnderTest.findOne(images.getFirst()));

        objectUnderTest.delete(images.getFirst());

        assertNull(objectUnderTest.findOne(images.getFirst()));
        images.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(images);

        assertNotNull(objectUnderTest.findOne(images.get(0)));
        assertNotNull(objectUnderTest.findOne(images.get(1)));
        assertNotNull(objectUnderTest.findOne(images.get(2)));

        objectUnderTest.delete(images);

        assertNull(objectUnderTest.findOne(images.get(0)));
        assertNull(objectUnderTest.findOne(images.get(1)));
        assertNull(objectUnderTest.findOne(images.get(2)));
    }

    @Test
    public void deleteFromItem() {
        item = new Item();
        item.setImage(images.getFirst());

        itemService.save(item);

        assertNotNull(itemService.findOne(item));
        assertNotNull(objectUnderTest.findOne(images.getFirst()));

        objectUnderTest.delete(images.getFirst());

        assertNotNull(itemService.findOne(item));
        assertNull(itemService.findOne(item).getImage());
    }

    @Test
    public void deleteFromChampion() {
        champion = new Champion();
        champion.setImage(images.getFirst());

        championService.save(champion);

        assertNotNull(championService.findOne(champion));
        assertNotNull(objectUnderTest.findOne(images.getFirst()));

        objectUnderTest.delete(images.getFirst());

        assertNotNull(championService.findOne(champion));
        assertNull(championService.findOne(champion).getImage());
    }

    @Test
    public void deleteFromMonster() {
        monster = new Monster();
        monster.setImage(images.getFirst());

        monsterService.save(monster);

        assertNotNull(monsterService.findOne(monster));
        assertNotNull(objectUnderTest.findOne(images.getFirst()));

        objectUnderTest.delete(images.getFirst());

        assertNotNull(monsterService.findOne(monster));
        assertNull(monsterService.findOne(monster).getImage());

    }
}
