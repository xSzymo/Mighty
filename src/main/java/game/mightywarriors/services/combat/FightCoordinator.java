package game.mightywarriors.services.combat;

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
}
