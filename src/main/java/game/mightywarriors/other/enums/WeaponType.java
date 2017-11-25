package game.mightywarriors.other.enums;

public enum WeaponType {
    WEAPON("weapon"), OFFHAND("offhand"),
    HELMET("helmet"), ARMOR("armor"),
    GLOVES("gloves"), LEGS("legs"),
    BOOTS("boots"), RING("ring"),
    BRACELET("bracelet"), NECKLACE("necklace");

    private String type;

    WeaponType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
