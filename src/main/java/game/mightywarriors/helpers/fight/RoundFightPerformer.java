package game.mightywarriors.helpers.fight;

import game.mightywarriors.json.objects.fights.ChampionModel;
import game.mightywarriors.json.objects.fights.RoundProcess;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class RoundFightPerformer {
    public RoundProcess performSingleRound(RoundProcess round, int userTurn, int opponentTurn, boolean isUserTurn) {
        LinkedList<ChampionModel> userChampions = round.getUserChampions();
        LinkedList<ChampionModel> opponentChampions = round.getOpponentChampions();

        if (isUserTurn)
            round.setOpponentChampions(championAttack(opponentChampions, userChampions.get(userTurn)));
        else
            round.setUserChampions(championAttack(userChampions, opponentChampions.get(opponentTurn)));

        return round;
    }

    private LinkedList<ChampionModel> championAttack(LinkedList<ChampionModel> champions, ChampionModel championModel) {
        for (ChampionModel champion : champions) {
            double hp = new Double(champion.getHp());

            if (hp <= 0)
                continue;

            if ((Math.random() * (100 - 0)) + 0 <= champion.getCriticChance()) {
                hp -= (championModel.getStrength() / champion.getArmor()) * 2;
                hp -= (championModel.getIntelligence() / champion.getMagicResist()) * 2;
            } else {
                hp -= (championModel.getStrength() / champion.getArmor());
                hp -= (championModel.getIntelligence() / champion.getMagicResist());
            }

            championModel.setDmg(champion.getHp() - hp);
            champion.setHp(hp);

            return champions;
        }
        return null;
    }
}
