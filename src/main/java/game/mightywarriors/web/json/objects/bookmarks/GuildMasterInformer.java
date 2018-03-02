package game.mightywarriors.web.json.objects.bookmarks;

public class GuildMasterInformer {


    /**
     * userName : user name
     * userId : user id
     */
    public String userName;
    public int userId;

    public GuildMasterInformer() {
    }

    public GuildMasterInformer(String userName, int userId) {
        this.userName = userName;
        this.userId = userId;
    }
}
