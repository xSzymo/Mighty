package game.mightywarriors.web.json.objects.bookmarks;

public class TavernInformer {

    /**
     * Mission
     * missionId : mission id
     */
    public long missionId;


    /**
     * All of above
     * championId : table of user's champions ids
     */
    public long[] championId;

    public TavernInformer() {
    }

    public TavernInformer(long missionId, long[] championId) {
        this.missionId = missionId;
        this.championId = championId;
    }
}
