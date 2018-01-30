package game.mightywarriors.other.enums;

public enum TypeOfFighter {
    CHAMPION("champion"), MONSTER("monster");

    private String type;

    TypeOfFighter(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
