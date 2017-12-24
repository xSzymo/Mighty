package game.mightywarriors.services.background.tasks;

import game.mightywarriors.configuration.system.SystemFightsVariablesManager;
import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Division;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.comparators.UsersDivisionComparator;
import game.mightywarriors.other.enums.League;
import game.mightywarriors.services.bookmarks.league.PointsForDivisionCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;

@Service
public class DivisionAssigner {
    private static final double PERCENT = 0.01;
    private final PointsForDivisionCounter pointsForDivisionCounter = new PointsForDivisionCounter();

    @Autowired
    private DivisionService divisionService;
    @Autowired
    private UserService userService;

    @Transactional
    public void assignUsersDivisions() {
        LinkedList<User> users = userService.findAll();
        users.sort(new UsersDivisionComparator());

        Division challenger = divisionService.findByLeague(League.CHALLENGER);
        Division diamond = divisionService.findByLeague(League.DIAMOND);
        Division gold = divisionService.findByLeague(League.GOLD);
        Division silver = divisionService.findByLeague(League.SILVER);
        Division bronze = divisionService.findByLeague(League.BRONZE);
        Division wood = divisionService.findByLeague(League.WOOD);

        challenger.clearUsers();
        diamond.clearUsers();
        gold.clearUsers();
        silver.clearUsers();
        bronze.clearUsers();
        wood.clearUsers();


        for (User user : users) {
            long level = user.getUserChampiongHighestLevel();

            if (level < 30)
                continue;
            double points = pointsForDivisionCounter.getPointsOfFighterPower(user);


            if (challenger.getUsers().size() < SystemFightsVariablesManager.MAX_PLAYERS_IN_CHALLENGER)
                if (level >= SystemFightsVariablesManager.MIN_LEVEL_FOR_CHALLENGER && points > SystemFightsVariablesManager.MIN_POINTS_FOR_CHALLENGER) {
                    challenger.getUsers().add(user);
                    continue;
                }

            if (diamond.getUsers().size() < SystemFightsVariablesManager.MAX_PLAYERS_IN_DIAMOND)
                if (level >= SystemFightsVariablesManager.MIN_LEVEL_FOR_DIAMOND && points >= SystemFightsVariablesManager.MIN_POINTS_FOR_DIAMOND) {
                    diamond.getUsers().add(user);
                    continue;
                }

            if (gold.getUsers().size() < (long) users.size() * (SystemFightsVariablesManager.MAX_PERCENT_PLAYERS_IN_GOLD * PERCENT))
                if (level >= SystemFightsVariablesManager.MIN_LEVEL_FOR_GOLD && points > SystemFightsVariablesManager.MIN_POINTS_FOR_GOLD) {
                    gold.getUsers().add(user);
                    continue;
                }

            if (silver.getUsers().size() < (long) users.size() * (SystemFightsVariablesManager.MAX_PERCENT_PLAYERS_IN_SILVER * PERCENT))
                if (level >= SystemFightsVariablesManager.MIN_LEVEL_FOR_SILVER && points > SystemFightsVariablesManager.MIN_POINTS_FOR_SILVER) {
                    silver.getUsers().add(user);
                    continue;
                }

            if (bronze.getUsers().size() < (long) users.size() * (SystemFightsVariablesManager.MAX_PERCENT_PLAYERS_IN_BRONZE * PERCENT))
                if (level >= SystemFightsVariablesManager.MIN_LEVEL_FOR_BRONZE && points > SystemFightsVariablesManager.MIN_POINTS_FOR_BRONZE) {
                    bronze.getUsers().add(user);
                    continue;
                }

            if (wood.getUsers().size() < (long) users.size() * (SystemFightsVariablesManager.MAX_PERCENT_PLAYERS_IN_WOOD * PERCENT))
                if (level >= SystemFightsVariablesManager.MIN_LEVEL_FOR_WOOD && points > SystemFightsVariablesManager.MIN_POINTS_FOR_WOOD)
                    wood.getUsers().add(user);

        }

        divisionService.save(challenger);
        divisionService.save(diamond);
        divisionService.save(gold);
        divisionService.save(silver);
        divisionService.save(bronze);
        divisionService.save(wood);
    }
}