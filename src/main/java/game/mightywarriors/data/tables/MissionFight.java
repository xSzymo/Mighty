package game.mightywarriors.data.tables;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "mission_fight")
public class MissionFight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "block_time")
    private Timestamp blockTime;

    @OneToOne
    private Mission mission;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Champion> champion;

    public Long getId() {
        return id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public List<Champion> getChampion() {
        return champion;
    }

    public void setChampion(List<Champion> champion) {
        this.champion = champion;
    }

    public Timestamp getBlockDate() {
        return blockTime;
    }

    public void setBlockTime(Timestamp blockTime) {
        this.blockTime = blockTime;
    }
}
