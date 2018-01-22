package integration.web.rest.api;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.web.rest.api.UserApiController;
import integration.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static junit.framework.TestCase.assertEquals;


public class UserApiControllerTest extends IntegrationTestsConfig {
    @Autowired
    private UserApiController userApiController;
    @Autowired
    private UserService userService;

    private String login = "UserApiControllerTest" + System.currentTimeMillis();
    private String login1 = "UserApiControllerTest1" + System.currentTimeMillis();
    private long id;

    @Before
    public void setUp() {
        User user = new User(login, "", "");
        userService.save(user);
        id = user.getId();
        userService.save(new User(login1, "", ""));
    }

    @Test
    public void getUserWithValidStatistics_test() {
        User userWithValidStatistics = userApiController.getUserWithValidStatistics(String.valueOf(id));
        assertEquals(0.9, userWithValidStatistics.getChampions().iterator().next().getStatistic().getVitality());

        userWithValidStatistics = userApiController.getUser(String.valueOf(id));
        assertEquals(3.0, userWithValidStatistics.getChampions().iterator().next().getStatistic().getVitality());
    }

    @Test
    public void getUsersWithValidStatistics_test() {
        Set<User> usersWithValidStatistics = userApiController.getUsersWithValidStatistics();
        assertEquals(0.9, usersWithValidStatistics.stream().filter(x -> x.getLogin().equals(login)).findFirst().get().getChampions().iterator().next().getStatistic().getVitality());
        assertEquals(0.9, usersWithValidStatistics.stream().filter(x -> x.getLogin().equals(login1)).findFirst().get().getChampions().iterator().next().getStatistic().getVitality());

        usersWithValidStatistics = userApiController.getUsers();
        assertEquals(3.0, usersWithValidStatistics.stream().filter(x -> x.getLogin().equals(login)).findFirst().get().getChampions().iterator().next().getStatistic().getVitality());
        assertEquals(3.0, usersWithValidStatistics.stream().filter(x -> x.getLogin().equals(login1)).findFirst().get().getChampions().iterator().next().getStatistic().getVitality());
    }
}