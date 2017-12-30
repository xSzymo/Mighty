package game.mightywarriors.data.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rankings")
public class Ranking {

    @Id
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "ranking", unique = true)
    private long ranking;

    public Ranking() {

    }

    public Ranking(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getRanking() {
        return ranking;
    }

    public void setRanking(long ranking) {
        this.ranking = ranking;
    }

    public Ranking incrementRanking() {
        ranking++;
        return this;
    }
}
