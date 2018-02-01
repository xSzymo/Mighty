package game.mightywarriors.other.enums;

public enum ItemType {
    WEAPON("weapon"), OFFHAND("offhand"),
    HELMET("helmet"), ARMOR("armor"),
    GLOVES("gloves"), LEGS("legs"),
    BOOTS("boots"), RING("ring"),
    BRACELET("bracelet"), NECKLACE("necklace");

    private String type;

    ItemType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
