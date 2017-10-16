package game.mightywarriors.data.tables;

import javax.persistence.*;

@Entity
@Table(name = "champions")
public class Champion {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "strength")
    private long strength;
    @Column(name = "intelligence")
    private long intelligence;
    @Column(name = "vitality")
    private long vitality;
    @Column(name = "critic_chance")
    private long criticChance;
    @Column(name = "armor")
    private long armor;
    @Column(name = "megic_resist")
    private long megicResist;

    @OneToOne
    private User user;

    public Champion(long strength, long intelligence, long vitality, long criticChance, long armor, long megicResist, User user) {
        this.strength = strength;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.criticChance = criticChance;
        this.armor = armor;
        this.megicResist = megicResist;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public long getStrength() {
        return strength;
    }

    public void setStrength(long strength) {
        this.strength = strength;
    }

    public long getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(long intelligence) {
        this.intelligence = intelligence;
    }

    public long getVitality() {
        return vitality;
    }

    public void setVitality(long vitality) {
        this.vitality = vitality;
    }

    public long getCriticChance() {
        return criticChance;
    }

    public void setCriticChance(long criticChance) {
        this.criticChance = criticChance;
    }

    public long getArmor() {
        return armor;
    }

    public void setArmor(long armor) {
        this.armor = armor;
    }

    public long getMegicResist() {
        return megicResist;
    }

    public void setMegicResist(long megicResist) {
        this.megicResist = megicResist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
