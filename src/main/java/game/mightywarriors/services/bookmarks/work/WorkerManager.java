package game.mightywarriors.services.bookmarks.work;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.services.WorkService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.Work;
import game.mightywarriors.other.exceptions.BusyChampionException;
import game.mightywarriors.services.bookmarks.utilities.Helper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.tavern.MissionFightInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class WorkerManager {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private WorkService workService;
    @Autowired
    private Helper helper;
    @Autowired
    private WorkerUtility workerUtility;

    public void setWorkForUser(String authorization, MissionFightInformer workJSON) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        if (helper.getBiggestBlockTimeForEnteredChampions(user, helper.getChampionsId(user.getChampions())) > 0)
            throw new BusyChampionException("Some one is already busy");

        workerUtility.createWork(user, workJSON.hours, helper.getChampions(user, workJSON.championId));
    }

    public void getPayment(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        List<Work> works = workService.findOne(user.getLogin());

        for (Work work : works)
            if (work != null && !work.getBlockDate().after(new Timestamp(System.currentTimeMillis())))
                workerUtility.getPayment(user, work);
    }

    public void cancelWork(String authorization, MissionFightInformer missionFightInformer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        List<Work> works = workService.findOne(user.getLogin());

        for (Work work : works)
            if (work.getChampion().size() == missionFightInformer.championId.length)
                if (workerUtility.checkContains(work.getChampion(), helper.getChampions(user, missionFightInformer.championId))) {
                    user = workerUtility.setDate(user, work.getChampion(), null);

                    workService.delete(work);
                    userService.save(user);
                }
    }
}
