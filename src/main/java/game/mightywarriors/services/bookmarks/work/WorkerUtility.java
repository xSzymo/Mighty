package game.mightywarriors.services.bookmarks.work;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorkerUtility {
    @Autowired
    private UserService userService;
    @Autowired
    private WorkService workService;

    public void createWork(User user, int hours, Set<Champion> champions) {
        Timestamp blockTime = new Timestamp(System.currentTimeMillis() + getHours(hours));
        LinkedList<Work> works = new LinkedList<>();

        for (Champion x : champions)
            works.add(new Work().build()
                    .setNickname(user.getLogin())
                    .setBlockUntil(blockTime)
                    .setTime(hours)
                    .setChampion(x));

        user = setDate(user, champions, blockTime);

        workService.save(works);
        userService.save(user);
    }

    public void getPayment(User user, Work work) {
        user.addGold(new BigDecimal(work.getTime() * SystemVariablesManager.GOLD_FROM_WORK * work.getChampion().getLevel()));
        user = setDate(user, Stream.of(work.getChampion()).collect(Collectors.toSet()), null);

        workService.delete(work);
        userService.save(user);
    }

    public User setDate(User user, Set<Champion> champions, Timestamp date) {
        user.getChampions().stream().filter(x -> {
            for (Champion x1 : champions)
                if (x1.getId().equals(x.getId()))
                    return true;
            return false;
        }).forEach(x -> x.setBlockUntil(date));

        return user;
    }

    public long getHours(int hours) {
        return hours * 60 * 60 * 1000;
    }
}
