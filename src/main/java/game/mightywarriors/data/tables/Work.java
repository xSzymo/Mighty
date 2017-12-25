package game.mightywarriors.data.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "work")
public class Work {
    @Id
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "time")
    private int time;

    @Column(name = "block_time")
    private Timestamp blockTime;

    public Work() {

    }

    public Work(String nickanem) {
        this.nickname = nickanem;
    }

    public Timestamp getBlockDate() {
        return blockTime;
    }

    public void setBlockTime(Timestamp blockTime) {
        this.blockTime = blockTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
