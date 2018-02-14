package game.mightywarriors.services.bookmarks.guild;

import game.mightywarriors.data.services.UserRoleService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.GuildRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.services.bookmarks.utilities.GuildHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildMasterService {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private GuildHelper helper;

    public void addNewGuildMaster(String authorization, GuildInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        User newMaster = helper.retrieveUser(informer);

        throwExceptionIf_guildIsNotPresent(user);
        throwExceptionIf_userIsNotGuildOwner(user);
        throwExceptionIf_userIsNotPresent(newMaster);
        throwExceptionIf_userIsNotPartOfGuild(user.getGuild(), newMaster);

        user.setUserRole(userRoleService.find(GuildRole.MEMBER.getRole()));
        newMaster.setUserRole(userRoleService.find(GuildRole.OWNER.getRole()));

        userService.save(user);
        userService.save(newMaster);
    }

    public void removeMember(String authorization, GuildInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        User memberToRemove = helper.retrieveUser(informer);

        throwExceptionIf_guildIsNotPresent(user);
        throwExceptionIf_userIsNotGuildOwner(user);
        throwExceptionIf_MemberToRemoveIsOwner(memberToRemove);
        throwExceptionIf_userIsNotPresent(memberToRemove);
        throwExceptionIf_userIsNotPartOfGuild(user.getGuild(), memberToRemove);

        memberToRemove.setUserRole(userRoleService.find("user"));
        memberToRemove.setGuild(null);
        userService.save(memberToRemove);
    }

    private void throwExceptionIf_userIsNotGuildOwner(User user) throws NoAccessException {
        if (!user.getUserRole().getRole().equals(GuildRole.OWNER.getRole()))
            throw new NoAccessException("user have no access to do that");
    }

    private void throwExceptionIf_MemberToRemoveIsOwner(User user) throws NoAccessException {
        if (user.getUserRole().getRole().equals(GuildRole.OWNER.getRole()))
            throw new NoAccessException("user have no access to do that");
    }

    private void throwExceptionIf_userIsNotPartOfGuild(Guild guild, User secondUser) throws Exception {
        if (!helper.isUserInGuild(guild, secondUser))
            throw new Exception("User is not member of the guild");
    }

    private void throwExceptionIf_guildIsNotPresent(User user) throws Exception {
        if (user.getGuild() == null)
            throw new Exception("You already have guild");
    }

    private void throwExceptionIf_userIsNotPresent(User user) throws Exception {
        if (user == null)
            throw new Exception("User not found");
    }
}