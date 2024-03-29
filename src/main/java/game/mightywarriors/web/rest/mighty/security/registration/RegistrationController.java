package game.mightywarriors.web.rest.mighty.security.registration;

import game.mightywarriors.services.registration.AccountEnabler;
import game.mightywarriors.services.registration.CodeSender;
import game.mightywarriors.services.registration.RegistrationManager;
import game.mightywarriors.web.json.objects.bookmarks.CodeInformer;
import game.mightywarriors.web.json.objects.bookmarks.RegistrationInformer;
import game.mightywarriors.web.json.objects.bookmarks.RemindInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private RegistrationManager registrationManager;
    @Autowired
    private AccountEnabler accountEnabler;
    @Autowired
    private CodeSender codeSender;

    @PostMapping("registration")
    public void register(@RequestBody RegistrationInformer informer) throws Exception {
        registrationManager.registerUser(informer);
    }

    @PostMapping("registration/enable")
    public void enable(@RequestBody CodeInformer informer) throws Exception {
        accountEnabler.enableAccount(informer);
    }

    @PostMapping("registration/send")
    public void sendCode(@RequestBody RemindInformer informer) throws Exception {
        codeSender.sendCodeToEnableAccount(informer);
    }
}
