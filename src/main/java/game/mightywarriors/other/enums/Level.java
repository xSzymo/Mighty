package game.mightywarriors.other.enums;

public enum Level {
    ONE(10, 1), TWO(20, 2), THREE(40, 3), FOUR(80, 4), FIVE(160, 5), SIX(320, 6), SEVEN(640, 7), EIGHT(1280, 8), NINE(2560, 9), TEN(5120, 10),
    ELEVEN(10240, 11), TWELVE(20480, 12);

    private long experience;
    private long level;

    Level(long experience, long level) {
        this.experience = experience;
        this.level = level;
    }

    public long getExperience() {
        return experience;
    }

    public long getLevel() {
        return level;
    }
}
