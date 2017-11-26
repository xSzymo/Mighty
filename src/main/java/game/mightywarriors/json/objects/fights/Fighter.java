package game.mightywarriors.json.objects.fights;

public class Fighter {
    private long id;
    private double dmg;
    private long level;
    private double strength;
    private double intelligence;
    private double hp;
    private double criticChance;
    private double armor;
    private double magicResist;

    public Fighter() {

    }

    public Fighter(Fighter fighter) {
        this.id = fighter.id;
        this.dmg = fighter.dmg;
        this.level = fighter.level;
        this.strength = fighter.strength;
        this.intelligence = fighter.intelligence;
        this.hp = fighter.hp;
        this.criticChance = fighter.criticChance;
        this.armor = fighter.armor;
        this.magicResist = fighter.magicResist;
    }

    public Fighter build() {
        return this;
    }

    public double getDmg() {
        return dmg;
    }

    public Fighter setDmg(double dmg) {
        this.dmg = dmg;
        return this;
    }

    public double getStrength() {
        return strength;
    }

    public Fighter setStrength(double strength) {
        this.strength = strength;
        return this;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public Fighter setIntelligence(double intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public double getCriticChance() {
        return criticChance;
    }

    public Fighter setCriticChance(double criticChance) {
        this.criticChance = criticChance;
        return this;
    }

    public double getArmor() {
        return armor;
    }

    public Fighter setArmor(double armor) {
        this.armor = armor;
        return this;
    }

    public double getMagicResist() {
        return magicResist;
    }

    public Fighter setMagicResist(double magicResist) {
        this.magicResist = magicResist;
        return this;
    }

    public long getLevel() {
        return level;
    }

    public Fighter setLevel(long level) {
        this.level = level;
        return this;
    }

    public double getHp() {
        return hp;
    }

    public Fighter setHp(double hp) {
        this.hp = hp;
        return this;
    }

    public double getId() {
        return id;
    }

    public Fighter setId(long id) {
        this.id = id;
        return this;
    }
}
