package game.mightywarriors.web.rest.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;


@RestController
public class TimeController {

    @GetMapping("time")
    public Timer getUser() {
        return new Timer(new Timestamp(System.currentTimeMillis()));
    }

    private class Timer {
        public Timestamp currentTime;

        public Timer(Timestamp timestamp) {
            this.currentTime = timestamp;
        }
    }
}
