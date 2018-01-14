package game.mightywarriors.data.tables;

import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.other.exceptions.WrongTypeItemException;

import javax.persistence.*;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Item weapon;
    @OneToOne(fetch = FetchType.EAGER)
    private Item offhand;
    @OneToOne(fetch = FetchType.EAGER)
    private Item helmet;
    @OneToOne(fetch = FetchType.EAGER)
    private Item armor;
    @OneToOne(fetch = FetchType.EAGER)
    private Item gloves;
    @OneToOne(fetch = FetchType.EAGER)
    private Item legs;
    @OneToOne(fetch = FetchType.EAGER)
    private Item boots;
    @OneToOne(fetch = FetchType.EAGER)
    private Item ring;
    @OneToOne(fetch = FetchType.EAGER)
    private Item bracelet;
    @OneToOne(fetch = FetchType.EAGER)
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

    public void setWeapon(Item weapon) throws WrongTypeItemException {
        if (weapon != null)
            if (!weapon.getTypeOfWeapon().equals(WeaponType.WEAPON))
                throw new WrongTypeItemException();

        this.weapon = weapon;
    }

    public Item getOffhand() {
        return offhand;
    }

    public void setOffhand(Item offhand) throws WrongTypeItemException {
        if (offhand != null)
            if (!offhand.getTypeOfWeapon().equals(WeaponType.OFFHAND))
                throw new WrongTypeItemException();

        this.offhand = offhand;
    }

    public Item getHelmet() {
        return helmet;
    }

    public void setHelmet(Item helmet) throws WrongTypeItemException {
        if (helmet != null)
            if (!helmet.getTypeOfWeapon().equals(WeaponType.HELMET))
                throw new WrongTypeItemException();

        this.helmet = helmet;
    }

    public Item getArmor() {
        return armor;
    }

    public void setArmor(Item armor) throws WrongTypeItemException {
        if (armor != null)
            if (!armor.getTypeOfWeapon().equals(WeaponType.ARMOR))
                throw new WrongTypeItemException();

        this.armor = armor;
    }

    public Item getGloves() {
        return gloves;
    }

    public void setGloves(Item gloves) throws WrongTypeItemException {
        if (gloves != null)
            if (!gloves.getTypeOfWeapon().equals(WeaponType.GLOVES))
                throw new WrongTypeItemException();

        this.gloves = gloves;
    }

    public Item getLegs() {
        return legs;
    }

    public void setLegs(Item legs) throws WrongTypeItemException {
        if (legs != null)
            if (!legs.getTypeOfWeapon().equals(WeaponType.LEGS))
                throw new WrongTypeItemException();

        this.legs = legs;
    }

    public Item getBoots() {
        return boots;
    }

    public void setBoots(Item boots) throws WrongTypeItemException {
        if (boots != null)
            if (!boots.getTypeOfWeapon().equals(WeaponType.BOOTS))
                throw new WrongTypeItemException();

        this.boots = boots;
    }

    public Item getRing() {
        return ring;
    }

    public void setRing(Item ring) throws WrongTypeItemException {
        if (ring != null)
            if (!ring.getTypeOfWeapon().equals(WeaponType.RING))
                throw new WrongTypeItemException();

        this.ring = ring;
    }

    public Item getBracelet() {
        return bracelet;
    }

    public void setBracelet(Item bracelet) throws WrongTypeItemException {
        if (bracelet != null)
            if (!bracelet.getTypeOfWeapon().equals(WeaponType.BRACELET))
                throw new WrongTypeItemException();

        this.bracelet = bracelet;
    }

    public Item getNecklace() {
        return necklace;
    }

    public void setNecklace(Item necklace) throws WrongTypeItemException {
        if (necklace != null)
            if (!necklace.getTypeOfWeapon().equals(WeaponType.NECKLACE))
                throw new WrongTypeItemException();
        this.necklace = necklace;
    }
}
