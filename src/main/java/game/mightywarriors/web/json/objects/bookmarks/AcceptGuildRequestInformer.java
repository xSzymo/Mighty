package game.mightywarriors.web.json.objects.bookmarks;

public class AcceptGuildRequestInformer {

    /**
     * guildId : guild id
     * guildName: guild name
     */
    public long requestId;
    public String userName;

    public AcceptGuildRequestInformer() {
    }

    public AcceptGuildRequestInformer(long requestId, String userName) {
        this.requestId = requestId;
        this.userName = userName;
    }
}
