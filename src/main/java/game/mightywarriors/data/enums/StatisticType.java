package game.mightywarriors.data.enums;

public enum StatisticType {
    STRENGTH("strength"), INTELLIGENCE("intelligence"),
    VITALITY("vitality"), CRITIC_CHANCE("criticChance"),
    ARMOR("armor"), MAGIC_RESIST("magicResist");

    private String type;

    StatisticType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
