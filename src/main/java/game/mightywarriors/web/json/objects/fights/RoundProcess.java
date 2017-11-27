package game.mightywarriors.web.json.objects.fights;

import java.util.Comparator;
import java.util.LinkedList;

public class RoundProcess {
    private int roundNr;
    private LinkedList<Fighter> userChampions;
    private LinkedList<Fighter> opponentChampions;

    public int getRoundNr() {
        return roundNr;
    }

    public void setRoundNr(int roundNr) {
        this.roundNr = roundNr;
    }

    public LinkedList<Fighter> getUserChampions() {
        return new LinkedList<>(userChampions);
    }

    public void setUserChampions(LinkedList<Fighter> userChampions) {
        this.userChampions = userChampions;
        userChampions.sort(Comparator.comparing(Fighter::getLevel));
    }

    public LinkedList<Fighter> getOpponentChampions() {
        return new LinkedList<>(opponentChampions);
    }

    public void setOpponentChampions(LinkedList<Fighter> opponentChampions) {
        this.opponentChampions = opponentChampions;
        opponentChampions.sort(Comparator.comparing(Fighter::getLevel));
    }
}
