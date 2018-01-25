package advanced.integration.services.bookmarks.shop;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NotEnoughGoldException;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.tavern.ShopInformer;
import game.mightywarriors.web.rest.bookmarks.shop.ShopController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


public class ShopManagerTest extends AuthorizationConfiguration {
    @Autowired
    private ShopController objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UsersRetriever usersRetriever;

    private User user;
    private Item item;
    private Item item1;
    private ShopInformer informer;

    @Before
    public void setUp() throws Exception {
        informer = new ShopInformer();
        user = usersRetriever.retrieveUser(token);
        item = user.getShop().getItems().iterator().next();
        item.setGold(new BigDecimal("10"));

        informer.itemId = item.getId();
        userService.save(user);
        item1 = new Item();
        item1.setGold(new BigDecimal("10"));
        itemService.save(item1);
        itemService.save(item);
    }

    @Test
    public void buyItem() throws Exception {
        user.addGold(new BigDecimal("1000"));
        BigDecimal itemGold = item.getGold();
        BigDecimal userGold = user.getGold();
        userService.save(user);

        objectUnderTest.buyItem(token, informer);

        User user = userService.find(this.user.getLogin());
        assertEquals(userGold.subtract(itemGold), user.getGold());
        assertFalse(user.getShop().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
    }

    @Test(expected = NotEnoughGoldException.class)
    public void buyItem_withNotEnoughMoney() throws Exception {
        user.setGold(new BigDecimal("0"));
        userService.save(user);

        objectUnderTest.buyItem(token, informer);
    }

    @Test(expected = Exception.class)
    public void buyItem_withWrongItem() throws Exception {
        informer.itemId = item1.getId();

        objectUnderTest.buyItem(token, informer);
    }

    @Test
    public void sellItem() throws Exception {
        user.setGold(new BigDecimal("0"));
        user.getInventory().addItem(item1);
        userService.save(user);
        informer.itemId = item1.getId();

        objectUnderTest.sellItem(token, informer);

        User user = userService.find(this.user.getLogin());
        assertEquals(new BigDecimal(item.getGold().doubleValue() / 2).doubleValue(), user.getGold().doubleValue());
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
    }
}