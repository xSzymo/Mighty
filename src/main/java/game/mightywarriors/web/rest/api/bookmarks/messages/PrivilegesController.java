package game.mightywarriors.web.rest.api.bookmarks.messages;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.messages.PrivilegesManager;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivilegesController {
    @Autowired
    private PrivilegesManager privilegesManager;

    @PostMapping("secure/chat/privileges/add")
    public void addPrivileges(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        privilegesManager.addPrivileges(authorization, informer);
    }

    @PostMapping("secure/chat/privileges/delete")
    public void removePrivileges(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        privilegesManager.removePrivileges(authorization, informer);
    }
}
