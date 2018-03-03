package game.mightywarriors.other.enums;

public enum Kingdom {
    KNIGHT("knight"), MERCENERY("mercenary");

    private String kingdom;

    Kingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getKingdom() {
        return kingdom;
    }
}
