package game.mightywarriors.web.json.objects.bookmarks;

public class PasswordInformer {

    /**
     * Profile
     * itemId : item id;
     */
    public String password;
    public String code;

    public PasswordInformer() {
    }

    public PasswordInformer(String password, String code) {
        this.password = password;
        this.code = code;
    }
}
