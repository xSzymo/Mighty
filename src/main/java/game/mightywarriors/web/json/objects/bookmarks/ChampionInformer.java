package game.mightywarriors.web.json.objects.bookmarks;

public class ChampionInformer {


    /**
     * All of above
     * championId : table of user's champions ids
     */
    public long[] championId;//TODO

    public ChampionInformer() {
    }

    public ChampionInformer(long[] championId) {
        this.championId = championId;
    }
}
