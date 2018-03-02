package game.mightywarriors.web.json.objects.security;

public class JSONTokenObject {
    public String token;
    public long id;

    public JSONTokenObject() {
    }

    public JSONTokenObject(String token, long id) {
        this.token = token;
        this.id = id;
    }
}
