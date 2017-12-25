package game.mightywarriors.data.tables;

import javax.persistence.*;

@Entity
@Table(name = "statistic")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private long strength;
    private long intelligence;
    private long vitality;
    private long criticChance;
    private long armor;
    private long magicResist;

    public Statistic() {
        this.strength = 0;
        this.intelligence = 0;
        this.vitality = 0;
        this.criticChance = 0;
        this.armor = 0;
        this.magicResist = 0;
    }

    public Statistic(long strength, long intelligence, long vitality, long criticChance, long armor, long magicResist) {
        this.strength = strength;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.criticChance = criticChance;
        this.armor = armor;
        this.magicResist = magicResist;
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

    public long getMagicResist() {
        return magicResist;
    }

    public void setMagicResist(long magicResist) {
        this.magicResist = magicResist;
    }

    public Long getId() {
        return id;
    }
}
