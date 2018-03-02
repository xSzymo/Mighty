package game.mightywarriors.web.json.objects.bookmarks;

public class WorkInformer {

    /**
     * Work
     * hours : how many hours to work
     */
    public int hours;

    /**
     * All of above
     * championId : table of user's champions ids
     */
    public long[] championId;

    public WorkInformer() {
    }

    public WorkInformer(int hours, long[] championId) {
        this.hours = hours;
        this.championId = championId;
    }
}
