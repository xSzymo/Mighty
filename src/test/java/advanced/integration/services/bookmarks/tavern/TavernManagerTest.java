package advanced.integration.services.bookmarks.tavern;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.tavern.TavernManager;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.tavern.Informer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.*;

public class TavernManagerTest extends AuthorizationConfiguration {
    @Autowired
    private TavernManager objectUnderTest;
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private UserService userService;
    @Autowired
    private MissionFightService missionFightService;
    @Autowired
    private MissionService missionService;

    private int howManyRolls = 3;
    private User user;
    private Informer informer;
    private int missionPoints;
    private long userGold;
    private long goldFromMission;
    private long champExperience;
    private long champExperience1;
    private long champLevel;
    private long champLevel1;

    @Before
    public void setUp() throws Exception {
        user = retriever.retrieveUser(token);

        informer = new Informer();
        informer.championId = new long[]{user.getChampions().get(0).getId(), user.getChampions().get(1).getId()};
    }

    @Test
    public void run() throws Exception {
        for (int i = 0; i < howManyRolls; i++) {
            informer.missionId = user.getMissions().get(0).getId();
            tavern_process();
        }
    }

    private void tavern_process() throws Exception {
        setUpVariables();

        MissionFight missionFight = objectUnderTest.sendChampionOnMission(token, informer);
        checkAfterSendChampion(missionFight);

        FightResult fightResult = objectUnderTest.performFight(token, informer);
        checkAfterPerformFight(fightResult);
    }

    private void checkAfterSendChampion(MissionFight missionFight) throws InterruptedException {
        informer.id = missionFight.getId();
        user = userService.findOne(user);
        missionFight = missionFightService.findOne(missionFight);

        long blockTime = new Timestamp(System.currentTimeMillis() + SystemVariablesManager.HOW_MANY_MINUTES_BLOCK_ARENA_FIGHT * 60 * 1000).getTime() / 60 / 1000;
        assertEquals(2, missionFight.getChampion().size());
        assertTrue(user.getChampions().get(0).getId().equals(missionFight.getChampion().get(0).getId()) || user.getChampions().get(0).getId().equals(missionFight.getChampion().get(1).getId()));
        assertTrue(user.getChampions().get(1).getId().equals(missionFight.getChampion().get(0).getId()) || user.getChampions().get(1).getId().equals(missionFight.getChampion().get(1).getId()));
        assertTrue((blockTime - user.getChampions().get(0).getBlockDate().getTime() / 60 / 1000) >= -1 && (blockTime - user.getChampions().get(0).getBlockDate().getTime() / 60 / 1000) <= 1);
        assertTrue((blockTime - user.getChampions().get(1).getBlockDate().getTime() / 60 / 1000) >= -1 && (blockTime - user.getChampions().get(1).getBlockDate().getTime() / 60 / 1000) <= 1);
        assertTrue((blockTime - new Timestamp(System.currentTimeMillis() + (missionService.findOne(informer.missionId).getTimeDuration() * 1000)).getTime() / 60 / 1000) >= -1 ||
                (blockTime - new Timestamp(System.currentTimeMillis() + (missionService.findOne(informer.missionId).getTimeDuration() * 1000)).getTime() / 60 / 1000) <= 1);

        sleep(missionFight);
    }

    private void checkAfterPerformFight(FightResult fightResult) {
        user = userService.findOne(user);

        assertEquals(missionPoints - 1, user.getMissionPoints());
        assertEquals(userGold + goldFromMission, user.getGold().intValue());
        assertEquals(user.getLogin(), fightResult.getWinner().getLogin());
        assertEquals(new BigDecimal(goldFromMission).intValue(), fightResult.getGold().intValue());
        assertEquals(missionService.findOne(informer.missionId).getExperience(), fightResult.getExperience());
        assertEquals(champLevel, user.getChampions().get(0).getLevel());
        assertEquals(champLevel1, user.getChampions().get(1).getLevel());
        assertEquals(champExperience + missionService.findOne(informer.missionId).getExperience() / 2, user.getChampions().get(0).getExperience());
        assertEquals(champExperience1 + missionService.findOne(informer.missionId).getExperience() / 2, user.getChampions().get(1).getExperience());
        assertNull(user.getChampions().get(0).getBlockDate());
        assertNull(user.getChampions().get(1).getBlockDate());

        for (Mission mission : user.getMissions())
            if (mission.getId() == informer.missionId)
                fail("This mission should not be in user's collection anymore");
    }

    private void setUpVariables() {
        goldFromMission = user.getMissions().get(0).getGold().longValue();
        userGold = user.getGold().intValue();
        missionPoints = user.getMissionPoints();

        champExperience = user.getChampions().get(0).getExperience();
        champExperience1 = user.getChampions().get(0).getExperience();
        champLevel = user.getChampions().get(0).getLevel();
        champLevel1 = user.getChampions().get(0).getLevel();
    }

    private void sleep(MissionFight missionFight) throws InterruptedException {

        long timeToSleep = missionFight.getBlockDate().getTime() - new Timestamp(System.currentTimeMillis()).getTime() + 1000;
        if (timeToSleep > 0)
            Thread.sleep(timeToSleep);
    }
}
