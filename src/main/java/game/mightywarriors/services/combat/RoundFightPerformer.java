package game.mightywarriors.services.combat;

import game.mightywarriors.web.json.objects.fights.Fighter;
import game.mightywarriors.web.json.objects.fights.RoundProcess;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

import static java.lang.Math.random;

@Service
public class RoundFightPerformer {

    public RoundProcess performSingleRound(RoundProcess round, int userTurn, int opponentTurn, boolean isUserTurn) {
        LinkedList<Fighter> fighters;
        LinkedList<Fighter> userChampions = round.getUserChampions();
        LinkedList<Fighter> opponentChampions = round.getOpponentChampions();

        if (isUserTurn) {
            while (userChampions.get(userTurn).getHp() <= 0)
                userTurn++;

            fighters = championAttack(opponentChampions, userChampions.get(userTurn));
            if (fighters == null)
                return null;

            round.setOpponentChampions(fighters);
        } else {
            while (opponentChampions.get(opponentTurn).getHp() <= 0)
                opponentTurn++;

            fighters = championAttack(userChampions, opponentChampions.get(opponentTurn));
            if (fighters == null)
                return null;

            round.setUserChampions(fighters);
        }

        return round;
    }

    private LinkedList<Fighter> championAttack(LinkedList<Fighter> champions, Fighter fighter) {
        for (Fighter champion : champions) {
            double hp = champion.getHp();

            if (hp <= 0)
                continue;

            double AD = round(fighter.getStrength() / champion.getArmor());
            double AP = round(fighter.getIntelligence() / champion.getMagicResist());

            if (random() * 100 <= champion.getCriticalChance()) {
                hp -= (AD * 2);
                hp -= (AP * 2);
            } else {
                hp -= AD;
                hp -= AP;
            }

            fighter.setDmg(round(champion.getHp() - hp));
            champion.setHp(round(hp));

            return champions;
        }
        return null;
    }

    private double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
