package advanced.integration.services.bookmarks.profile;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.InventoryItem;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.services.bookmarks.profile.ItemManager;
import game.mightywarriors.services.bookmarks.profile.ItemPlaceChanger;
import game.mightywarriors.services.security.UsersRetriever;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ItemPlaceChangerTest extends AuthorizationConfiguration {
    @Autowired
    private ItemPlaceChanger objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private ItemService itemService;

    private User user;
    private Item item;
    private Champion champion;
    private static boolean addData = true;

    @Before
    public void setUp() throws Exception {
        if (addData) {
            user = usersRetriever.retrieveUser(token);
            item = new Item(ItemType.OFFHAND);
            itemService.save(item);
            Item item1 = new Item(ItemType.BOOTS);
            itemService.save(item1);
            Item item2 = new Item(ItemType.HELMET);
            itemService.save(item2);
            Item item3 = new Item(ItemType.NECKLACE);
            itemService.save(item3);
            user.getInventory().addItem(item);
            user.getInventory().addItem(item1);
            user.getInventory().addItem(item2);
            user.getInventory().addItem(item3);
            userService.save(user);

            addData = false;
        }
    }

    @After
    public void cleanUp() {
        try {
            userService.delete("justForTest");
        } catch (Exception e) {
        }
    }

    @Test
    public void changeItemPlace_1() throws Exception {
        user = usersRetriever.retrieveUser(token);
        InventoryItem item = user.getInventory().getItems().stream().filter(x -> x.getPosition() == 0).findFirst().get();

        objectUnderTest.changePlace(token, 0, 6);

        user = userService.find(user);
        assertEquals(item.getId(), user.getInventory().getItems().stream().filter(x -> x.getPosition() == 6).findFirst().get().getId());
    }

    @Test
    public void changeItemPlace_2() throws Exception {
        user = usersRetriever.retrieveUser(token);
        InventoryItem item = user.getInventory().getItems().stream().filter(x -> x.getPosition() == 1).findFirst().get();
        InventoryItem item1 = user.getInventory().getItems().stream().filter(x -> x.getPosition() == 3).findFirst().get();

        objectUnderTest.changePlace(token, 1, 3);

        user = userService.find(user);
        assertEquals(item.getId(), user.getInventory().getItems().stream().filter(x -> x.getPosition() == 3).findFirst().get().getId());
        assertEquals(item1.getId(), user.getInventory().getItems().stream().filter(x -> x.getPosition() == 1).findFirst().get().getId());
    }

    @Test
    public void changeItemPlace_3() throws Exception {
        user = usersRetriever.retrieveUser(token);
        InventoryItem item = user.getInventory().getItems().stream().filter(x -> x.getPosition() == 2).findFirst().get();

        objectUnderTest.changePlace(token, 2, 2);

        user = userService.find(user);
        assertEquals(item.getId(), user.getInventory().getItems().stream().filter(x -> x.getPosition() == 2).findFirst().get().getId());
    }

    @Test
    public void changeItemPlace_4() throws Exception {
        user = usersRetriever.retrieveUser(token);
        InventoryItem item = user.getInventory().getItems().stream().filter(x -> x.getPosition() == 2).findFirst().get();
        InventoryItem item1 = user.getInventory().getItems().stream().filter(x -> x.getPosition() == 3).findFirst().get();

        objectUnderTest.changePlace(token, 2, 3);

        user = userService.find(user);
        assertEquals(item.getId(), user.getInventory().getItems().stream().filter(x -> x.getPosition() == 3).findFirst().get().getId());
        assertEquals(item1.getId(), user.getInventory().getItems().stream().filter(x -> x.getPosition() == 2).findFirst().get().getId());
    }

    @Test(expected = Exception.class)
    public void changeItemPlace_with_wrong_item() throws Exception {
        user = usersRetriever.retrieveUser(token);

        objectUnderTest.changePlace(token, 21, 1);
    }
}