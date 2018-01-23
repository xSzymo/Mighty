package game.mightywarriors.services.bookmarks.arena;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.RankingService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Ranking;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.BusyChampionException;
import game.mightywarriors.other.exceptions.IllegalFightException;
import game.mightywarriors.other.exceptions.NotProperlyChampionsException;
import game.mightywarriors.other.exceptions.UsedPointsException;
import game.mightywarriors.services.bookmarks.utilities.Helper;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.tavern.Informer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class ArenaManager {
    @Autowired
    private UserService userService;
    @Autowired
    private FightCoordinator fightCoordinator;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private RankingService rankingService;
    @Autowired
    private Helper helper;

    private int ONE_SECOND = 1000;
    private int ONE_MINUTE = 60 * ONE_SECOND;

    @Transactional
    public FightResult fightUser(String authorization, Informer userFightInformer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        User opponent;

        if (userFightInformer.opponentId != 0)
            opponent = userService.find(userFightInformer.opponentId);
        else if (userFightInformer.opponentName != null)
            opponent = userService.find(userFightInformer.opponentName);
        else
            throw new NotProperlyChampionsException("Wrong champions id");

        Set<Champion> champions = user.getChampions();
        check(user, opponent, champions);

        FightResult fight = fightCoordinator.fight(user, opponent, helper.getChampionsId(champions));
        getThingsDoneAfterFight(user, opponent, fight, champions);

        return fight;
    }

    private void getThingsDoneAfterFight(User user, User opponent, FightResult fight, Set<Champion> champions) {
        if (fight.getWinner().getLogin().equals(user.getLogin())) {
            long HigherRank = rankingService.find(opponent.getLogin()).getRanking();
            long lowerRank = rankingService.find(user.getLogin()).getRanking();
            rankingService.delete(user.getLogin());

            LinkedList<Ranking> rankings = new LinkedList<>(rankingService.findAllBetween(lowerRank, HigherRank));
            for (int i = rankings.size() - 1; i >= 0; i--)
                rankingService.save(rankings.get(i).incrementRanking());

            Ranking userRanking = new Ranking(user.getLogin());
            rankingService.save(userRanking);

            userRanking.setRanking(HigherRank);
            rankingService.save(userRanking);
        }

        Timestamp blockDate = new Timestamp(System.currentTimeMillis() + (SystemVariablesManager.HOW_MANY_MINUTES_BLOCK_ARENA_FIGHT * ONE_MINUTE));
        user.getChampions().stream().filter(x -> champions.contains(x)).forEach(x -> x.setBlockTime(blockDate));

        user.setArenaPoints(user.getArenaPoints() - 1);
        userService.save(user);
    }

    private void check(User user, User opponent, Set<Champion> champions) throws Exception {
        if (rankingService.find(user.getLogin()).getRanking() < rankingService.find(opponent.getLogin()).getRanking())
            throw new IllegalFightException("User can't fight with user below his ranking");
        if (user.getArenaPoints() < 1)
            throw new UsedPointsException("Arena points already used");
        if (helper.isChampionOnMission(new HashSet<>(champions), true))
            throw new BusyChampionException("Someone is already busy");
        if (opponent == null)
            throw new NotProperlyChampionsException("Wrong champions id");
    }
}
