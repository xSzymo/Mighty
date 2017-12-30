package advanced.integration.services.bookmarks.arena;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.RankingService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Ranking;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.IllegalFightException;
import game.mightywarriors.other.exceptions.NotProperlyChampionsException;
import game.mightywarriors.services.bookmarks.arena.ArenaManager;
import game.mightywarriors.services.bookmarks.league.PointsForDivisionCounter;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.tavern.Informer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArenaManagerTest extends AuthorizationConfiguration {
    @Autowired
    private ArenaManager objectUnderTest;
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private UserService userService;
    @Autowired
    private RankingService rankingService;
    @Autowired
    private PointsForDivisionCounter pointsForDivisionCounter;

    private User user;
    private Ranking userRanking;

    private User opponentWithHigherRanking;
    private User opponentWithLowerRanking;
    private Informer informer;

    private long abstractComicalNumber = 9998991;

    @Before
    public void setUp() throws Exception {
        authorize(rankingService.findOne(3).getNickname());

        user = retriever.retrieveUser(token);
        userRanking = rankingService.findOne(user.getLogin());

        List<Ranking> allAboveRanking = rankingService.findAllAboveRanking(userRanking.getRanking());
        List<Ranking> allBelowRanking = rankingService.findAllBelowRanking(userRanking.getRanking());
        opponentWithLowerRanking = userService.findByLogin(allAboveRanking.get(0).getNickname());
        opponentWithHigherRanking = getOpponentWithHigherRanking(allBelowRanking);
        if (opponentWithHigherRanking == null)
            throw new Exception("Opponent was not chosen");

        informer = new Informer();
        informer.championId = new long[]{user.getChampions().get(0).getId(), user.getChampions().get(1).getId(), user.getChampions().get(2).getId()};
    }

    @After
    public void cleanUp() {
        user.getChampions().get(0).setBlockTime(null);
        user.getChampions().get(1).setBlockTime(null);
        user.getChampions().get(2).setBlockTime(null);

        userService.save(user);
    }

    @Test
    public void fightUser_opponentWithHigherRanking_win() throws Exception {
        informer.opponentName = opponentWithHigherRanking.getLogin();

        int arenaPoints = user.getArenaPoints();
        long oldOpponentRanking = rankingService.findOne(informer.opponentName).getRanking();

        objectUnderTest.fightUser(token, informer);

        user = userService.findOne(user.getId());
        assertEquals(arenaPoints - 1, user.getArenaPoints());
        assertEquals(oldOpponentRanking, rankingService.findOne(user.getLogin()).getRanking());
        assertEquals(oldOpponentRanking + 1, rankingService.findOne(informer.opponentName).getRanking());
        long blockTime = new Timestamp(System.currentTimeMillis() + SystemVariablesManager.HOW_MANY_MINUTES_BLOCK_ARENA_FIGHT * 60 * 1000).getTime() / 60 / 1000;
        assertEquals(blockTime, user.getChampions().stream().filter(x -> x.getId().equals(informer.championId[0])).findFirst().get().getBlockDate().getTime() / 60 / 1000);
        assertEquals(blockTime, user.getChampions().stream().filter(x -> x.getId().equals(informer.championId[1])).findFirst().get().getBlockDate().getTime() / 60 / 1000);
        assertEquals(blockTime, user.getChampions().stream().filter(x -> x.getId().equals(informer.championId[2])).findFirst().get().getBlockDate().getTime() / 60 / 1000);
    }

    @Test(expected = IllegalFightException.class)
    public void fightUser_opponentWithLowerRanking() throws Exception {
        informer.opponentName = opponentWithLowerRanking.getLogin();

        objectUnderTest.fightUser(token, informer);
    }

    @Test(expected = NotProperlyChampionsException.class)
    public void fightUser_wrong_champions_id() throws Exception {
        informer.championId = new long[]{abstractComicalNumber};

        objectUnderTest.fightUser(token, informer);
    }

    public User getOpponentWithHigherRanking(List<Ranking> allBelowRanking) {
        return userService.findByLogin(allBelowRanking.stream()
                .filter(x -> pointsForDivisionCounter.getPointsOfFighterPower(user) > pointsForDivisionCounter.getPointsOfFighterPower(userService.findByLogin(x.getNickname())))
                .findFirst().get().getNickname());
    }
}
