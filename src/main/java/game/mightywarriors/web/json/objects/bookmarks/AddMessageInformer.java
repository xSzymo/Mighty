package game.mightywarriors.web.json.objects.bookmarks;

public class AddMessageInformer {

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

    public AddMessageInformer() {
    }

    public AddMessageInformer(long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }
}
