package game.mightywarriors.json.objects.fights;

import game.mightywarriors.data.tables.User;

import java.util.LinkedList;
import java.util.List;

public class FightResult {
    private User winner;
    private User looser;
    private List<RoundProcess> rounds = new LinkedList<>();

    public List<RoundProcess> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundProcess> rounds) {
        this.rounds = rounds;
    }

    public User getLooser() {
        return looser;
    }

    public void setLooser(User looser) {
        this.looser = looser;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }
}
