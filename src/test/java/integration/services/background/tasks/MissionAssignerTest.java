package integration.services.background.tasks;

import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.services.background.tasks.MissionAssigner;
import integration.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class MissionAssignerTest extends IntegrationTestsConfig {
    @Autowired
    private MissionAssigner objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private MissionService missionService;

    private static boolean run = true;

    @Before
    @Transactional
    public void setUp() throws InterruptedException {
        if (run) {
            run = false;

            int level = 10;
            for (int i = 3; i < 100; i++) {
                if (i == level)
                    level += 10;

                User user = new User("a" + i, "", "").addChampion(new Champion());
                userService.save(user);
                user.getChampions().iterator().next().setLevel(level / 10);
                userService.save(user);

                missionService.save(new Mission(1, "description : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
                missionService.save(new Mission(1, "description1 : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
                missionService.save(new Mission(1, "description2 : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
                missionService.save(new Mission(1, "description3 : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
                missionService.save(new Mission(1, "description4 : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
            }
        }
    }

    @Test
    @Transactional
    public void drawItemsForUser() {
        objectUnderTest.assignNewMissionForUsers();

        userService.findAll().forEach(x -> assertEquals(3, x.getMissions().size()));
    }

    @Test
    @Transactional
    public void assignNewMissionForUsers_1() {
        User user = userService.find("a5");
        user.getMissions().clear();
        userService.save(user);
        assertEquals(0, userService.find(user).getMissions().size());

        objectUnderTest.assignNewMissionForUsers(user.getId());

        assertEquals(3, userService.find(user.getId()).getMissions().size());
    }
}
