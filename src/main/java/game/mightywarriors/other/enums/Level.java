package game.mightywarriors.other.enums;

public enum Level {
    ONE(0), TWO(20), THREE(40), FOUR(80), FIVE(160), SIX(320), SEVEN(640), EIGHT(1280), NINE(2560), TEN(5120),
    ELEVEN(10240), TWELVE(20480);

    private long level;

    Level(long experience) {
        this.level = experience;
    }

    public long getLevel() {
        return level;
    }
}
