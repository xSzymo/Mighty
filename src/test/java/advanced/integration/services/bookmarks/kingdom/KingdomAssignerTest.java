package advanced.integration.services.bookmarks.kingdom;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.kingdom.KingdomAssigner;
import game.mightywarriors.web.json.objects.bookmarks.KingdomInformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class KingdomAssignerTest extends AuthorizationConfiguration {
    @Autowired
    private KingdomAssigner objectUnderTest;
    @Autowired
    private UserService userService;
    private User user;
    private KingdomInformer informer;

    @Before
    public void setUp() throws Exception {
        user = new User("test", "test", "test");
        user.setAccountEnabled(true);
        userService.save(user);
        authorize(user.getLogin());

        informer = new KingdomInformer();
    }

    @After
    public void cleanUp() {
        userService.delete(user);
    }

    @Test
    public void setKingdom() throws Exception {
        informer.knight = true;

        objectUnderTest.setKingdom(token, informer);

        User user = userService.find(this.user);
        assertEquals("knight", user.getKingdom().getKingdom());
        assertEquals(true, user.isAccountNonLocked());
    }

    @Test
    public void setKingdom_1() throws Exception {
        informer.knight = false;

        objectUnderTest.setKingdom(token, informer);

        User user = userService.find(this.user);
        assertEquals("mercenary", user.getKingdom().getKingdom());
        assertEquals(true, user.isAccountNonLocked());
    }
}