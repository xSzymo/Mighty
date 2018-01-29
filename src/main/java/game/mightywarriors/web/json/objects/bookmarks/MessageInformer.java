package game.mightywarriors.web.json.objects.bookmarks;

public class MessageInformer {

    /**
     * Messages
     * chatId : chat id;
     */
    public long chatId;

    /**
     * message : text of message
     * messageId : message id
     */
    public String message;
    public long messageId;

    /**
     * userId : user id
     * userLogin : user login
     */
    public long userId;
    public String userLogin;

    /**
     * admin : admin role in other case modifier role
     */
    public boolean admin;
}
