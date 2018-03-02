package game.mightywarriors.web.json.objects.bookmarks;

public class ArenaInformer {

    /**
     * Tavern
     * Can use one of them to find opponent
     * <p>
     * opponentId : opponent's id to fight
     * opponentName :  opponent's name to fight
     */
    public long opponentId;
    public String opponentName;

    public ArenaInformer() {
    }

    public ArenaInformer(long opponentId, String opponentName) {
        this.opponentId = opponentId;
        this.opponentName = opponentName;
    }
}
