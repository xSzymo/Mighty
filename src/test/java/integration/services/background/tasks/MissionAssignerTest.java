package integration.services.background.tasks;

import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.User;
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

    @Before
    @Transactional
    public void setUp() {
        int level = 10;
        for (int i = 3; i < 100; i++) {
            if (i == level)
                level += 10;

            User user = new User("a" + i, "", "");
            userService.save(user);
            user.getChampions().get(0).setLevel(level / 10);
            userService.save(user);

            missionService.save(new Mission(1, "description : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
            missionService.save(new Mission(1, "description1 : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
            missionService.save(new Mission(1, "description2 : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
            missionService.save(new Mission(1, "description3 : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
            missionService.save(new Mission(1, "description4 : " + i, new BigDecimal("1" + i), new Monster(new Statistic(1, 1, 1, 1, 1, 1)).setLevel(level / 10)));
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
        User user = userService.findByLogin("a5");
        user.getMissions().clear();
        userService.save(user);
        assertEquals(0, userService.findOne(user).getMissions().size());

        objectUnderTest.assignNewMissionForUsers(user.getId());

        assertEquals(3, userService.findOne(user.getId()).getMissions().size());
    }
}
