package integration.services.background.tasks;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.background.tasks.ArenaPointsRefresher;
import integration.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ArenaPointsRefresherTest extends IntegrationTestsConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private ArenaPointsRefresher arenaPointsRefresher;

    @Before
    public void setUp() {
        for(int i = 0; i < 10; i++) {
            userService.save(new User("" + i, "", ""));
        }
    }

    @Test
    public void refreshMissionPointsForAllMission() {
        Set<User> users = userService.findAll();
        users.forEach(x -> x.setArenaPoints(2));
        userService.save(users);

        arenaPointsRefresher.refreshArenaPointsForAllUsers();

        userService.findAll().forEach(x -> assertEquals(SystemVariablesManager.ARENA_POINTS, x.getArenaPoints()));
    }
}