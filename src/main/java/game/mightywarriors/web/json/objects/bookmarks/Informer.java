package game.mightywarriors.web.json.objects.bookmarks;

public class Informer {

    /**
     * Work
     * hours : how many hours to work
     */
    public int hours;

    /**
     * Mission
     * id : MissionFight id
     * missionId : mission id
     */
    public long id;
    public long missionId;

    /**
     * Tavern
     * Can use one of them to find opponent
     * <p>
     * opponentId : opponent's id to fight
     * opponentName :  opponent's name to fight
     */
    public long opponentId;
    public String opponentName;

    /**
     * All of above
     * championId : table of user's champions ids
     */
    public long[] championId;

}
