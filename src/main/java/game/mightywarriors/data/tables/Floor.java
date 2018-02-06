package game.mightywarriors.data.tables;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "floors")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "experience")
    private long experience = 1;
    @Column(name = "description")
    private String description;
    @Column(name = "time_duration")
    private long duration;
    @Column(name = "floor")
    private long floor;
    @Column(name = "gold")
    private BigDecimal gold = new BigDecimal("0");
    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Monster> monsters;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item item;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image image;

    public Floor() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Floor(long experience, long duration, long floor, BigDecimal gold, Set<Monster> monster, Item item, Image image) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.experience = experience;
        this.duration = duration;
        this.floor = floor;
        this.gold = gold;
        this.monsters = monster;
        this.item = item;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    public long getDuration() {
        return duration;
    }

    public Floor setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Floor)) return false;
        Floor mission = (Floor) o;
        return experience == mission.experience &&
                duration == mission.duration &&
                Objects.equals(id, mission.id) &&
                Objects.equals(description, mission.description) &&
                Objects.equals(gold, mission.gold);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, experience, description, duration, gold);
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public Image getImage() {
        return image;
    }

    public Floor setImage(Image image) {
        this.image = image;
        return this;
    }

    public Set<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(Set<Monster> monsters) {
        this.monsters = monsters;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getFloor() {
        return floor;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }
}
