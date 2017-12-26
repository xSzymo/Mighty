package game.mightywarriors.services.bookmarks.work;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.services.WorkService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
public class WorkerUtility {
    @Autowired
    private UserService userService;
    @Autowired
    private WorkService workService;

    public void createWork(User user, int hours, LinkedList<Champion> champions) {
        Timestamp blockTime = new Timestamp(System.currentTimeMillis() + getHours(hours));

        Work work = new Work().build()
                .setNickname(user.getLogin())
                .setBlockTime(blockTime)
                .setTime(hours)
                .setChampion(champions);

        user = setDate(user, champions, blockTime);

        workService.save(work);
        userService.save(user);
    }

    public void getPayment(User user, Work work) {
        for (Champion x : work.getChampion())
            user.addGold(new BigDecimal(work.getTime() * SystemVariablesManager.GOLD_FROM_WORK * x.getLevel()));
        user = setDate(user, work.getChampion(), null);

        workService.delete(work);
        userService.save(user);
    }

    public boolean checkContains(List<Champion> workChampions, LinkedList<Champion> champions) {
        boolean same = true;

        for (Champion champion : champions)
            if (!workChampions.contains(champion))
                same = false;

        return same;
    }

    public User setDate(User user, List<Champion> champions, Timestamp date) {
        user.getChampions().stream().filter(x -> {
            for (Champion x1 : champions)
                if (x1.getId().equals(x.getId()))
                    return true;
            return false;
        }).forEach(x -> x.setBlockTime(date));

        return user;
    }

    private long getHours(int hours) {
        return hours * 60 * 60 * 1000;
    }
}
