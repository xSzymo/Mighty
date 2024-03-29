package integration.web.rest.api;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.web.rest.mighty.data.user.ApiUser;
import integration.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static junit.framework.TestCase.assertEquals;


public class ApiUserTest extends IntegrationTestsConfig {
    @Autowired
    private ApiUser apiUser;
    @Autowired
    private UserService userService;

    private String login = "UserApiControllerTest" + System.currentTimeMillis();
    private String login1 = "UserApiControllerTest1" + System.currentTimeMillis();
    private long id;

    @Before
    public void setUp() {
        User user = new User(login, "", "").addChampion(new Champion());
        userService.save(user);
        id = user.getId();
        userService.save(new User(login1, "", "").addChampion(new Champion()));
    }

    @Test
    public void getUserWithValidStatistics_test() {
        User userWithValidStatistics = apiUser.getUserWithValidStatistics(String.valueOf(id));
        assertEquals(1, userWithValidStatistics.getChampions().iterator().next().getStatistic().getVitality());

        userWithValidStatistics = apiUser.getUser(String.valueOf(id));
        assertEquals(3, userWithValidStatistics.getChampions().iterator().next().getStatistic().getVitality());
    }

    @Test
    public void getUsersWithValidStatistics_test() {
        Set<User> usersWithValidStatistics = apiUser.getUsersWithValidStatistics();
        assertEquals(1, usersWithValidStatistics.stream().filter(x -> x.getLogin().equals(login)).findFirst().get().getChampions().iterator().next().getStatistic().getVitality());
        assertEquals(1, usersWithValidStatistics.stream().filter(x -> x.getLogin().equals(login1)).findFirst().get().getChampions().iterator().next().getStatistic().getVitality());

        usersWithValidStatistics = apiUser.getUsers();
        assertEquals(3, usersWithValidStatistics.stream().filter(x -> x.getLogin().equals(login)).findFirst().get().getChampions().iterator().next().getStatistic().getVitality());
        assertEquals(3, usersWithValidStatistics.stream().filter(x -> x.getLogin().equals(login1)).findFirst().get().getChampions().iterator().next().getStatistic().getVitality());
    }
}
