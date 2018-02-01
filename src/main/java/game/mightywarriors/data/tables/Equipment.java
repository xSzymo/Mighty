package game.mightywarriors.data.tables;

import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.other.exceptions.WrongTypeItemException;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item weapon;
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item offhand;
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item helmet;
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item armor;
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item gloves;
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item legs;
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item boots;
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item ring;
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item bracelet;
    @NotFound(action = NotFoundAction.IGNORE)
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
            if (!weapon.getItemType().equals(ItemType.WEAPON))
                throw new WrongTypeItemException();

        this.weapon = weapon;
    }

    public Item getOffhand() {
        return offhand;
    }

    public void setOffhand(Item offhand) throws WrongTypeItemException {
        if (offhand != null)
            if (!offhand.getItemType().equals(ItemType.OFFHAND))
                throw new WrongTypeItemException();

        this.offhand = offhand;
    }

    public Item getHelmet() {
        return helmet;
    }

    public void setHelmet(Item helmet) throws WrongTypeItemException {
        if (helmet != null)
            if (!helmet.getItemType().equals(ItemType.HELMET))
                throw new WrongTypeItemException();

        this.helmet = helmet;
    }

    public Item getArmor() {
        return armor;
    }

    public void setArmor(Item armor) throws WrongTypeItemException {
        if (armor != null)
            if (!armor.getItemType().equals(ItemType.ARMOR))
                throw new WrongTypeItemException();

        this.armor = armor;
    }

    public Item getGloves() {
        return gloves;
    }

    public void setGloves(Item gloves) throws WrongTypeItemException {
        if (gloves != null)
            if (!gloves.getItemType().equals(ItemType.GLOVES))
                throw new WrongTypeItemException();

        this.gloves = gloves;
    }

    public Item getLegs() {
        return legs;
    }

    public void setLegs(Item legs) throws WrongTypeItemException {
        if (legs != null)
            if (!legs.getItemType().equals(ItemType.LEGS))
                throw new WrongTypeItemException();

        this.legs = legs;
    }

    public Item getBoots() {
        return boots;
    }

    public void setBoots(Item boots) throws WrongTypeItemException {
        if (boots != null)
            if (!boots.getItemType().equals(ItemType.BOOTS))
                throw new WrongTypeItemException();

        this.boots = boots;
    }

    public Item getRing() {
        return ring;
    }

    public void setRing(Item ring) throws WrongTypeItemException {
        if (ring != null)
            if (!ring.getItemType().equals(ItemType.RING))
                throw new WrongTypeItemException();

        this.ring = ring;
    }

    public Item getBracelet() {
        return bracelet;
    }

    public void setBracelet(Item bracelet) throws WrongTypeItemException {
        if (bracelet != null)
            if (!bracelet.getItemType().equals(ItemType.BRACELET))
                throw new WrongTypeItemException();

        this.bracelet = bracelet;
    }

    public Item getNecklace() {
        return necklace;
    }

    public void setNecklace(Item necklace) throws WrongTypeItemException {
        if (necklace != null)
            if (!necklace.getItemType().equals(ItemType.NECKLACE))
                throw new WrongTypeItemException();
        this.necklace = necklace;
    }
}
