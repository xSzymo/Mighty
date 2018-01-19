package game.mightywarriors.services.bookmarks.work;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.services.WorkService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.Work;
import game.mightywarriors.other.exceptions.BusyChampionException;
import game.mightywarriors.services.bookmarks.utilities.Helper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.tavern.Informer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorkerManager {
    private Helper helper;
    private UserService userService;
    private WorkService workService;
    private WorkerUtility workerUtility;
    private UsersRetriever usersRetriever;

    @Autowired
    public WorkerManager(UserService userService, UsersRetriever usersRetriever, WorkService workService, Helper helper, WorkerUtility workerUtility) {
        this.userService = userService;
        this.usersRetriever = usersRetriever;
        this.workService = workService;
        this.helper = helper;
        this.workerUtility = workerUtility;
    }

    public void setWorkForUser(String authorization, Informer workJSON) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        if (helper.getBiggestBlockTimeForEnteredChampions(user, helper.getChampionsId(user.getChampions())) > 0)
            throw new BusyChampionException("Some one is already busy");

        workerUtility.createWork(user, workJSON.hours, helper.getChampions(user, workJSON.championId));
    }

    public void getPayment(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Set<Work> works = workService.find(user.getLogin());

        for (Work work : works)
            if (work != null && !work.getBlockDate().after(new Timestamp(System.currentTimeMillis())))
                workerUtility.getPayment(user, work);
    }

    public void cancelWork(String authorization, Informer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Set<Work> works = workService.find(user.getLogin());

        for (Work work : works) {
            for (Champion champion : helper.getChampions(user, informer.championId)) {
                if (work.getChampion().getId().equals(champion.getId())) {
                    user = workerUtility.setDate(user, Stream.of(work.getChampion()).collect(Collectors.toSet()), null);

                    workService.delete(work);
                    userService.save(user);
                }
            }
        }
    }
}
