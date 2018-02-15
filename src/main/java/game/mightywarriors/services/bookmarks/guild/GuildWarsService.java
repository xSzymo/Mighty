package game.mightywarriors.services.bookmarks.guild;

import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.GuildRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.services.bookmarks.utilities.GuildHelper;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildWarsService {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private FightCoordinator fightCoordinator;
    @Autowired
    private GuildHelper helper;

    /**
     * This is not final system for guild wars
     * The current environment don't consider fights between guilds
     * Here's a little hack :)
     *
     * @param authorization - authorization token
     * @param informer      - needs info about enemy guild
     * @return FightResult
     * @throws Exception
     */
    public FightResult performGuildWar(String authorization, GuildInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Guild guild = helper.retrieveGuild(informer);

        throwExceptionIf_guildIsNotPresent(guild);
        throwExceptionIf_guildIsNotPresent(user.getGuild());
        throwExceptionIf_userIsNotGuildOwner(user);


        return fightCoordinator.fight(user.getGuild().getUsers(), guild.getUsers());
    }

    private void throwExceptionIf_userIsNotGuildOwner(User user) throws NoAccessException {
        if (!user.getRole().getRole().equals(GuildRole.OWNER.getRole()))
            throw new NoAccessException("user have no access to do that");
    }

    private void throwExceptionIf_guildIsNotPresent(Guild guild) throws Exception {
        if (guild == null)
            throw new Exception("You already have guild");
    }
}
