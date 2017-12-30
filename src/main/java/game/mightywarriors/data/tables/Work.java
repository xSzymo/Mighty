package game.mightywarriors.data.tables;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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
    private Timestamp blockTime;

    @OneToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Champion champion;

    public Work() {

    }

    public Work(String nickanem) {
        this.nickname = nickanem;
    }

    public Work build() {
        return this;
    }

    public Timestamp getBlockDate() {
        return blockTime;
    }

    public Work setBlockTime(Timestamp blockTime) {
        this.blockTime = blockTime;
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
}
