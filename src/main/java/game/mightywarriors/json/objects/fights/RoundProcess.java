package game.mightywarriors.json.objects.fights;

import java.util.Comparator;
import java.util.LinkedList;

public class RoundProcess {
    private int roundNr;
    private LinkedList<ChampionModel> userChampions;
    private LinkedList<ChampionModel> opponentChampions;

    public int getRoundNr() {
        return roundNr;
    }

    public void setRoundNr(int roundNr) {
        this.roundNr = roundNr;
    }

    public LinkedList<ChampionModel> getUserChampions() {
        return new LinkedList<>(userChampions);
    }

    public void setUserChampions(LinkedList<ChampionModel> userChampions) {
        this.userChampions = userChampions;
        userChampions.sort(Comparator.comparing(ChampionModel::getLevel));
    }

    public LinkedList<ChampionModel> getOpponentChampions() {
        return new LinkedList<>(opponentChampions);
    }

    public void setOpponentChampions(LinkedList<ChampionModel> opponentChampions) {
        this.opponentChampions = opponentChampions;
        opponentChampions.sort(Comparator.comparing(ChampionModel::getLevel));
    }
}
