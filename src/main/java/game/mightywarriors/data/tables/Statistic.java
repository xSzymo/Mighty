package game.mightywarriors.data.tables;

import javax.persistence.*;

@Entity
@Table(name = "statistic")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private double strength;
    private double intelligence;
    private double vitality;
    private double criticalChance;
    private double armor;
    private double magicResist;

    public Statistic() {
        this.strength = 0;
        this.intelligence = 0;
        this.vitality = 0;
        this.criticalChance = 0;
        this.armor = 0;
        this.magicResist = 0;
    }

    public Statistic(double strength, double intelligence, double vitality, double criticalChance, double armor, double magicResist) {
        this.strength = strength;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.criticalChance = criticalChance;
        this.armor = armor;
        this.magicResist = magicResist;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(double intelligence) {
        this.intelligence = intelligence;
    }

    public double getVitality() {
        return vitality;
    }

    public void setVitality(double vitality) {
        this.vitality = vitality;
    }

    public double getCriticalChance() {
        return criticalChance;
    }

    public void setCriticalChance(double criticalChance) {
        this.criticalChance = criticalChance;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getMagicResist() {
        return magicResist;
    }

    public void setMagicResist(double magicResist) {
        this.magicResist = magicResist;
    }

    public Long getId() {
        return id;
    }
}
