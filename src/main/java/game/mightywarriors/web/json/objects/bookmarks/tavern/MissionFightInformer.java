package game.mightywarriors.web.json.objects.bookmarks.tavern;

public class MissionFightInformer {

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
     *
     * opponentId : opponent's id to fight
     * opponentName :  opponent's name to fight
     */
    public long opponentId;
    public String opponentName;

    /**
     * Both
     * championId : table of user's champions ids
     */
    public long[] championId;

}
