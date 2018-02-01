package game.mightywarriors.other.enums;

public enum FighterType {
    CHAMPION("champion"), MONSTER("monster");

    private String type;

    FighterType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
