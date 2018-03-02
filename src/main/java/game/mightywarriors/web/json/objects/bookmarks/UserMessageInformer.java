package game.mightywarriors.web.json.objects.bookmarks;

public class UserMessageInformer {

    /**
     * userId : user id
     * userLogin : user login
     */
    public long userId;
    public String userLogin;

    public UserMessageInformer() {
    }

    public UserMessageInformer(long userId, String userLogin) {
        this.userId = userId;
        this.userLogin = userLogin;
    }
}
