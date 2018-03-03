package game.mightywarriors.web.rest.mighty.bookmarks.guild;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.guild.GuildManager;
import game.mightywarriors.services.bookmarks.guild.GuildMasterService;
import game.mightywarriors.web.json.objects.bookmarks.CreateGuildInformer;
import game.mightywarriors.web.json.objects.bookmarks.GuildMasterInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuildController {
    @Autowired
    private GuildManager guildManager;
    @Autowired
    private GuildMasterService guildMasterService;

    @PostMapping("secure/guild/create")
    public void removeCurrentUserFromRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody CreateGuildInformer informer) throws Exception {

        guildManager.createGuild(authorization, informer);
    }

    @PostMapping("secure/guild/delete")
    public void createChat(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {

        guildManager.deleteGuild(authorization);
    }

    @PostMapping("secure/guild/leave")
    public void leaveGuild(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {

        guildManager.leaveGuild(authorization);
    }

    @PostMapping("secure/guild/new/master")
    public void addNewGuildMaster(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody GuildMasterInformer informer) throws Exception {

        guildMasterService.addNewGuildMaster(authorization, informer);
    }

    @PostMapping("secure/guild/member/remove")
    public void removeMember(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody GuildMasterInformer informer) throws Exception {

        guildMasterService.removeMember(authorization, informer);
    }
}
