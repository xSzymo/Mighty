package game.mightywarriors.web.rest.mighty.bookmarks.options;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.options.EmailChanger;
import game.mightywarriors.services.bookmarks.options.LoginChanger;
import game.mightywarriors.services.bookmarks.options.PasswordChanger;
import game.mightywarriors.web.json.objects.bookmarks.CodeInformer;
import game.mightywarriors.web.json.objects.bookmarks.LoginInformer;
import game.mightywarriors.web.json.objects.bookmarks.PasswordInformer;
import game.mightywarriors.web.json.objects.bookmarks.RemindInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptionsController {
    @Autowired
    private PasswordChanger passwordChanger;
    @Autowired
    private LoginChanger loginChanger;
    @Autowired
    private EmailChanger emailChanger;

    @PostMapping("secure/options/new/email")
    public void prepareChangeEmail(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody RemindInformer informer) throws Exception {
        emailChanger.prepareChangeEmail(authorization, informer);
    }

    @PostMapping("secure/options/new/login")
    public void prepareChangeLogin(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody LoginInformer informer) throws Exception {
        loginChanger.prepareChangeLogin(authorization, informer);
    }

    @PostMapping("secure/options/new/password")
    public void prepareChangePassword(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PasswordInformer informer) throws Exception {
        passwordChanger.prepareChangePassword(authorization, informer);
    }

    @PostMapping("secure/options/change/email")
    public void changeEmail(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody CodeInformer informer) throws Exception {
        emailChanger.changeEmail(authorization, informer);
    }

    @PostMapping("secure/options/change/login")
    public void changeLogin(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody CodeInformer informer) throws Exception {
        loginChanger.changeLogin(authorization, informer);
    }

    @PostMapping("secure/options/change/password")
    public void changePassword(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody CodeInformer informer) throws Exception {
        passwordChanger.changePassword(authorization, informer);
    }
}
