package game.mightywarriors.web.rest.api.security.registration;

import game.mightywarriors.services.bookmarks.registration.AccountEnabler;
import game.mightywarriors.services.bookmarks.registration.RegistrationManager;
import game.mightywarriors.web.json.objects.bookmarks.RegistrationInformer;
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

    @PostMapping("registration")
    public void register(@RequestBody RegistrationInformer informer) throws Exception {
        registrationManager.registerUser(informer);
    }

    @PostMapping("registration/enable")
    public void enable(@RequestBody RegistrationInformer informer) throws Exception {
        accountEnabler.enableAccount(informer);
    }
}
