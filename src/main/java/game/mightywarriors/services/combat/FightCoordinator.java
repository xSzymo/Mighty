package game.mightywarriors.services.combat;

import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

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

    public FightResult fight(User user, Monster monster, long championId) throws Exception {
        long[] championsId = {championId};

        return fight(user, monster, championsId);
    }

    public FightResult fight(User user, User opponent, long championId, long opponentId) throws Exception {
        long[] championsId = {championId};
        long[] opponentsId = {opponentId};

        return fight(user, opponent, championsId, opponentsId);
    }

    public FightResult fight(User user, Monster monster, long[] championsId) throws Exception {
        LinkedList<Champion> champions = new LinkedList<>();

        for (Champion champion : user.getChampions())
            for (long id : championsId)
                if (champion.getId().equals(id))
                    champions.add(champion);

        user.setChampions((new LinkedList<>(champions)));

        if (user.getChampions().size() < 1)
            throw new Exception("Specify champion to fight");

        return fight(user, monster);
    }

    public FightResult fight(User user, User opponent, long[] championsId, long[] opponentsId) throws Exception {
        LinkedList<Champion> champions = new LinkedList<>();

        for (Champion champion : user.getChampions())
            for (long id : championsId)
                if (champion.getId().equals(id))
                    champions.add(champion);

        user.setChampions((new LinkedList<>(champions)));

        LinkedList<Champion> opponentsChampions = new LinkedList<>();

        for (Champion champion : opponent.getChampions())
            for (long id : opponentsId)
                if (champion.getId().equals(id))
                    opponentsChampions.add(champion);

        opponent.setChampions((new LinkedList<>(opponentsChampions)));

        if (user.getChampions().size() < 1)
            throw new Exception("Specify champion to fight");
        if (opponent.getChampions().size() < 1)
            throw new Exception("Specify champion to fight");

        return fight(user, opponent);
    }


}
