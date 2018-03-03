package advanced.integration.services.bookmarks.dungeon;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.DungeonFightService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.services.bookmarks.dungeon.DungeonManager;
import game.mightywarriors.web.json.objects.fights.FightResult;
import game.mightywarriors.web.rest.mighty.data.user.DungeonController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class DungeonManagerTest extends AuthorizationConfiguration {
    @Autowired
    private DungeonManager objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private ChampionService championService;
    @Autowired
    private DungeonController dungeonController;
    @Autowired
    private DungeonFightService dungeonFightService;

    private User user;
    private Floor floor;
    private int dungeonPoints;
    private long userGold;
    private long champExperience;
    private long champLevel;
    private Long championId;
    private int dungeonNumber;
    private static String userName = "DungeonTest";

    @Before
    public void setUp() throws Exception {
        user = new User(userName, userName, userName);
        user.setAccountEnabled(true);
        user.setChampions(new HashSet<>(Collections.singleton(new Champion(new Statistic(10, 10, 10, 10, 10, 10)).setLevel(10))));
        userService.save(user);

        authorize(user.getLogin());
    }

    @After
    public void cleanUp() {
        userName = userName + "1";
        userService.delete(user);
    }

    @Test
    public void tavern_process() throws Exception {//MINIMUM LEVEL DELETE
        setUpVariables();

        objectUnderTest.sendChampionsToDungeon(token);
        checkAfterSendChampion(dungeonFightService.findByUserId(user.getId()));

        FightResult fightResult = objectUnderTest.performFightDungeonFight(token);
        checkAfterPerformFight(fightResult, false);
    }

    @Test
    public void tavern_process_with_new_dungeon() throws Exception {
        user = userService.find(user);
        user.getDungeon().setCurrentFloor(1);
        userService.save(user);

        setUpVariables();

        objectUnderTest.sendChampionsToDungeon(token);
        checkAfterSendChampion(dungeonFightService.findByUserId(user.getId()));

        FightResult fightResult = objectUnderTest.performFightDungeonFight(token);
        checkAfterPerformFight(fightResult, true);
    }

    private void checkAfterSendChampion(DungeonFight dungeonFight) throws InterruptedException {
        user = userService.find(user);
        Champion champion = championService.find(championId);
        long floorBlockTime = new Timestamp(System.currentTimeMillis() + floor.getDuration() * 60 * 1000).getTime() / 60 / 1000;
        long dungeonFightBlockTime = dungeonFight.getBlockUntil().getTime() / 60 / 1000;

        assertTrue((floorBlockTime - champion.getBlockUntil().getTime() / 60 / 1000) >= -1 && (floorBlockTime - champion.getBlockUntil().getTime() / 60 / 1000) <= 1);
        assertTrue((dungeonFightBlockTime - champion.getBlockUntil().getTime() / 60 / 1000) >= -1 && (dungeonFightBlockTime - champion.getBlockUntil().getTime() / 60 / 1000) <= 1);

        sleep(dungeonFight);
    }

    private void checkAfterPerformFight(FightResult fightResult, boolean newDungeon) {
        user = userService.find(user);
        Champion champion = championService.find(championId);

        assertEquals(dungeonPoints - 1, user.getDungeonPoints());
        assertEquals(userGold + floor.getGold().intValue(), user.getGold().intValue());
        assertEquals(user.getLogin(), fightResult.getWinner().getLogin());
        assertEquals(floor.getGold().intValue(), fightResult.getGold().intValue());
        assertEquals(floor.getExperience(), fightResult.getExperience());
        assertEquals(champLevel, champion.getLevel());
        assertEquals(champExperience + floor.getExperience(), champion.getExperience());
        assertNull(champion.getBlockUntil());

        assertEquals(newDungeon ? dungeonNumber + 1 : dungeonNumber, user.getDungeon().getDungeon().getStage());
        assertNull(dungeonFightService.findByUserId(user.getId()));
    }

    private void setUpVariables() throws Exception {
        Champion champion = user.getChampions().stream().findFirst().get();
        floor = dungeonController.getCurrentFloor(token);
        dungeonNumber = user.getDungeon().getDungeon().getStage();

        userGold = user.getGold().intValue();
        dungeonPoints = user.getDungeonPoints();

        champExperience = champion.getExperience();
        champLevel = champion.getLevel();
        championId = champion.getId();
    }

    private void sleep(DungeonFight dungeonFight) throws InterruptedException {
        long timeToSleep = dungeonFight.getBlockUntil().getTime() - new Timestamp(System.currentTimeMillis()).getTime() + 1000;
        if (timeToSleep > 0)
            Thread.sleep(timeToSleep);
    }
}
