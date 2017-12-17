package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;

@Entity
@Table(name = "mission_fight")
public class MissionFight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "block_time")
    private Timestamp blockTime;
    @Column(name = "complete")
    private boolean complete;

    @OneToOne
    private Mission mission;

    @OneToOne
    private LinkedList<Champion> champion;

    public Long getId() {
        return id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public LinkedList<Champion> getChampion() {
        return champion;
    }

    public void setChampion(LinkedList<Champion> champion) {
        this.champion = champion;
    }

    public Timestamp getBlockDate() {
        return blockTime;
    }

    public void setBlockTime(Timestamp blockTime) {
        this.blockTime = blockTime;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
