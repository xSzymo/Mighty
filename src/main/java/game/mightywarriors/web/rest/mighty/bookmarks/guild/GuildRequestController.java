package game.mightywarriors.web.rest.mighty.bookmarks.guild;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.guild.GuildRequestManager;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuildRequestController {
    @Autowired
    private GuildRequestManager guildRequestManager;

    @PostMapping("secure/request/send")
    public void sendRequest(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody GuildInformer informer) throws Exception {

        guildRequestManager.sendRequest(authorization, informer);
    }

    @PostMapping("secure/request/accept")
    public void acceptRequest(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody GuildInformer informer) throws Exception {

        guildRequestManager.acceptRequest(authorization, informer);
    }

    @PostMapping("secure/request/delete")
    public void deleteRequest(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody GuildInformer informer) throws Exception {

        guildRequestManager.deleteRequest(authorization, informer);
    }
}
