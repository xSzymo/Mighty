package game.mightywarriors.services.combat;

import game.mightywarriors.data.interfaces.IFighter;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.casters.FighterModelCaster;
import game.mightywarriors.web.json.objects.fights.FightResult;
import game.mightywarriors.web.json.objects.fights.Fighter;
import game.mightywarriors.web.json.objects.fights.RoundProcess;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class FightPerformer {
    private final RoundFightPerformer roundFightPerformer = new RoundFightPerformer();
    private final FighterModelCaster fighterModelCaster = new FighterModelCaster();

    public FightResult fightBetweenUsers(User user, Object opponent) {
        FightResult fightResult = new FightResult();
        RoundProcess round = new RoundProcess();
        RoundProcess firstRound;

        int opponentChampionTurn = -1;
        int userChampionTurn = -1;
        int roundNr = 0;
        boolean isUserTurn;

        firstRound = setUpRound(round, user, opponent).setRoundNr(roundNr);

        while (fightResult.getWinner() == null && fightResult.getLooser() == null) {
            round = new RoundProcess().setRoundNr(++roundNr);
            isUserTurn = roundNr % 2 == 1;

            if (isUserTurn)
                userChampionTurn = setUserTurn(user, ++userChampionTurn);
            else
                opponentChampionTurn = setOpponentTurn(opponent, ++opponentChampionTurn);

            round = (roundNr == 1) ? setUpRound(round, user, opponent) : setUpRound(round, fightResult, roundNr);
            round = roundFightPerformer.performSingleRound(round, userChampionTurn, opponentChampionTurn, isUserTurn);
            fightResult = endFightIfAllFightersFromAnyTeamsAreDead(fightResult, round, user, opponent, firstRound);
        }

        return fightResult;
    }

    private int setUserTurn(User user, int userChampionTurn) {
        if (++userChampionTurn >= user.getChampions().size())
            userChampionTurn = 0;

        return userChampionTurn;
    }

    private int setOpponentTurn(Object opponent, int opponentChampionTurn) {
        if (opponent instanceof User)
            if (opponentChampionTurn >= ((User) opponent).getChampions().size())
                opponentChampionTurn = 0;

        if (opponent instanceof Collection<?>)
            if (opponentChampionTurn >= ((HashSet<Monster>) opponent).size())
                opponentChampionTurn = 0;

        return opponentChampionTurn;
    }

    private RoundProcess setUpRound(RoundProcess round, User user, Object opponent) {
        LinkedList<IFighter> fighters = new LinkedList<>();
        fighters.addAll(user.getChampions());
        round.setUserChampions(fighterModelCaster.castChampionToChampionModel(fighters));

        fighters = new LinkedList<>();
        if (opponent instanceof User)
            fighters.addAll(((User) opponent).getChampions());
        if (opponent instanceof Collection<?>)
            fighters.addAll((HashSet<Monster>) opponent);
        round.setOpponentChampions(fighterModelCaster.castChampionToChampionModel(fighters));

        return round;
    }

    private RoundProcess setUpRound(RoundProcess round, FightResult fightResult, int roundNr) {
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

        return round;
    }

    private FightResult endFightIfAllFightersFromAnyTeamsAreDead(FightResult fightResult, RoundProcess round, User user, Object opponent, RoundProcess firstRound) {
        if (round == null)
            throw new RuntimeException("Something went wrong");

        boolean endFight = true;

        for (Fighter fighter : round.getOpponentChampions())
            if (fighter.getHp() > 0)
                endFight = false;

        if (endFight) {
            fightResult.setWinner(user);
            if (opponent instanceof User)
                fightResult.setLooser((User) opponent);
        } else {
            endFight = true;

            for (Fighter fighter : round.getUserChampions())
                if (fighter.getHp() > 0)
                    endFight = false;

            if (endFight) {
                fightResult.setLooser(user);
                if (opponent instanceof User)
                    fightResult.setWinner((User) opponent);
            }
        }
        if (endFight)
            ((LinkedList) fightResult.getRounds()).addFirst(firstRound);

        fightResult.getRounds().add(round);
        return fightResult;
    }
}
