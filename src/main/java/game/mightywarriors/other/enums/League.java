package game.mightywarriors.other.enums;

public enum League {
    CHALLENGER("challenger"), DIAMOND("diamond"),
    GOLD("gold"), SILVER("silver"),
    BRONZE("bronze"), WOOD("wood");

    private String type;

    League(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
