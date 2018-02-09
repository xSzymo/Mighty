package game.mightywarriors.web.json.objects.fights;

import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class FightResult {
    private User winner;
    private User looser;
    private List<Monster> monsters = new LinkedList<>();
    private List<RoundProcess> rounds = new LinkedList<>();
    private BigDecimal gold;
    private long experience;
    private Item item;

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

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }
}
