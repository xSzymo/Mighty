package game.mightywarriors.data.tables;

import javax.persistence.*;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private Item weapon;
    @OneToOne
    private Item offhand;

    @OneToOne
    private Item helmet;
    @OneToOne
    private Item armor;
    @OneToOne
    private Item gloves;
    @OneToOne
    private Item legs;
    @OneToOne
    private Item boots;
    @OneToOne
    private Item ring;
    @OneToOne
    private Item bracelet;
    @OneToOne
    private Item necklace;

    public Equipment() {

    }

    public Equipment(Item weapon, Item weapon2, Item helmet, Item armor, Item gloves, Item legs, Item boots, Item ring1, Item ring2, Item necklace) {
        this.weapon = weapon;
        this.offhand = weapon2;
        this.helmet = helmet;
        this.armor = armor;
        this.gloves = gloves;
        this.legs = legs;
        this.boots = boots;
        this.ring = ring1;
        this.bracelet = ring2;
        this.necklace = necklace;
    }

    public Long getId() {
        return id;
    }

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public Item getOffhand() {
        return offhand;
    }

    public void setOffhand(Item offhand) {
        this.offhand = offhand;
    }

    public Item getHelmet() {
        return helmet;
    }

    public void setHelmet(Item helmet) {
        this.helmet = helmet;
    }

    public Item getArmor() {
        return armor;
    }

    public void setArmor(Item armor) {
        this.armor = armor;
    }

    public Item getGloves() {
        return gloves;
    }

    public void setGloves(Item gloves) {
        this.gloves = gloves;
    }

    public Item getLegs() {
        return legs;
    }

    public void setLegs(Item legs) {
        this.legs = legs;
    }

    public Item getBoots() {
        return boots;
    }

    public void setBoots(Item boots) {
        this.boots = boots;
    }

    public Item getRing() {
        return ring;
    }

    public void setRing(Item ring) {
        this.ring = ring;
    }

    public Item getBracelet() {
        return bracelet;
    }

    public void setBracelet(Item bracelet) {
        this.bracelet = bracelet;
    }

    public Item getNecklace() {
        return necklace;
    }

    public void setNecklace(Item necklace) {
        this.necklace = necklace;
    }
}
