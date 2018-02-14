package game.mightywarriors.web.rest.api.bookmarks.guild;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.guild.GuildWarsService;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuildWarController {
    @Autowired
    private GuildWarsService guildWarsService;

    @PostMapping("secure/guild/war")
    public FightResult removeCurrentUserFromRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody GuildInformer informer) throws Exception {

        return guildWarsService.performGuildWar(authorization, informer);
    }
}
