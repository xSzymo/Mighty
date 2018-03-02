package game.mightywarriors.web.json.objects.bookmarks;

public class DeleteMessageInformer {

    /**
     * Messages
     * chatId : chat id;
     */
    public long chatId;

    /**
     * messageId : message id
     */
    public long messageId;

    public DeleteMessageInformer() {
    }

    public DeleteMessageInformer(long chatId, long messageId) {
        this.chatId = chatId;
        this.messageId = messageId;
    }
}
