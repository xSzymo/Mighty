package game.mightywarriors.web.json.objects.bookmarks;

public class CreateGuildInformer {

    /**
     * guildId : guild id
     * guildName: guild name
     * description: description of request - optional
     */
    public String guildName;
    public int minimumLevel;

    public CreateGuildInformer() {
    }

    public CreateGuildInformer(String guildName, int minimumLevel) {
        this.guildName = guildName;
        this.minimumLevel = minimumLevel;
    }
}
