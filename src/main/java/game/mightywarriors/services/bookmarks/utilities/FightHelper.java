package game.mightywarriors.services.bookmarks.utilities;

import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FightHelper {
    protected static final int ONE_SECOND = 1000;

    public boolean isAnyOfChampionsBlocked(Set<Champion> champions) {
        for (Champion champion : champions)
            if (champion.getBlockUntil() != null && new Timestamp(System.currentTimeMillis()).before(champion.getBlockUntil()))
                return true;

        return false;
    }

    public long getBiggestBlockTimeForEnteredChampions(User user, long[] ids) {
        LinkedList<Champion> collect = user.getChampions().stream().filter(x -> {
            for (long id : ids)
                if (x.getId().equals(id))
                    return true;

            return false;
        }).collect(Collectors.toCollection(LinkedList::new));

        long time = 0;
        for (Champion champion : collect) {
            if (champion.getBlockUntil() != null)
                if (champion.getBlockUntil().getTime() > time)
                    time = champion.getBlockUntil().getTime();
        }

        return (time - new Timestamp(System.currentTimeMillis()).getTime()) / ONE_SECOND;
    }

    public Set<Champion> getChampions(User user, long[] ids) {
        return user.getChampions().stream().filter(x -> {
            for (long id : ids)
                if (x.getId().equals(id))
                    return true;

            return false;
        }).collect(Collectors.toCollection(HashSet::new));
    }

    public Set<Long> getSetOfChampionsIds(Set<Champion> champions) {
        Set<Long> collect = new HashSet<>();
        Iterator<Champion> iterator = champions.iterator();
        while (iterator.hasNext())
            collect.add(iterator.next().getId());

        return collect;
    }

    public Mission getMission(User user, long missionId) {
        return user.getMissions().stream().filter(x -> x.getId().equals(missionId)).findFirst().get();
    }

    public long[] getChampionsId(Set<Champion> champions) {
        return getChampionsId(new LinkedList<>(champions));
    }

    private long[] getChampionsId(List<Champion> champions) {
        long[] ids = new long[champions.size()];
        for (int i = 0; i < champions.size(); i++)
            ids[i] = champions.get(i).getId();

        return ids;
    }
}
