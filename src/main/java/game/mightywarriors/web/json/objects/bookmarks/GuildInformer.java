package game.mightywarriors.web.json.objects.bookmarks;

public class GuildInformer {


    /**
     * userName : user name
     * userId : user id
     * requestId: request id
     */
    public String userName;
    public int userId;
    public int requestId;

    /**
     * guildId : guild id
     * guildName: guild name
     * description: description of request - optional
     */
    public long guildId;
    public String guildName;
    public String description;
    public int minimumLevel;

}
