package game.mightywarriors.data.tables;

import game.mightywarriors.other.ChampionLevelManager;

import javax.persistence.*;

@Entity
@Table(name = "champions")
public class Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @Column(name = "experience")
    private long experience = 1;

    @OneToOne
    private Statistic statistic;

    @OneToOne
    private Equipment equipment;

    public Champion() {

    }

    public Champion(Statistic statistic, Equipment equipment) {
        this.statistic = statistic;
        this.equipment = equipment;
    }

    public Long getId() {
        return id;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }


    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public long getLevel() {
        return ChampionLevelManager.getUserLevel(this);
    }
}
