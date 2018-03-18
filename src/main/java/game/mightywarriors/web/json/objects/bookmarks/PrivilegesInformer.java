package game.mightywarriors.web.json.objects.bookmarks;

public class PrivilegesInformer {

    public long userId;
    public String userLogin;

    /**
     * Messages
     * chatId : chat id;
     */
    public long chatId;

    /**
     * admin : admin role in other case modifier role
     */
    public boolean admin;

    public PrivilegesInformer() {
    }

    public PrivilegesInformer(long userId, String userLogin, long chatId, boolean admin) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.chatId = chatId;
        this.admin = admin;
    }
}
