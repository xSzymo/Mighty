package game.mightywarriors.other.enums;

public enum ChatRole {
    ADMIN("admin"), MODIFIER("modifier"), OWNER("owner");

    private String role;

    ChatRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
