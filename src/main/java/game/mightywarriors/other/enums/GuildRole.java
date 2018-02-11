package game.mightywarriors.other.enums;

public enum GuildRole {
    MEMBER("member"), OWNER("owner");

    private String role;

    GuildRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
