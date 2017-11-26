package game.mightywarriors.json.objects.security;

public class JSONLoginObject {
    public String login;
    public String password;

    public JSONLoginObject() {
    }

    public JSONLoginObject(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
