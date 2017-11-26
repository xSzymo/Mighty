package game.mightywarriors.json.objects.fights;

public class ChampionModel {
    private long id;
    private double dmg;
    private long level;
    private double strength;
    private double intelligence;
    private double hp;
    private double criticChance;
    private double armor;
    private double magicResist;

    public ChampionModel() {

    }

    public ChampionModel(ChampionModel championModel) {
        this.id = championModel.id;
        this.dmg = championModel.dmg;
        this.level = championModel.level;
        this.strength = championModel.strength;
        this.intelligence = championModel.intelligence;
        this.hp = championModel.hp;
        this.criticChance = championModel.criticChance;
        this.armor = championModel.armor;
        this.magicResist = championModel.magicResist;
    }

    public ChampionModel build() {
        return this;
    }

    public double getDmg() {
        return dmg;
    }

    public ChampionModel setDmg(double dmg) {
        this.dmg = dmg;
        return this;
    }

    public double getStrength() {
        return strength;
    }

    public ChampionModel setStrength(double strength) {
        this.strength = strength;
        return this;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public ChampionModel setIntelligence(double intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public double getCriticChance() {
        return criticChance;
    }

    public ChampionModel setCriticChance(double criticChance) {
        this.criticChance = criticChance;
        return this;
    }

    public double getArmor() {
        return armor;
    }

    public ChampionModel setArmor(double armor) {
        this.armor = armor;
        return this;
    }

    public double getMagicResist() {
        return magicResist;
    }

    public ChampionModel setMagicResist(double magicResist) {
        this.magicResist = magicResist;
        return this;
    }

    public long getLevel() {
        return level;
    }

    public ChampionModel setLevel(long level) {
        this.level = level;
        return this;
    }

    public double getHp() {
        return hp;
    }

    public ChampionModel setHp(double hp) {
        this.hp = hp;
        return this;
    }

    public double getId() {
        return id;
    }

    public ChampionModel setId(long id) {
        this.id = id;
        return this;
    }
}
