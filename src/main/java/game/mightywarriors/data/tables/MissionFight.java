package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mission_fight")
public class MissionFight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "block_until")
    private Timestamp blockUntil;
    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    private Mission mission;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Champion> champions;

    public MissionFight() {
        createdDate = new Timestamp(System.currentTimeMillis());
        champions = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Set<Champion> getChampions() {
        return champions;
    }

    public void setChampions(Set<Champion> champions) {
        this.champions = champions;
    }

    public Timestamp getBlockUntil() {
        return blockUntil;
    }

    public void setBlockUntil(Timestamp blockUntil) {
        this.blockUntil = blockUntil;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}
