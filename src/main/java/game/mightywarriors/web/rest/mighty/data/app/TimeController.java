package game.mightywarriors.web.rest.mighty.data.app;

import game.mightywarriors.web.json.objects.time.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;


@RestController
public class TimeController {

    @GetMapping("time")
    public Timer getServerTime() {
        return new Timer(new Timestamp(System.currentTimeMillis()));
    }
}
