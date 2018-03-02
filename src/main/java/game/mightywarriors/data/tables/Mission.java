package game.mightywarriors.data.tables;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "missions")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "experience")
    private long experience = 1;
    @Column(name = "descriptionDark")
    private String descriptionDark;
    @Column(name = "descriptionLight")
    private String descriptionLight;
    @Column(name = "time_duration")
    private long duration;
    @Column(name = "gold")
    private BigDecimal gold = new BigDecimal("0");
    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    private Monster monster;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image imageLight;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image imageDark;

    public Mission() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Mission(long experience, String descriptionDark, BigDecimal gold, Monster monster) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.experience = experience;
        this.descriptionDark = descriptionDark;
        this.gold = gold;
        this.monster = monster;
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

    public String getDescriptionDark() {
        return descriptionDark;
    }

    public void setDescriptionDark(String descriptionDark) {
        this.descriptionDark = descriptionDark;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public long getDuration() {
        return duration;
    }

    public Mission setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mission)) return false;
        Mission mission = (Mission) o;
        return experience == mission.experience &&
                duration == mission.duration &&
                Objects.equals(id, mission.id) &&
                Objects.equals(descriptionDark, mission.descriptionDark) &&
                Objects.equals(gold, mission.gold);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, experience, descriptionDark, duration, gold);
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public Image getImageLight() {
        return imageLight;
    }

    public Mission setImageLight(Image imageLight) {
        this.imageLight = imageLight;
        return this;
    }

    public Image getImageDark() {
        return imageDark;
    }

    public void setImageDark(Image imageDark) {
        this.imageDark = imageDark;
    }

    public String getDescriptionLight() {
        return descriptionLight;
    }

    public void setDescriptionLight(String descriptionLight) {
        this.descriptionLight = descriptionLight;
    }
}
