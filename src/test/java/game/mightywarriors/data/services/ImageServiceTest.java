package game.mightywarriors.data.services;

import game.mightywarriors.data.tables.Image;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest {
    @Autowired
    private ImageService objectUnderTest;

    private LinkedList<Image> images;
    private String myWeirdURL = "https://avatars1.githubusercontent.com/u/15995737?s=460&v=4";

    @Before
    public void beforeEachTest() {
        images = new LinkedList<>();
        images.add(new Image(myWeirdURL));
        images.add(new Image(myWeirdURL + "1"));
        images.add(new Image(myWeirdURL + "2"));
    }

    @After
    public void afterEachTest() {
        images.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() throws Exception {
        objectUnderTest.save(images.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() throws Exception {
        objectUnderTest.save(images);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test(expected = Exception.class)
    public void saveWithSameURL() throws Exception {
        objectUnderTest.save(images.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.save(new Image(myWeirdURL));
    }

    @Test(expected = Exception.class)
    public void saveCollectionWithSameURL() throws Exception {
        images.add(new Image(myWeirdURL));

        objectUnderTest.save(images);
    }

    @Test
    public void findOne() throws Exception {
        objectUnderTest.save(images.getFirst());

        assertNotNull(objectUnderTest.findOne(images.getFirst()));
    }

    @Test
    public void findOne1() throws Exception {
        objectUnderTest.save(images.getFirst());

        assertNotNull(objectUnderTest.findOne(images.getFirst().getId()));
    }

    @Test
    public void findAll() throws Exception {
        objectUnderTest.save(images);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);
    }

    @Test
    public void delete() throws Exception {
        objectUnderTest.save(images.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(images.getFirst());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        images.clear();
    }

    @Test
    public void delete1() throws Exception {
        objectUnderTest.save(images.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(images.getFirst().getId());

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        images.clear();
    }

    @Test
    public void delete2() throws Exception {
        objectUnderTest.save(images);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(3, counter);

        objectUnderTest.delete(images);

        counter = objectUnderTest.findAll().stream().count();

        assertEquals(0, counter);
        images.clear();
    }
}