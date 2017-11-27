package game.mightywarriors.services.fight;

import game.mightywarriors.web.json.objects.fights.Fighter;
import game.mightywarriors.web.json.objects.fights.RoundProcess;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class RoundFightPerformer {

    public RoundProcess performSingleRound(RoundProcess round, int userTurn, int opponentTurn, boolean isUserTurn) {
        LinkedList<Fighter> userChampions = round.getUserChampions();
        LinkedList<Fighter> opponentChampions = round.getOpponentChampions();

        if (isUserTurn)
            round.setOpponentChampions(championAttack(opponentChampions, userChampions.get(userTurn)));
        else
            round.setUserChampions(championAttack(userChampions, opponentChampions.get(opponentTurn)));

        return round;
    }

    private LinkedList<Fighter> championAttack(LinkedList<Fighter> champions, Fighter fighter) {
        for (Fighter champion : champions) {
            double hp = champion.getHp();

            if (hp <= 0)
                continue;

            if (Math.random() * 100 <= champion.getCriticChance()) {
                hp -= (fighter.getStrength() / champion.getArmor()) * 2;
                hp -= (fighter.getIntelligence() / champion.getMagicResist()) * 2;
            } else {
                hp -= (fighter.getStrength() / champion.getArmor());
                hp -= (fighter.getIntelligence() / champion.getMagicResist());
            }

            fighter.setDmg(champion.getHp() - hp);
            champion.setHp(hp);

            return champions;
        }
        return null;
    }
}
