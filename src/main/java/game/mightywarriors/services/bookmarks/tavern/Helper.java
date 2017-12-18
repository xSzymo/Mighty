package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
public class Helper {
    @Autowired
    private MissionFightService missionFightService;

    protected static final int ONE_SECOND = 1000;

    public boolean isChampionOnMission(LinkedList<Champion> champions, boolean mustBeNull) {
        for (Champion champion : champions) {
            if (champion.getBlockDate() != null && new Timestamp(System.currentTimeMillis()).before(champion.getBlockDate()))
                return true;

            if (!mustBeNull)
                if (missionFightService.findLatestByChampionId(champion) == null)
                    return true;
        }

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
            if (champion.getBlockDate().getTime() > time)
                time = champion.getBlockDate().getTime();
        }

        return (time - new Timestamp(System.currentTimeMillis()).getTime()) / ONE_SECOND;
    }

    public LinkedList<Champion> getChampions(User user, long[] ids) {
        return user.getChampions().stream().filter(x -> {
            for (long id : ids)
                if (x.getId().equals(id))
                    return true;

            return false;
        }).collect(Collectors.toCollection(LinkedList::new));
    }

    public Mission getMission(User user, long missionId) {
        return user.getMissions().stream().filter(x -> x.getId().equals(missionId)).findFirst().get();
    }
}
