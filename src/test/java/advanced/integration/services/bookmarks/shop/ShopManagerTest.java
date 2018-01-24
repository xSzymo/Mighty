package advanced.integration.services.bookmarks.shop;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.tavern.ShopInformer;
import game.mightywarriors.web.rest.bookmarks.shop.ShopController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ShopManagerTest extends AuthorizationConfiguration {
    @Autowired
    private ShopController objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;

    private User user;
    private Item item;
    private ShopInformer informer;

    @Before
    public void setUp() throws Exception {
        informer = new ShopInformer();
        user = usersRetriever.retrieveUser(token);
        user.addGold(new BigDecimal("1000"));
        item = user.getShop().getItems().iterator().next();

        informer.itemId = item.getId();
        userService.save(user);
    }

    @Test
    public void buyItem() throws Exception {
        BigDecimal itemGold = item.getGold();
        BigDecimal userGold = user.getGold();

        objectUnderTest.buyItem(token, informer);

        User user = userService.find(this.user.getLogin());
        assertEquals(userGold.subtract(itemGold), user.getGold());
        assertFalse(user.getShop().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
    }
}