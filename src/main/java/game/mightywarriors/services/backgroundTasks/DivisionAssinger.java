package game.mightywarriors.services.backgroundTasks;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Division;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.helpers.counters.division.StatisticCounter;
import game.mightywarriors.other.comparators.UsersDivisionComparator;
import game.mightywarriors.other.enums.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;

@Service
public class DivisionAssinger {
    private final StatisticCounter statisticCounter = new StatisticCounter();
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
            double points = statisticCounter.getPointsOfFighterPower(user);


            if (challenger.getUsers().size() < SystemVariablesManager.MAX_PLAYERS_IN_CHALLENGER)
                if (level >= SystemVariablesManager.MIN_LEVEL_FOR_CHALLENGER && points > SystemVariablesManager.MIN_POINTS_FOR_CHALLENGER) {
                    challenger.getUsers().add(user);
                    continue;
                }

            if (diamond.getUsers().size() < SystemVariablesManager.MAX_PLAYERS_IN_DIAMOND)
                if (level >= SystemVariablesManager.MIN_LEVEL_FOR_DIAMOND && points >= SystemVariablesManager.MIN_POINTS_FOR_DIAMOND) {
                    diamond.getUsers().add(user);
                    continue;
                }

            if (gold.getUsers().size() < (long) users.size() * (SystemVariablesManager.MAX_PERCENT_PLAYERS_IN_GOLD * 0.1))
                if (level >= SystemVariablesManager.MIN_LEVEL_FOR_GOLD && points > SystemVariablesManager.MIN_POINTS_FOR_GOLD) {
                    gold.getUsers().add(user);
                    continue;
                }

            if (silver.getUsers().size() < (long) users.size() * (SystemVariablesManager.MAX_PERCENT_PLAYERS_IN_SILVER * 0.1))
                if (level >= SystemVariablesManager.MIN_LEVEL_FOR_SILVER && points > SystemVariablesManager.MIN_POINTS_FOR_SILVER) {
                    silver.getUsers().add(user);
                    continue;
                }

            if (bronze.getUsers().size() < (long) users.size() * (SystemVariablesManager.MAX_PERCENT_PLAYERS_IN_BRONZE * 0.1))
                if (level >= SystemVariablesManager.MIN_LEVEL_FOR_BRONZE && points > SystemVariablesManager.MIN_POINTS_FOR_BRONZE) {
                    bronze.getUsers().add(user);
                    continue;
                }

            if (wood.getUsers().size() < (long) users.size() * (SystemVariablesManager.MAX_PERCENT_PLAYERS_IN_WOOD * 0.1))
                if (level >= SystemVariablesManager.MIN_LEVEL_FOR_WOOD && points > SystemVariablesManager.MIN_POINTS_FOR_WOOD)
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
