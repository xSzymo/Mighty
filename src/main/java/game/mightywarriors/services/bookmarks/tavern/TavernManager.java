package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.BusyChampionException;
import game.mightywarriors.services.bookmarks.utilities.Helper;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.Informer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TavernManager {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private MissionFightService missionFightService;
    @Autowired
    private FightCoordinator fightCoordinator;
    @Autowired
    private TavernUtility tavernUtility;
    @Autowired
    private Helper helper;

    public MissionFight sendChampionOnMission(String authorization, Informer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        if (user.getMissionPoints() < 1)
            throw new Exception("Mission points already used");

        Set<Champion> champions = helper.getChampions(user, informer.championId);
        Mission mission = helper.getMission(user, informer.missionId);

        if (helper.isChampionOnMission(champions, true) || champions.size() < 1 || mission == null)
            throw new BusyChampionException("Someone is already busy");

        return tavernUtility.prepareNewMissionFight(champions, mission);
    }

    public FightResult performFight(String authorization, Informer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        MissionFight missionFight = missionFightService.find(informer.missionFightId);

        Set<Champion> champions = missionFight.getChampion();
        if (helper.isChampionOnMission(new HashSet<>(champions), false))
            throw new BusyChampionException("Someone is already busy");

        FightResult fight = fightCoordinator.fight(user, missionFight.getMission().getMonster(), helper.getChampionsId(missionFight.getChampion()));
        tavernUtility.getThingsDoneAfterFight(user, missionFight, champions, fight, fight.getWinner().getLogin().equals(user.getLogin()));

        return fight;
    }
}
