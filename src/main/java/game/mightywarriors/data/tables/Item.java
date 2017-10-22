package game.mightywarriors.data.tables;

import game.mightywarriors.data.enums.WeaponType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String name;
    private String description;
    private WeaponType type_of_weapon;
    private long level;

    @OneToOne
    private Statistic statistic;

    @OneToOne
    private Image image;

    @Column(name = "time_stamp")
    private Timestamp timeStamp;

    public Item() {
        timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public Item(String name, WeaponType type_of_weapon, Statistic statistic, long level) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.name = name;
        this.type_of_weapon = type_of_weapon;
        this.statistic = statistic;
        this.level = level;
    }

    public Item(String name, String description, WeaponType type_of_weapon, Statistic statistic, Image image, long level) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.name = name;
        this.description = description;
        this.type_of_weapon = type_of_weapon;
        this.statistic = statistic;
        this.image = image;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WeaponType getType_of_weapon() {
        return type_of_weapon;
    }

    public void setType_of_weapon(WeaponType type_of_weapon) {
        this.type_of_weapon = type_of_weapon;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }
}
