package game.mightywarriors.data.tables;

import game.mightywarriors.data.interfaces.IFighter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    @Column(name = "name")
    private String name;
    @Column(name = "level")
    private long level = 1;
    @Column(name = "block_until")
    private Timestamp blockUntil;
    @Column(name = "created_date")
    private Timestamp createdDate;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Statistic statistic;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image image;

    @OneToOne(fetch = FetchType.EAGER)
    private Equipment equipment;

    public Champion() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Champion(Statistic statistic) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.statistic = statistic;
    }

    public Champion(Statistic statistic, Equipment equipment) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.statistic = statistic;
        this.equipment = equipment;
    }

    public Champion(Statistic statistic, Image image) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.statistic = statistic;
        this.image = image;
    }

    public Champion(Statistic statistic, Image image, Equipment equipment) {
        createdDate = new Timestamp(System.currentTimeMillis());
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

    public Timestamp getBlockUntil() {
        return blockUntil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlockUntil(Timestamp blockUntil) {
        this.blockUntil = blockUntil;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public boolean isIsPicked() {
        return false;
    }
}
