package advanced.integration.services.bookmarks.arena;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.RankingService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Ranking;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.IllegalFightException;
import game.mightywarriors.other.exceptions.NotProperlyChampionsException;
import game.mightywarriors.services.bookmarks.arena.ArenaManager;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ArenaInformer;
import game.mightywarriors.web.json.objects.bookmarks.Informer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;

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
    private ChampionService championService;

    private User user;
    private Ranking userRanking;

    private Champion champion;
    private Statistic statistic;
    private User opponentWithHigherRanking;
    private User opponentWithLowerRanking;
    private Informer informer;

    private long abstractComicalNumber = 9998991;


    @Before
    public void setUp() throws Exception {
        authorize(rankingService.find(3).getNickname());

        user = retriever.retrieveUser(token);
        champion = user.getChampions().stream().findFirst().get();
        statistic = champion.getStatistic();
        championService.save(champion.setStatistic(new Statistic(abstractComicalNumber, abstractComicalNumber, abstractComicalNumber, abstractComicalNumber, abstractComicalNumber, abstractComicalNumber)));
        userRanking = rankingService.find(user.getLogin());

        Set<Ranking> allAboveRanking = rankingService.findAllAboveRanking(userRanking.getRanking());
        Set<Ranking> allBelowRanking = rankingService.findAllBelowRanking(userRanking.getRanking());
        opponentWithLowerRanking = userService.find(allAboveRanking.iterator().next().getNickname());
        opponentWithHigherRanking = getOpponentWithHigherRanking(allBelowRanking);

        if (opponentWithHigherRanking == null)
            throw new Exception("Opponent was not chosen");

        informer = new Informer();
        Iterator<Champion> champions = user.getChampions().iterator();
        informer.championId = new long[]{champions.next().getId(), champions.next().getId(), champions.next().getId()};
    }

    @After
    public void cleanUp() {
        user.getChampions().forEach(x -> x.setBlockUntil(null));
        user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().setStatistic(statistic);
        userService.save(user);
    }

    @Test
    public void fightUser_opponentWithHigherRanking_win() throws Exception {
        informer.opponentName = opponentWithHigherRanking.getLogin();
        int arenaPoints = user.getArenaPoints();
        long oldOpponentRanking = rankingService.find(informer.opponentName).getRanking();

        FightResult fightResult = objectUnderTest.fightUser(token, new ArenaInformer(informer.opponentId, informer.opponentName));

        user = userService.find(user.getId());
        assertEquals(user.getLogin(), fightResult.getWinner().getLogin());
        assertEquals(arenaPoints - 1, user.getArenaPoints());
        assertEquals(oldOpponentRanking, rankingService.find(user.getLogin()).getRanking());
        assertEquals(oldOpponentRanking + 1, rankingService.find(informer.opponentName).getRanking());
        long blockTime = new Timestamp(System.currentTimeMillis() + SystemVariablesManager.HOW_MANY_MINUTES_BLOCK_ARENA_FIGHT * 60 * 1000).getTime() / 60 / 1000;
        assertEquals(blockTime, user.getChampions().stream().filter(x -> x.getId().equals(informer.championId[0])).findFirst().get().getBlockUntil().getTime() / 60 / 1000);
        assertEquals(blockTime, user.getChampions().stream().filter(x -> x.getId().equals(informer.championId[1])).findFirst().get().getBlockUntil().getTime() / 60 / 1000);
        assertEquals(blockTime, user.getChampions().stream().filter(x -> x.getId().equals(informer.championId[2])).findFirst().get().getBlockUntil().getTime() / 60 / 1000);
    }

    @Test(expected = IllegalFightException.class)
    public void fightUser_opponentWithLowerRanking() throws Exception {
        informer.opponentName = opponentWithLowerRanking.getLogin();

        objectUnderTest.fightUser(token, new ArenaInformer(informer.opponentId, informer.opponentName));
    }

    @Test(expected = NotProperlyChampionsException.class)
    public void fightUser_wrong_champions_id() throws Exception {
        informer.championId = new long[]{abstractComicalNumber};

        objectUnderTest.fightUser(token, new ArenaInformer(informer.opponentId, informer.opponentName));
    }

    public User getOpponentWithHigherRanking(Set<Ranking> allBelowRanking) {
        return userService.find(allBelowRanking.stream()
                .findFirst().get().getNickname());
    }
}
