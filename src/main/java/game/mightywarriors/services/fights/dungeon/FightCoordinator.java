package game.mightywarriors.services.fights.dungeon;

import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.helpers.casters.ChampionModelCaster;
import game.mightywarriors.helpers.casters.MonsterModelCaster;
import game.mightywarriors.helpers.fight.RoundFightPerformer;
import game.mightywarriors.other.jsonObjects.fights.ChampionModel;
import game.mightywarriors.other.jsonObjects.fights.FightResult;
import game.mightywarriors.other.jsonObjects.fights.RoundProcess;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class FightCoordinator {
    private final RoundFightPerformer roundFightPerformer = new RoundFightPerformer();
    private final ChampionModelCaster championModelCaster = new ChampionModelCaster();
    private final MonsterModelCaster monsterModelCaster = new MonsterModelCaster();

    public FightResult fightBetweenPlayerAndMonster(User user, LinkedList<Monster> monsters) {
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
                if(userChampionTurn >= user.getChampions().size())
                    userChampionTurn = 0;
            } else {
                opponentChampionTurn++;
                if(opponentChampionTurn >= monsters.size())
                    opponentChampionTurn = 0;
            }

            if (roundNr == 1) {
                round.setUserChampions(championModelCaster.castChampionToChampionModel(user.getChampions()));
                round.setOpponentChampions(monsterModelCaster.castChampionToChampionModel(monsters));
            } else {
                LinkedList<ChampionModel> championModels = new LinkedList<>();
                for (ChampionModel championModel : fightResult.getRounds().get(roundNr - 2).getUserChampions()) {
                    championModels.add(new ChampionModel(championModel));
                }
                round.setUserChampions(championModels);

                championModels = new LinkedList<>();
                for (ChampionModel championModel : fightResult.getRounds().get(roundNr - 2).getOpponentChampions()) {
                    championModels.add(new ChampionModel(championModel));
                }
                round.setOpponentChampions(championModels);

                round.getUserChampions().forEach(x -> x.setDmg(0));
                round.getOpponentChampions().forEach(x -> x.setDmg(0));
            }


            round = roundFightPerformer.performSingleRound(round, userChampionTurn, opponentChampionTurn, isUserTurn);

            for (ChampionModel championModel : round.getOpponentChampions()) {
                if (championModel.getHp() > 0)
                    continue;

                fightResult.setWinner(user);
                continueFight = false;
            }

            for (ChampionModel championModel : round.getUserChampions()) {
                if (championModel.getHp() > 0)
                    continue;

                fightResult.setLooser(user);
                continueFight = false;
            }
            fightResult.getRounds().add(round);
        }

        return fightResult;
    }
}
