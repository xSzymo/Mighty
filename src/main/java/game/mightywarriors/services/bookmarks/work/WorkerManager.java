package game.mightywarriors.services.bookmarks.work;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.services.WorkService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.Work;
import game.mightywarriors.other.exceptions.BusyChampionException;
import game.mightywarriors.services.bookmarks.utilities.FightHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ChampionInformer;
import game.mightywarriors.web.json.objects.bookmarks.WorkInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorkerManager {
    private FightHelper fightHelper;
    private UserService userService;
    private WorkService workService;
    private WorkerUtility workerUtility;
    private UsersRetriever usersRetriever;

    @Autowired
    public WorkerManager(UserService userService, UsersRetriever usersRetriever, WorkService workService, FightHelper fightHelper, WorkerUtility workerUtility) {
        this.userService = userService;
        this.usersRetriever = usersRetriever;
        this.workService = workService;
        this.fightHelper = fightHelper;
        this.workerUtility = workerUtility;
    }

    public void setWorkForUser(String authorization, WorkInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_AnyChampionIsBusy(user, informer);

        workerUtility.createWork(user, informer.hours, fightHelper.getChampions(user, informer.championId));
    }

    public void getPayment(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Set<Work> works = workService.find(user.getLogin());

        for (Work work : works)
            if (work != null && !work.getBlockUntil().after(new Timestamp(System.currentTimeMillis())))
                workerUtility.getPayment(user, work);
    }

    public void cancelWork(String authorization, ChampionInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Set<Work> works = workService.find(user.getLogin());

        for (Work work : works) {
            for (Champion champion : fightHelper.getChampions(user, informer.championId)) {
                if (work.getChampion().getId().equals(champion.getId())) {
                    user = workerUtility.setDate(user, Stream.of(work.getChampion()).collect(Collectors.toSet()), null);

                    workService.delete(work);
                    userService.save(user);
                }
            }
        }
    }

    private void throwExceptionIf_AnyChampionIsBusy(User user, WorkInformer workInformer) throws BusyChampionException {
        if (fightHelper.getBiggestBlockTimeForEnteredChampions(user, workInformer.championId) > 0)
            throw new BusyChampionException("Some one is already busy");
    }
}
