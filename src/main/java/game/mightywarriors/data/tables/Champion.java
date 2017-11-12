package game.mightywarriors.data.tables;

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
    @Column(name = "level")
    private long level = 1;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Statistic statistic;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    private Equipment equipment;

    public Champion() {

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
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
