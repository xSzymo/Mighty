package game.mightywarriors.services.bookmarks.dungeon;

import game.mightywarriors.data.services.DungeonFightService;
import game.mightywarriors.data.tables.DungeonFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.UserDungeon;
import game.mightywarriors.other.exceptions.BusyChampionException;
import game.mightywarriors.services.bookmarks.utilities.DungeonHelper;
import game.mightywarriors.services.bookmarks.utilities.FightHelper;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;

@Service
public class DungeonManager {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private FightCoordinator fightCoordinator;
    @Autowired
    private DungeonUtility dungeonUtility;
    @Autowired
    private DungeonFightService dungeonFightService;
    @Autowired
    private FightHelper fightHelper;
    @Autowired
    private DungeonHelper dungeonHelper;

    public void sendChampionsToDungeon(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_DungeonIsNull(user.getDungeon());
        throwExceptionIf_UserHaveNotEnoughDungeonPoints(user);
        throwExceptionIf_AnyOfChampionsIsBlocked(user);
        dungeonHelper.throwExceptionIf_DungeonsFloorsAreNotSetProperly(user);

        dungeonUtility.prepareNewDungeonFight(user);
    }

    public FightResult performFightDungeonFight(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        DungeonFight dungeonFight = dungeonFightService.findByUserId(user.getId());

        throwExceptionIf_DungeonFightIsNull(dungeonFight);
        throwExceptionIf_AnyOfChampionsIsBlocked(user);

        FightResult fight = fightCoordinator.fight(user, new LinkedList<>(dungeonHelper.getCurrentFloor(user).getMonsters()));
        return dungeonUtility.getThingsDoneAfterFight(user, dungeonFight, fight, fight.getWinner().getLogin().equals(user.getLogin()));
    }

    private void throwExceptionIf_AnyOfChampionsIsBlocked(User user) throws BusyChampionException {
        if (fightHelper.isAnyOfChampionsBlocked(new HashSet<>(user.getChampions())))
            throw new BusyChampionException("Someone is already busy");
    }

    private void throwExceptionIf_DungeonFightIsNull(DungeonFight dungeonFight) throws Exception {
        if (dungeonFight == null)
            throw new Exception("Dungeon fight not found");
    }

    private void throwExceptionIf_DungeonIsNull(UserDungeon dungeon) throws Exception {
        if (dungeon == null)
            throw new Exception("User have no access yet");
    }

    private void throwExceptionIf_UserHaveNotEnoughDungeonPoints(User user) throws Exception {
        if (user.getDungeonPoints() < 1)
            throw new Exception("Dungeon points already used");
    }
}
