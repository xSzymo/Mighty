package advanced.integration.services.bookmarks.tavern;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.tavern.ChampionTavern;
import game.mightywarriors.web.json.objects.bookmarks.ChampionBuyInformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ChampionTavernTest extends AuthorizationConfiguration {
    @Autowired
    private ChampionTavern objectUnderTest;
    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("test", "test", "test");
        user.setAccountEnabled(true);
        user.setAccountNonLocked(true);
        user.addGold(new BigDecimal(10000));
        userService.save(user);
        authorize(user.getLogin());
    }

    @After
    public void cleanUp() {
        userService.delete(user);
    }

    @Test
    public void runFirst() throws Exception {
        check(10, 0);

        run(SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION);
        check(300, 5);
    }

    @Test
    public void runFirst1() throws Exception {
        check(10, 0);

        run(SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION);
        check(300, 5);
        user.getChampions().iterator().next().setLevel(100);
        userService.save(user);

        run(SystemVariablesManager.MINIMUM_GOLD_FOR_SECOND_CHAMPION);
        check(1000, 10);
    }

    @Test
    public void runFirst2() throws Exception {
        check(10, 0);

        run(SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION);
        check(300, 5);
        user.getChampions().iterator().next().setLevel(100);
        userService.save(user);

        run(SystemVariablesManager.MINIMUM_GOLD_FOR_SECOND_CHAMPION);
        check(1000, 10);
        user.getChampions().iterator().next().setLevel(100);
        userService.save(user);

        run(SystemVariablesManager.MINIMUM_GOLD_FOR_THIRD_CHAMPION);
    }

    @Test
    public void runFirst3() throws Exception {
        check(10, 0);

        run(SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION);
        check(300, 5);
        user.getChampions().iterator().next().setLevel(100);
        userService.save(user);

        run(SystemVariablesManager.MINIMUM_GOLD_FOR_SECOND_CHAMPION);
        check(1000, 10);
        user.getChampions().iterator().next().setLevel(100);
        userService.save(user);

        run(SystemVariablesManager.MINIMUM_GOLD_FOR_THIRD_CHAMPION);
        check(1, 1000);
    }

    private void check(long gold, long level) throws Exception {
        ChampionBuyInformer informer = objectUnderTest.getInformationForBuyChampion(token);
        assertEquals(gold, informer.gold);
        assertEquals(level, informer.level);
    }

    public void run(long goldForChampion) throws Exception {
        long champions = user.getChampions().size();
        long gold = user.getGold().longValue();

        objectUnderTest.buyChampion(token);

        user = userService.find(user.getLogin());
        assertEquals(champions + 1, user.getChampions().size());
        assertEquals(gold - goldForChampion, user.getGold().longValue());
    }
}
