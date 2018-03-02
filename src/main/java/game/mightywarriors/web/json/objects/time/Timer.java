package game.mightywarriors.web.json.objects.time;

import java.sql.Timestamp;

public class Timer {
    public Timestamp serverTime;

    public Timer() {
    }

    public Timer(Timestamp serverTime) {
        this.serverTime = serverTime;
    }
}
