package game.mightywarriors.web.json.objects.bookmarks;

public class PrivilegesWithOutAdminInformer {

    public long userId;
    public String userLogin;

    /**
     * Messages
     * chatId : chat id;
     */
    public long chatId;

    public PrivilegesWithOutAdminInformer() {
    }

    public PrivilegesWithOutAdminInformer(long userId, String userLogin, long chatId) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.chatId = chatId;
    }
}
