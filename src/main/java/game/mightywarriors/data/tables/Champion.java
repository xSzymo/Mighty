package game.mightywarriors.data.tables;

import game.mightywarriors.data.interfaces.IFighter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "champions")
public class Champion implements IFighter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "experience")
    private long experience = 1;
    @Column(name = "level")
    private long level = 1;
    @Column(name = "block_date")
    private Timestamp blockDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Statistic statistic;

    @OneToOne
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    private Equipment equipment;

    public Champion() {

    }

    public Champion(Statistic statistic) {
        this.statistic = statistic;
    }

    public Champion(Statistic statistic, Equipment equipment) {
        this.statistic = statistic;
        this.equipment = equipment;
    }

    public Champion(Statistic statistic, Image image) {
        this.statistic = statistic;
        this.image = image;
    }

    public Champion(Statistic statistic, Image image, Equipment equipment) {
        this.statistic = statistic;
        this.image = image;
        this.equipment = equipment;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Statistic getStatistic() {
        return statistic;
    }

    public Champion setStatistic(Statistic statistic) {
        this.statistic = statistic;
        return this;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Champion setEquipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }

    public long getExperience() {
        return experience;
    }

    public Champion setExperience(long experience) {
        this.experience = experience;
        return this;
    }

    public Champion addExperience(long experience) {
        this.experience += experience;
        return this;
    }

    public long getLevel() {
        return level;
    }

    public Champion setLevel(long level) {
        this.level = level;
        return this;
    }

    public Image getImage() {
        return image;
    }

    public Champion setImage(Image image) {
        this.image = image;
        return this;
    }

    public Timestamp getBlockDate() {
        return blockDate;
    }

    public void setBlockTime(Timestamp blockDate) {
        this.blockDate = blockDate;
    }
}
