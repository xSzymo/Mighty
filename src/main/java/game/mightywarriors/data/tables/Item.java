package game.mightywarriors.data.tables;

import game.mightywarriors.other.enums.WeaponType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "type_of_weapon")
    private WeaponType typeOfWeapon;
    @Column(name = "level")
    private long level;
    @Column(name = "time_stamp")
    private Timestamp timeStamp;
    @Column(name = "gold")
    private BigDecimal gold = new BigDecimal("0");

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Statistic statistic;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image image;

    public Item() {
        timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public Item(WeaponType typeOfWeapon) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.typeOfWeapon = typeOfWeapon;
    }

    public Item(WeaponType typeOfWeapon, Statistic statistic, long level) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.typeOfWeapon = typeOfWeapon;
        this.statistic = statistic;
        this.level = level;
    }

    public Item(String name, WeaponType typeOfWeapon, Statistic statistic, long level) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.name = name;
        this.typeOfWeapon = typeOfWeapon;
        this.statistic = statistic;
        this.level = level;
    }

    public Item(String name, String description, WeaponType typeOfWeapon, Statistic statistic, Image image, long level) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.name = name;
        this.description = description;
        this.typeOfWeapon = typeOfWeapon;
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

    public WeaponType getTypeOfWeapon() {
        return typeOfWeapon;
    }

    public void setTypeOfWeapon(WeaponType typeOfWeapon) {
        this.typeOfWeapon = typeOfWeapon;
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

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }
}
