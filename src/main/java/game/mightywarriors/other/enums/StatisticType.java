package game.mightywarriors.other.enums;

public enum StatisticType {
    STRENGTH("strength"), INTELLIGENCE("intelligence"),
    VITALITY("vitality"), CRITICAL_CHANCE("criticalChance"),
    ARMOR("armor"), MAGIC_RESIST("magicResist");

    private String type;

    StatisticType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
