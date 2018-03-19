package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.BusyChampionException;
import game.mightywarriors.services.bookmarks.utilities.FightHelper;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.MissionFightInformer;
import game.mightywarriors.web.json.objects.bookmarks.TavernInformer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TavernManager {
    private UsersRetriever usersRetriever;
    private MissionFightService missionFightService;
    private FightCoordinator fightCoordinator;
    private TavernUtility tavernUtility;
    private FightHelper fightHelper;
    private UserService userService;

    @Autowired
    public TavernManager(UsersRetriever usersRetriever, MissionFightService missionFightService, FightCoordinator fightCoordinator, TavernUtility tavernUtility, FightHelper fightHelper, UserService userService) {
        this.usersRetriever = usersRetriever;
        this.missionFightService = missionFightService;
        this.fightCoordinator = fightCoordinator;
        this.tavernUtility = tavernUtility;
        this.fightHelper = fightHelper;
        this.userService = userService;
    }

    public void sendChampionOnMission(String authorization, TavernInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Set<Champion> champions = fightHelper.getChampions(user, informer.championId);
        Mission mission = fightHelper.getMission(user, informer.missionId);

        throwExceptionIf_UserHaveNotEnoughMissionPoints(user);
        throwExceptionIf_MissionIsNotPresent(mission);
        throwExceptionIf_UserDidNotSentEnoughChampions(champions);
        throwExceptionIf_AnyOfChampionsIsBusy(champions);

        tavernUtility.prepareNewMissionFight(champions, mission);
        user.setMissionPoints(user.getMissionPoints() - 1);
        userService.save(user);
    }

    public FightResult performFight(String authorization, MissionFightInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        MissionFight missionFight = missionFightService.find(informer.missionFightId);

        throwExceptionIf_MissionFightIsNotPresent(missionFight);
        Set<Champion> champions = missionFight.getChampions();
        throwExceptionIf_AnyOfChampionsIsBusy(champions);

        FightResult fight = fightCoordinator.fight(user, missionFight.getMission().getMonster(), fightHelper.getChampionsId(missionFight.getChampions()));
        tavernUtility.getThingsDoneAfterFight(user, missionFight, champions, fight, didUserWon(fight, user));

        return fight;
    }

    private boolean didUserWon(FightResult fight, User user) {
        return fight.getWinner().getLogin() != null && fight.getWinner().getLogin().equals(user.getLogin());
    }

    private void throwExceptionIf_MissionFightIsNotPresent(MissionFight missionFight) throws Exception {
        if (missionFight == null)
            throw new Exception("Mission Fight not found");
    }

    private void throwExceptionIf_AnyOfChampionsIsBusy(Set<Champion> champions) throws BusyChampionException {
        if (fightHelper.isAnyOfChampionsBlocked(champions))
            throw new BusyChampionException("Someone is already busy");
    }

    private void throwExceptionIf_MissionIsNotPresent(Mission mission) throws Exception {
        if (mission == null)
            throw new Exception("Mission not found");
    }

    private void throwExceptionIf_UserDidNotSentEnoughChampions(Set<Champion> champions) throws Exception {
        if (champions.size() < 1)
            throw new Exception("Champions not found");
    }

    private void throwExceptionIf_UserHaveNotEnoughMissionPoints(User user) throws Exception {
        if (user.getMissionPoints() < 1)
            throw new Exception("Mission points already used");
    }
}
