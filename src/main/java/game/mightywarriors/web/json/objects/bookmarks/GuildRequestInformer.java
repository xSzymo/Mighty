package game.mightywarriors.web.json.objects.bookmarks;

public class GuildRequestInformer {

    /**
     * guildId : guild id
     * guildName: guild name
     */
    public long guildId;
    public String guildName;
    public String description;

    public GuildRequestInformer() {
    }

    public GuildRequestInformer(long guildId, String guildName) {
        this.guildId = guildId;
        this.guildName = guildName;
    }
}
