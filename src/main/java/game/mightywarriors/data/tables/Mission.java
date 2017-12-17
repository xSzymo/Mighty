package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "missions")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "experience")
    private long experience = 1;
    @Column(name = "description")
    private String description;
    @Column(name = "time_duration")
    private long timeDuration;
    @Column(name = "gold")
    private BigDecimal gold = new BigDecimal("0");

    @OneToOne
    private Monster monster;

    public Mission() {

    }

    public Mission(long experience, String description, BigDecimal gold, Monster monster) {
        this.experience = experience;
        this.description = description;
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

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public long getTimeDuration() {
        return timeDuration;
    }

    public Mission setTimeDuration(long timeDuration) {
        this.timeDuration = timeDuration;
        return this;
    }
}
