package game.mightywarriors.other.enums;

public enum Kingdom {
    KNIGH("knight"), mercenery("merceney");

    private String kingdom;

    Kingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getKingdom() {
        return kingdom;
    }
}
