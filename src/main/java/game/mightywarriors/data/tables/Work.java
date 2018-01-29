package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;
    @Column(name = "time")
    private int time;
    @Column(name = "block_time")
    private Timestamp blockUntil;
    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    private Champion champion;

    public Work() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Work(String nickname) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.nickname = nickname;
    }

    public Work build() {
        return this;
    }

    public Timestamp getBlockUntil() {
        return blockUntil;
    }

    public Work setBlockUntil(Timestamp blockUntil) {
        this.blockUntil = blockUntil;
        return this;
    }

    public int getTime() {
        return time;
    }

    public Work setTime(int time) {
        this.time = time;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Work setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public Champion getChampion() {
        return champion;
    }

    public Work setChampion(Champion champion) {
        this.champion = champion;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}
