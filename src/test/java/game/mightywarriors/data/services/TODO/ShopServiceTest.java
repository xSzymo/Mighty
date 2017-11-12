package game.mightywarriors.data.services.TODO;

import game.mightywarriors.data.enums.WeaponType;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.ShopService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.User;
import org.junit.After;
import org.junit.Assert;
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
public class ShopServiceTest {

    @Autowired
    private ShopService objectUnderTest;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    private LinkedList<Shop> shops;
    private LinkedList<Item> items;
    private User user;

    @Before
    public void beforeEachTest() throws Exception {
        shops = new LinkedList<>();
        items = new LinkedList<>();
        addExampleDataToEquipments();
    }

    @After
    public void afterEachTest() throws Exception {
        for (Item item : items) {
            itemService.delete(item);
        }
        shops.forEach(objectUnderTest::delete);
        if (user != null)
            userService.delete(user);
    }

    @Test
    public void save() throws Exception {
        objectUnderTest.save(shops.getFirst());

        long counter = objectUnderTest.findAll().size();

        checkSavedItemsAreNotNull(shops.getFirst());
        assertEquals(1, counter);
    }

    @Test
    public void saveCollection() throws Exception {
        objectUnderTest.save(shops);
        long counter = objectUnderTest.findAll().stream().count();

        shops.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(4, counter);
    }

    @Test
    public void findOne() throws Exception {
        objectUnderTest.save(shops.getFirst());

        checkSavedItemsAreNotNull(shops.getFirst());
        assertNotNull(objectUnderTest.findOne(shops.getFirst()));
    }

    @Test
    public void findOne1() throws Exception {
        objectUnderTest.save(shops.getFirst());

        checkSavedItemsAreNotNull(shops.getFirst());
        assertNotNull(objectUnderTest.findOne(shops.getFirst().getId()));
    }

    @Test
    public void findAll() throws Exception {
        objectUnderTest.save(shops);

        long counter = objectUnderTest.findAll().stream().count();

        shops.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(4, counter);
    }

    @Test
    public void delete() throws Exception {
        objectUnderTest.save(shops.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(shops.getFirst());

        counter = objectUnderTest.findAll().stream().count();

        checkSavedItemsAreNotNull(shops.getFirst());
        assertEquals(0, counter);
    }

    @Test
    public void delete1() throws Exception {
        objectUnderTest.save(shops.getFirst());

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(1, counter);

        objectUnderTest.delete(shops.getFirst().getId());

        counter = objectUnderTest.findAll().stream().count();

        checkSavedItemsAreNotNull(shops.getFirst());
        assertEquals(0, counter);
    }

    @Test
    public void delete2() throws Exception {
        objectUnderTest.save(shops);

        long counter = objectUnderTest.findAll().stream().count();

        assertEquals(4, counter);

        objectUnderTest.delete(shops);

        counter = objectUnderTest.findAll().stream().count();

        shops.forEach(this::checkSavedItemsAreNotNull);
        assertEquals(0, counter);
    }


    @Test
    public void deleteFromUser() {
        user = new User();
        user.setShop(shops.getFirst());

        userService.save(user);

        assertNotNull(userService.findOne(user));
        assertNotNull(objectUnderTest.findOne(shops.getFirst()));

        objectUnderTest.delete(shops.getFirst());

        assertNotNull(userService.findOne(user.getId()));
        assertNull(userService.findOne(user.getId()).getShop());
        assertNull(objectUnderTest.findOne(shops.getFirst()));
    }

    private void checkSavedItemsAreNotNull(Shop shop) {
        shop.getItems().forEach(Assert::assertNotNull);
    }

    private void addExampleDataToEquipments() throws Exception {
        Statistic statistic;
        Shop shop;
        LinkedList<Item> myItems = new LinkedList<>();

        for (int a = 0, i = 3; i < 7; i++) {
            myItems.clear();
            shop = new Shop();
            statistic = new Statistic(i * i, i * i, i * i, i * i, i * i, i * i);

            myItems.add(new Item("name" + i, WeaponType.WEAPON, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.ARMOR, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.BOOTS, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.BRACELET, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.GLOVES, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.HELMET, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.LEGS, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.NECKLACE, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.OFFHAND, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.RING, statistic, i));

            items.addAll(myItems);
            shop.setItems(myItems);
            shops.add(shop);
        }
    }
}
