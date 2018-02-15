package game.mightywarriors.web.rest.mighty.data.user;


import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.Request;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController("guildDataController")
public class GuildController {
    @Autowired
    private GuildService guildService;
    @Autowired
    private UsersRetriever retriever;

    @GetMapping("secure/guild/chat")
    public Chat getGuildChat(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);

        return user.getGuild().getChat();
    }

    @GetMapping("secure/guild/invites")
    public Set<Request> getGuildInvites(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);

        return user.getGuild().getInvites();
    }

    @GetMapping("secure/guild")
    public Guild getUserGuild(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);

        return user.getGuild();
    }

    @GetMapping("guilds/{id}")
    public Guild getGuild(@PathVariable("id") String id) {

        return guildService.find(Integer.parseInt(id));
    }

    @GetMapping("guilds")
    public HashSet<Guild> getGuild() {

        return guildService.findAll();
    }
}
