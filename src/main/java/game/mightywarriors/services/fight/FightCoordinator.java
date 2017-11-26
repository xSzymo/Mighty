package game.mightywarriors.services.fight;

import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.helpers.casters.FighterModelCaster;
import game.mightywarriors.json.objects.fights.FightResult;
import game.mightywarriors.json.objects.fights.Fighter;
import game.mightywarriors.json.objects.fights.RoundProcess;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;

@Service
public class FightCoordinator {
    private final RoundFightPerformer roundFightPerformer = new RoundFightPerformer();
    private final FighterModelCaster fighterModelCaster = new FighterModelCaster();

    public FightResult fight(User user, User opponent) {
        return fightBetweenUsers(user, opponent);
    }

    public FightResult fight(User user, LinkedList<Monster> monsters) {
        return fightBetweenUsers(user, monsters);
    }

    public FightResult fight(User user, Monster monster) {
        LinkedList<Monster> monsters = new LinkedList<>();
        monsters.add(monster);

        return fightBetweenUsers(user, monsters);
    }

    private FightResult fightBetweenUsers(User user, Object opponent) {
        FightResult fightResult = new FightResult();
        RoundProcess round;

        int roundNr = 0;
        int opponentChampionTurn = -1;
        int userChampionTurn = -1;
        boolean continueFight = true;
        boolean isUserTurn;

        while (continueFight) {
            round = new RoundProcess();
            round.setRoundNr(++roundNr);
            isUserTurn = roundNr % 2 == 1;

            if (isUserTurn) {
                userChampionTurn++;

                if (userChampionTurn >= user.getChampions().size())
                    userChampionTurn = 0;
            } else {
                opponentChampionTurn++;

                if (opponent instanceof User)
                    if (opponentChampionTurn >= ((User) opponent).getChampions().size())
                        opponentChampionTurn = 0;

                if (opponent instanceof Collection<?>)
                    if (opponentChampionTurn >= ((LinkedList<Monster>) opponent).size())
                        opponentChampionTurn = 0;
            }

            if (roundNr == 1) {
                LinkedList<game.mightywarriors.data.interfaces.Fighter> fighters = new LinkedList<>();
                fighters.addAll(user.getChampions());
                round.setUserChampions(fighterModelCaster.castChampionToChampionModel(fighters));

                fighters = new LinkedList<>();
                if (opponent instanceof User)
                    fighters.addAll(((User) opponent).getChampions());
                if (opponent instanceof Collection<?>)
                    fighters.addAll((LinkedList<Monster>) opponent);
                round.setOpponentChampions(fighterModelCaster.castChampionToChampionModel(fighters));
            } else {
                LinkedList<Fighter> fighters = new LinkedList<>();
                for (Fighter fighter : fightResult.getRounds().get(roundNr - 2).getUserChampions()) {
                    fighters.add(new Fighter(fighter));
                }
                round.setUserChampions(fighters);

                fighters = new LinkedList<>();
                for (Fighter fighter : fightResult.getRounds().get(roundNr - 2).getOpponentChampions()) {
                    fighters.add(new Fighter(fighter));
                }
                round.setOpponentChampions(fighters);

                round.getUserChampions().forEach(x -> x.setDmg(0));
                round.getOpponentChampions().forEach(x -> x.setDmg(0));
            }

            round = roundFightPerformer.performSingleRound(round, userChampionTurn, opponentChampionTurn, isUserTurn);

            for (Fighter fighter : round.getOpponentChampions()) {
                if (fighter.getHp() > 0)
                    continue;

                fightResult.setWinner(user);
                if (opponent instanceof User)
                    fightResult.setLooser((User) opponent);
                continueFight = false;
            }

            for (Fighter fighter : round.getUserChampions()) {
                if (fighter.getHp() > 0)
                    continue;

                fightResult.setLooser(user);
                if (opponent instanceof User)
                    fightResult.setWinner((User) opponent);
                continueFight = false;
            }
            fightResult.getRounds().add(round);
        }

        return fightResult;
    }
}
