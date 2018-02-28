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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ImageServiceTest extends IntegrationTestsConfig {
    @Autowired
    private ImageService objectUnderTest;
    @Autowired
    private ItemService itemService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private ChampionService championService;

    private HashSet<Image> images;
    private String myWeirdURL = "https://avatars1.githubusercontent.com/u/15995737?s=460&v=4";
    private Item item;
    private Champion champion;
    private Monster monster;

    @Before
    public void beforeEachTest() {
        images = new HashSet<>();
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
        objectUnderTest.save(images.iterator().next());

        assertNotNull(objectUnderTest.find(images.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(images);

        Iterator<Image> iterator = images.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
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
        objectUnderTest.save(images.iterator().next());

        assertNotNull(objectUnderTest.find(images.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(images.iterator().next());

        assertNotNull(objectUnderTest.find(images.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(images);

        Iterator<Image> iterator = images.iterator();
        List<Image> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(images.iterator().next());

        assertNotNull(objectUnderTest.find(images.iterator().next()));

        objectUnderTest.delete(images.iterator().next());

        assertNull(objectUnderTest.find(images.iterator().next()));
        images.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(images.iterator().next());

        assertNotNull(objectUnderTest.find(images.iterator().next()));

        objectUnderTest.delete(images.iterator().next());

        assertNull(objectUnderTest.find(images.iterator().next()));
        images.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(images);

        Iterator<Image> iterator = images.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(images);

        iterator = images.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void deleteFromItem() {
        item = new Item();
        item.setImageLight(images.iterator().next());

        itemService.save(item);

        assertNotNull(itemService.find(item));
        assertNotNull(objectUnderTest.find(images.iterator().next()));

        objectUnderTest.delete(images.iterator().next());

        assertNotNull(itemService.find(item));
        assertNull(itemService.find(item).getImageLight());
    }

    @Test
    public void deleteFromChampion() {
        champion = new Champion();
        champion.setImageLight(images.iterator().next());

        championService.save(champion);

        assertNotNull(championService.find(champion));
        assertNotNull(objectUnderTest.find(images.iterator().next()));

        objectUnderTest.delete(images.iterator().next());

        assertNotNull(championService.find(champion));
        assertNull(championService.find(champion).getImageLight());
    }

    @Test
    public void deleteFromMonster() {
        monster = new Monster();
        monster.setImageLight(images.iterator().next());

        monsterService.save(monster);

        assertNotNull(monsterService.find(monster));
        assertNotNull(objectUnderTest.find(images.iterator().next()));

        objectUnderTest.delete(images.iterator().next());

        assertNotNull(monsterService.find(monster));
        assertNull(monsterService.find(monster).getImageLight());

    }
}
