package game.mightywarriors.other.enums;

public enum AuthorizationType {
    EMAIL("email"), LOGIN("login"), PASSWORD("password");

    private String type;

    AuthorizationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
