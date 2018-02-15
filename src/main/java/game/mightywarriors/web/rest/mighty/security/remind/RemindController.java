package game.mightywarriors.web.rest.mighty.security.remind;

import game.mightywarriors.services.remind.DataReminder;
import game.mightywarriors.web.json.objects.bookmarks.RemindInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemindController {
    @Autowired
    private DataReminder reminder;

    @PostMapping("remind/login")
    public void register(@RequestBody RemindInformer informer) throws Exception {
        reminder.remindLogin(informer);
    }

    @PostMapping("remind/password")
    public void enable(@RequestBody RemindInformer informer) throws Exception {
        reminder.remindPassword(informer);
    }
}
