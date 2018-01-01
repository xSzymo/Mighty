package game.mightywarriors.services.combat;

import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class FightCoordinator {
    private final FightPerformer fightPerformer = new FightPerformer();

    public FightResult fight(User user, User opponent) {
        return fightPerformer.fightBetweenUsers(user, opponent);
    }

    public FightResult fight(User user, LinkedList<Monster> monsters) {
        return fightPerformer.fightBetweenUsers(user, monsters);
    }

    public FightResult fight(User user, Monster monster) {
        LinkedList<Monster> monsters = new LinkedList<>();
        monsters.add(monster);

        return fightPerformer.fightBetweenUsers(user, monsters);
    }

    public FightResult fight(User user1, Monster monster, long championId) throws Exception {
        User user = new User(user1);
        long[] championsId = {championId};

        return fight(user, monster, championsId);
    }

    public FightResult fight(User user1, User opponent1, long championId) throws Exception {
        User user = new User(user1);
        User opponent = new User(opponent1);
        long[] championsId = {championId};

        return fight(user, opponent, championsId, getChampionsId(opponent1.getChampions()));
    }

    public FightResult fight(User user1, User opponent1, long[] championId) throws Exception {
        User user = new User(user1);
        User opponent = new User(opponent1);

        return fight(user, opponent, championId, getChampionsId(opponent1.getChampions()));
    }

    public FightResult fight(User user1, User opponent1, long championId, long opponentId) throws Exception {
        User user = new User(user1);
        User opponent = new User(opponent1);
        long[] championsId = {championId};
        long[] opponentsId = {opponentId};

        return fight(user, opponent, championsId, opponentsId);
    }

    public FightResult fight(User user1, Monster monster, long[] championsId) throws Exception {
        User user = new User(user1);
        LinkedList<Champion> champions = new LinkedList<>();

        for (Champion champion : user.getChampions())
            for (long id : championsId)
                if (champion.getId().equals(id))
                    champions.add(champion);

        user.setChampions((new HashSet<>(champions)));

        if (user.getChampions().size() < 1)
            throw new Exception("Specify champion to fight");

        return fight(user, monster);
    }

    public FightResult fight(User user1, User opponent1, long[] championsId, long[] opponentsId) throws Exception {
        User user = new User(user1);
        User opponent = new User(opponent1);
        LinkedList<Champion> champions = new LinkedList<>();

        for (Champion champion : user.getChampions())
            for (long id : championsId)
                if (champion.getId().equals(id))
                    champions.add(champion);

        user.setChampions((new HashSet<>(champions)));

        LinkedList<Champion> opponentsChampions = new LinkedList<>();

        for (Champion champion : opponent.getChampions())
            for (long id : opponentsId)
                if (champion.getId().equals(id))
                    opponentsChampions.add(champion);

        opponent.setChampions((new HashSet<>(opponentsChampions)));

        if (user.getChampions().size() < 1)
            throw new Exception("Specify champion to fight");
        if (opponent.getChampions().size() < 1)
            throw new Exception("Specify champion to fight");

        return fight(user, opponent);
    }

    private long[] getChampionsId(Set<Champion> champion) {
        return getChampionsId(new LinkedList<>(champion));
    }

    private long[] getChampionsId(List<Champion> champions) {
        long[] ids = new long[champions.size()];
        for (int i = 0; i < champions.size(); i++)
            ids[i] = champions.get(i).getId();

        return ids;
    }
}
