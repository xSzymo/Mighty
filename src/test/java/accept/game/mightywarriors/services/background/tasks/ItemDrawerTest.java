package accept.game.mightywarriors.services.background.tasks;

import accept.game.mightywarriors.config.AcceptTestConfig;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.background.tasks.ItemDrawer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

public class ItemDrawerTest extends AcceptTestConfig {
    @Autowired
    private ItemDrawer objectUnderTest;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        userService.save(new User("1", "", ""));
        userService.save(new User("2", "", ""));
        userService.save(new User("3", "", ""));
    }

    @Test
    @Transactional
    public void drawItemsForUser() {
        objectUnderTest.drawItemsForUser();

        userService.findAll().forEach(x -> assertEquals(10, x.getShop().getItems().size()));
    }
}
