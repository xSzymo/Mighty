package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.BusyChampionException;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.fights.FightResult;
import game.mightywarriors.web.json.objects.bookmarks.tavern.MissionFightInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MissionChampionSender {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private MissionFightService missionFightService;
    @Autowired
    private FightCoordinator fightCoordinator;
    @Autowired
    private SenderManager senderManager;
    @Autowired
    private Helper helper;

    public FightResult performFight(String authorization, MissionFightInformer missionFightInformer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        MissionFight missionFight = missionFightService.findOne(missionFightInformer.id);

        List<Champion> champions = missionFight.getChampion();
        if (helper.isChampionOnMission(new LinkedList<>(champions), false))
            throw new BusyChampionException("Someone is already busy");

        FightResult fight = fightCoordinator.fight(user, missionFight.getMission().getMonster(), getChampionsId(missionFight.getChampion()));
        senderManager.getThingsDoneAfterFight(user, missionFight, champions, fight, fight.getWinner().getLogin().equals(user.getLogin()));

        return fight;
    }

    public MissionFight sendChampionOnMission(String authorization, MissionFightInformer missionFightInformer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        if(user.getMissionPoints() < 1)
            throw new Exception("Mission points already used");

        LinkedList<Champion> champions = helper.getChampions(user, missionFightInformer.championId);
        Mission mission = helper.getMission(user, missionFightInformer.missionId);

        if (helper.isChampionOnMission(champions, true) || champions.size() < 1 || mission == null)
            throw new BusyChampionException("Someone is already busy");

        return senderManager.prepareNewMissionFight(champions, mission);
    }

    private long[] getChampionsId(List<Champion> champions) {
        long[] ids = new long[champions.size()];
        for(int i = 0; i < champions.size(); i++)
            ids[i] = champions.get(0).getId();

        return ids;
    }
}
